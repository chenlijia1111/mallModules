package com.github.chenlijia1111.spike.biz;

import com.github.chenlijia1111.commonModule.common.enums.CouponTypeEnum;
import com.github.chenlijia1111.commonModule.common.enums.OrderTypeEnum;
import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.pojo.coupon.AbstractCoupon;
import com.github.chenlijia1111.commonModule.common.requestVo.order.CouponWithGoodIds;
import com.github.chenlijia1111.commonModule.common.responseVo.order.CalculateOrderPriceVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.commonModule.entity.*;
import com.github.chenlijia1111.commonModule.service.*;
import com.github.chenlijia1111.spike.common.requestVo.spikeOrder.SpikeOrderAddParams;
import com.github.chenlijia1111.spike.common.response.product.SpikeAdminProductVo;
import com.github.chenlijia1111.spike.entity.SpikeOrderRecode;
import com.github.chenlijia1111.spike.entity.SpikeProduct;
import com.github.chenlijia1111.spike.service.SpikeOrderRecodeServiceI;
import com.github.chenlijia1111.spike.service.SpikeProductServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.*;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 秒杀订单
 * 秒杀模块依赖于普通订单模块
 * <p>
 * 秒杀采用乐观锁对库存进行控制
 * 商品参加秒杀之后,会有一个版本号{@link SpikeProduct#getUpdateVersion()}
 * 修改库存的时候需要比对版本号
 * <p>
 * 在订单待支付取消的时候需要退回库存,由于取消规则不同,调用者实现
 *
 * @author chenLiJia
 * @since 2019-11-25 15:06:11
 **/
@Service
public class SpikeOrderBiz {


    @Autowired
    private SpikeProductServiceI spikeProductService;//产品商品参与秒杀记录
    @Autowired
    private SpikeOrderRecodeServiceI spikeOrderRecodeService;//订单与秒杀之间的关联关系
    @Autowired
    private ShoppingOrderServiceI shoppingOrderService;//订单
    @Autowired
    private ImmediatePaymentOrderServiceI immediatePaymentOrderService;//发货单
    @Autowired
    private ReceivingGoodsOrderServiceI receivingGoodsOrderService;//收货单
    @Autowired
    private GoodsServiceI goodsService;//商品
    @Autowired
    private ProductServiceI productService;//产品
    @Resource
    private CommonModuleUserServiceI commonModuleUserService;//用户
    @Autowired
    private CouponServiceI couponService;//优惠券 主要用于计算物流费,一般秒杀无法使用优惠券,但也有例外


    /**
     * 添加秒杀订单
     *
     * @param params                   下单参数
     * @param groupIdGenerateImpl      组订单编号生成器
     * @param shoppingIdGenerateImpl   购物单订单编号生成器 默认实现 {@link com.github.chenlijia1111.spike.service.impl.SpikeOrderNoGeneratorServiceImpl}
     * @param sendIdGenerateImpl       发货订单编号生成器
     * @param receiveIdGenerateImpl    收货订单编号生成器
     * @param shopGroupIdGeneratorImpl 商家组订单Id生成器
     * @param retryLength              重试次数,这个参数可以给vip使用,也就是说普通用户只会抢一次,抢不到就失败了,而vip用户可以多抢几次
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:15 2019/11/25 0025
     **/
    @Transactional
    public Result addSpikeOrder(SpikeOrderAddParams params, OrderIdGeneratorServiceI groupIdGenerateImpl,
                                OrderIdGeneratorServiceI shoppingIdGenerateImpl, OrderIdGeneratorServiceI sendIdGenerateImpl,
                                OrderIdGeneratorServiceI receiveIdGenerateImpl, OrderIdGeneratorServiceI shopGroupIdGeneratorImpl,
                                Integer retryLength) {

        //重试次数默认为0
        if (Objects.isNull(retryLength)) {
            retryLength = 0;
        }

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //当前用户
        String userId = commonModuleUserService.currentUserId();
        if (StringUtils.isEmpty(userId)) {
            return Result.notLogin();
        }

        //判断商品参与秒杀记录,判断时间段是否与当前时间相符合
        SpikeProduct spikeProduct = spikeProductService.findById(params.getSpikeProductId());
        if (Objects.isNull(spikeProduct) || Objects.equals(BooleanConstant.YES_INTEGER, spikeProduct.getDeleteStatus())) {
            return Result.failure("无法查询到秒杀信息");
        }

        Date startTime = spikeProduct.getStartTime();
        Date endTime = spikeProduct.getEndTime();
        if (Objects.isNull(startTime) || Objects.isNull(endTime)) {
            return Result.failure("秒杀信息不合法");
        }

        //判断当前时间端
        Date currentTime = new Date();
        if (currentTime.getTime() < startTime.getTime()) {
            Result.failure("秒杀还未开始");
        }
        if (currentTime.getTime() >= spikeProduct.getEndTime().getTime()) {
            return Result.failure("秒杀已结束");
        }
        //判断库存
        if (spikeProduct.getStockCount() == 0) {
            return Result.failure("商品已被秒杀完");
        }
        if (spikeProduct.getStockCount() < params.getCount()) {
            return Result.failure("商品库存不足");
        }

        //可以进行秒杀
        //下单
        //组订单Id
        String groupId = groupIdGenerateImpl.createOrderNo();
        //商家组订单编号
        String shopGroupId = shopGroupIdGeneratorImpl.createOrderNo();

        //开始处理订单
        //商品id
        String goodId = params.getGoodId();
        //商品数量
        Integer count = params.getCount();

        //查询商品信息
        GoodVo goodVo = goodsService.findByGoodId(goodId);
        if (Objects.isNull(goodVo)) {
            return Result.failure("商品不存在");
        }
        //判断产品是否存在且上架
        Product product = productService.findByProductId(goodVo.getProductId());
        if (Objects.isNull(product)) {
            return Result.failure("产品不存在");
        }
        if (Objects.equals(BooleanConstant.NO_INTEGER, product.getShelfStatus())) {
            return Result.failure("产品未上架");
        }

        //订单编号
        String orderNo = shoppingIdGenerateImpl.createOrderNo();

        //添加秒杀订单记录
        //新版本号
        String newUpdateVersion = RandomUtil.createUUID();
        //减去之后的库存
        int newStockCount = spikeProduct.getStockCount() - params.getCount();
        spikeProduct.setUpdateVersion(newUpdateVersion);
        Result updateStockByVersion = spikeProductService.updateStockByVersion(spikeProduct.getId(), newStockCount, spikeProduct.getUpdateVersion(), newUpdateVersion);
        if (!updateStockByVersion.getSuccess()) {
            //判断是否可以重试
            while (retryLength > 0) {
                //进行重试
                spikeProduct = spikeProductService.findById(params.getSpikeProductId());
                newStockCount = spikeProduct.getStockCount() - params.getCount();
                //判断库存
                if (spikeProduct.getStockCount() < params.getCount()) {
                    return Result.failure("商品已被秒杀完");
                }
                updateStockByVersion = spikeProductService.updateStockByVersion(spikeProduct.getId(), newStockCount, spikeProduct.getUpdateVersion(), newUpdateVersion);
                retryLength = retryLength - 1;

                //重试成功
                if (updateStockByVersion.getSuccess()) {
                    break;
                }
            }

            //重试后还是失败
            if (!updateStockByVersion.getSuccess()) {
                return Result.failure("当前人数太多,请稍后重试");
            }
        }

        //修改秒杀库存成功
        //添加订单秒杀关联记录
        SpikeOrderRecode spikeOrderRecode = new SpikeOrderRecode().setOrderNo(orderNo).setSpikeId(spikeProduct.getId());
        spikeOrderRecodeService.add(spikeOrderRecode);

        //构建订单
        ShoppingOrder shoppingOrder = new ShoppingOrder().setOrderNo(orderNo).
                setCustom(userId).
                setShops(product.getShops()).
                setGoodsId(goodId).
                setCount(count).
                setState(CommonMallConstants.ORDER_INIT).
                setOrderType(OrderTypeEnum.SPIKE_ORDER.getType()).
                setProductAmountTotal(spikeProduct.getSpikePrice() * count).
                setGoodPrice(spikeProduct.getSpikePrice()).
                setOrderAmountTotal(spikeProduct.getSpikePrice() * count).
                setShopGroupId(shopGroupId).
                setGroupId(groupId).
                setCreateTime(currentTime).setRemarks(params.getRemarks());

        //秒杀订单产品快照
        AdminProductVo adminProductVo = productService.findAdminProductVoByProductId(goodVo.getProductId());
        SpikeAdminProductVo spikeAdminProductVo = new SpikeAdminProductVo();
        BeanUtils.copyProperties(adminProductVo, spikeAdminProductVo);
        //参与场次开始时间
        spikeAdminProductVo.setSpikeStartTime(spikeProduct.getStartTime());
        //参与场次结束时间
        spikeAdminProductVo.setSpikeEndTime(spikeProduct.getEndTime());
        //秒杀价格
        spikeAdminProductVo.setSpikePrice(spikeProduct.getSpikePrice());
        String productSnapshot = JSONUtil.objToStr(spikeAdminProductVo);
        shoppingOrder.setDetailsJson(productSnapshot);

        //订单备注
        shoppingOrder.setRemarks(params.getRemarks());

        //添加发货单
        //发货单单号
        String sendOrderNo = sendIdGenerateImpl.createOrderNo();
        ImmediatePaymentOrder immediatePaymentOrder = new ImmediatePaymentOrder().
                setOrderNo(sendOrderNo).
                setCustom(userId).
                setShops(product.getShops()).
                setState(CommonMallConstants.ORDER_INIT).
                setRecUser(params.getReceiverName()).
                setRecTel(params.getReceiverTelephone()).
                setRecProvince(params.getRecProvince()).
                setRecCity(params.getRecCity()).
                setRecArea(params.getRecArea()).
                setRecAddr(params.getRecAddr()).
                setFrontOrder(orderNo).
                setShoppingOrder(orderNo).
                setCreateTime(currentTime);

        shoppingOrder.setImmediatePaymentOrder(immediatePaymentOrder);

        //添加收货单
        //收货单单号
        String receiveOrderNo = receiveIdGenerateImpl.createOrderNo();
        ReceivingGoodsOrder receivingGoodsOrder = new ReceivingGoodsOrder().
                setOrderNo(receiveOrderNo).
                setCustom(userId).
                setShops(product.getShops()).
                setState(CommonMallConstants.ORDER_INIT).
                setShoppingOrder(orderNo).
                setImmediatePaymentOrder(sendOrderNo).
                setFrontOrder(sendOrderNo).
                setRecUser(params.getReceiverName()).
                setCreateTime(currentTime);
        immediatePaymentOrder.setReceivingGoodsOrder(receivingGoodsOrder);

        //计算各种优惠券,结算最终应付金额
        List<CouponWithGoodIds> couponWithGoodIdsList = params.getCouponWithGoodIdsList();
        if (Lists.isNotEmpty(couponWithGoodIdsList)) {
            //优惠券id集合
            Set<String> couponIdSet = couponWithGoodIdsList.stream().map(e -> e.getCouponId()).collect(Collectors.toSet());
            List<Coupon> coupons = couponService.listByIdSet(couponIdSet);
            if (Lists.isNotEmpty(coupons)) {
                for (CouponWithGoodIds couponWithGoodId : couponWithGoodIdsList) {
                    //优惠券Id
                    String couponId = couponWithGoodId.getCouponId();
                    //优惠券作用的商品id集合
                    List<String> goodIdList = couponWithGoodId.getGoodIdList();

                    Optional<Coupon> any = coupons.stream().filter(e -> Objects.equals(e.getId(), couponId)).findAny();
                    if (!any.isPresent() || Objects.equals(any.get().getCouponType(), CouponTypeEnum.ExpressCoupon.getType())) {
                        //物流券最后处理
                        continue;
                    }
                    Coupon coupon = any.get();
                    //找出符合条件的订单进行计算
                    //是否作用当前订单
                    if (goodIdList.contains(params.getGoodId())) {
                        String couponJson = coupon.getCouponJson();
                        AbstractCoupon abstractCoupon = AbstractCoupon.transferTypeToCoupon(coupon.getCouponType(), couponJson);
                        abstractCoupon.calculatePayable(Lists.asList(shoppingOrder));
                    }
                }

                //最后计算物流券
                for (CouponWithGoodIds couponWithGoodId : couponWithGoodIdsList) {
                    //优惠券Id
                    String couponId = couponWithGoodId.getCouponId();
                    //优惠券作用的商品id集合
                    List<String> goodIdList = couponWithGoodId.getGoodIdList();

                    Optional<Coupon> any = coupons.stream().filter(e -> Objects.equals(e.getId(), couponId)).findAny();
                    if (any.isPresent() && Objects.equals(any.get().getCouponType(), CouponTypeEnum.ExpressCoupon.getType())) {
                        //物流券最后处理
                        Coupon coupon = any.get();
                        //找出符合条件的订单进行计算
                        //作用的订单
                        if (goodIdList.contains(params.getGoodId())) {
                            String couponJson = coupon.getCouponJson();
                            AbstractCoupon abstractCoupon = AbstractCoupon.transferTypeToCoupon(coupon.getCouponType(), couponJson);
                            abstractCoupon.calculatePayable(Lists.asList(shoppingOrder));
                        }
                    }
                }

            }
        }


        //总应付金额
        Double payAble = shoppingOrder.getOrderAmountTotal();
        //保留两位小数
        payAble = NumberUtil.doubleToFixLengthDouble(payAble, 2);
        //最终的应付金额,待考虑
        shoppingOrder.setPayable(payAble);

        //订单使用的优惠券
        List<AbstractCoupon> couponList = shoppingOrder.getCouponList();
        String s = JSONUtil.objToStr(couponList);
        shoppingOrder.setOrderCoupon(s);

        shoppingOrderService.add(shoppingOrder);
        immediatePaymentOrderService.add(immediatePaymentOrder);
        receivingGoodsOrderService.add(receivingGoodsOrder);

        //返回groupId
        return Result.success("操作成功", groupId);
    }


    /**
     * 添加秒杀订单
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:15 2019/11/25 0025
     **/
    @Transactional
    public Result calculateSpikeOrderPrice(SpikeOrderAddParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params, Lists.asList("spikeProductId", "goodId", "count"));
        if (!result.getSuccess()) {
            return result;
        }

        //当前用户
        String userId = commonModuleUserService.currentUserId();
        if (StringUtils.isEmpty(userId)) {
            return Result.notLogin();
        }

        //判断商品参与秒杀记录,判断时间段是否与当前时间相符合
        SpikeProduct spikeProduct = spikeProductService.findById(params.getSpikeProductId());
        if (Objects.isNull(spikeProduct)) {
            return Result.failure("无法查询到秒杀信息");
        }

        Date startTime = spikeProduct.getStartTime();
        Date endTime = spikeProduct.getEndTime();
        if (Objects.isNull(startTime) || Objects.isNull(endTime)) {
            return Result.failure("秒杀信息不合法");
        }

        //判断当前时间端
        Date currentTime = new Date();
        if (currentTime.getTime() < startTime.getTime()) {
            Result.failure("秒杀还未开始");
        }
        if (currentTime.getTime() >= spikeProduct.getEndTime().getTime()) {
            return Result.failure("秒杀以结束");
        }
        //判断库存
        if (spikeProduct.getStockCount() < params.getCount()) {
            return Result.failure("商品已被秒杀完");
        }

        //可以进行秒杀,开始计算价格
        //组订单Id
        String groupId = String.valueOf(IDGenerateFactory.ORDER_ID_UTIL.nextId());

        //开始处理订单
        //商品id
        String goodId = params.getGoodId();
        //商品数量
        Integer count = params.getCount();

        //查询商品信息
        GoodVo goodVo = goodsService.findByGoodId(goodId);
        if (Objects.isNull(goodVo)) {
            return Result.failure("商品不存在");
        }
        //判断产品是否存在且上架
        Product product = productService.findByProductId(goodVo.getProductId());
        if (Objects.isNull(product)) {
            return Result.failure("产品不存在");
        }
        if (Objects.equals(BooleanConstant.NO_INTEGER, product.getShelfStatus())) {
            return Result.failure("产品未上架");
        }

        //订单编号
        String orderNo = String.valueOf(IDGenerateFactory.ORDER_ID_UTIL.nextId());

        //构建订单
        ShoppingOrder shoppingOrder = new ShoppingOrder().setOrderNo(orderNo).
                setCustom(userId).
                setShops(product.getShops()).
                setGoodsId(goodId).
                setCount(count).
                setState(CommonMallConstants.ORDER_INIT).
                setProductAmountTotal(spikeProduct.getSpikePrice() * count).
                setGoodPrice(spikeProduct.getSpikePrice()).
                setOrderAmountTotal(spikeProduct.getSpikePrice() * count).
                setGroupId(groupId).
                setCreateTime(currentTime).setRemarks(params.getRemarks());

        //订单备注
        shoppingOrder.setRemarks(params.getRemarks());

        //计算各种优惠券,结算最终应付金额
        List<CouponWithGoodIds> couponWithGoodIdsList = params.getCouponWithGoodIdsList();
        if (Lists.isNotEmpty(couponWithGoodIdsList)) {
            //优惠券id集合
            Set<String> couponIdSet = couponWithGoodIdsList.stream().map(e -> e.getCouponId()).collect(Collectors.toSet());
            List<Coupon> coupons = couponService.listByIdSet(couponIdSet);
            if (Lists.isNotEmpty(coupons)) {
                for (CouponWithGoodIds couponWithGoodId : couponWithGoodIdsList) {
                    //优惠券Id
                    String couponId = couponWithGoodId.getCouponId();
                    //优惠券作用的商品id集合
                    List<String> goodIdList = couponWithGoodId.getGoodIdList();

                    Optional<Coupon> any = coupons.stream().filter(e -> Objects.equals(e.getId(), couponId)).findAny();
                    if (!any.isPresent() || Objects.equals(any.get().getCouponType(), CouponTypeEnum.ExpressCoupon.getType())) {
                        //物流券最后处理
                        continue;
                    }
                    Coupon coupon = any.get();
                    //找出符合条件的订单进行计算
                    //是否作用当前订单
                    if (goodIdList.contains(params.getGoodId())) {
                        String couponJson = coupon.getCouponJson();
                        AbstractCoupon abstractCoupon = AbstractCoupon.transferTypeToCoupon(coupon.getCouponType(), couponJson);
                        abstractCoupon.calculatePayable(Lists.asList(shoppingOrder));
                    }
                }

                //最后计算物流券
                for (CouponWithGoodIds couponWithGoodId : couponWithGoodIdsList) {
                    //优惠券Id
                    String couponId = couponWithGoodId.getCouponId();
                    //优惠券作用的商品id集合
                    List<String> goodIdList = couponWithGoodId.getGoodIdList();

                    Optional<Coupon> any = coupons.stream().filter(e -> Objects.equals(e.getId(), couponId)).findAny();
                    if (any.isPresent() && Objects.equals(any.get().getCouponType(), CouponTypeEnum.ExpressCoupon.getType())) {
                        //物流券最后处理
                        Coupon coupon = any.get();
                        //找出符合条件的订单进行计算
                        //作用的订单
                        if (goodIdList.contains(params.getGoodId())) {
                            String couponJson = coupon.getCouponJson();
                            AbstractCoupon abstractCoupon = AbstractCoupon.transferTypeToCoupon(coupon.getCouponType(), couponJson);
                            abstractCoupon.calculatePayable(Lists.asList(shoppingOrder));
                        }
                    }
                }

            }
        }


        //总应付金额
        Double payAble = shoppingOrder.getOrderAmountTotal();
        //保留两位小数
        payAble = NumberUtil.doubleToFixLengthDouble(payAble, 2);
        //最终的应付金额,待考虑
        shoppingOrder.setPayable(payAble);

        //订单使用的优惠券
        List<AbstractCoupon> couponList = shoppingOrder.getCouponList();

        CalculateOrderPriceVo vo = new CalculateOrderPriceVo();
        vo.setPayAble(payAble);

        //查询各个优惠券的抵扣金额
        List<AbstractCoupon> abstractCouponList = Lists.newArrayList();
        if (Lists.isNotEmpty(couponList)) {
            abstractCouponList.addAll(couponList);
        }

        //满数量折扣优惠券抵扣金额
        vo.setCountDiscountPrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.CountDiscountCoupon.getType(), e.type())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));
        //满数量减优惠券抵扣金额
        vo.setCountSubPrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.CountSubCoupon.getType(), e.type())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));
        //满价格折扣优惠券抵扣金额
        vo.setPriceDiscountPrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.PriceDiscountCoupon.getType(), e.type())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));
        //满价格减优惠券抵扣金额
        vo.setPriceSubPrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.PriceSubCoupon.getType(), e.type())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));
        //积分抵扣金额
        vo.setScorePrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.ScoreCoupon.getType(), e.type())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));
        //物流费用
        vo.setExpressPrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.ExpressCoupon.getType(), e.type())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));

        //返回计算结果
        return Result.success("操作成功", vo);
    }


}
