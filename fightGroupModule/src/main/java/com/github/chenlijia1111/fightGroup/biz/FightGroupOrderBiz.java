package com.github.chenlijia1111.fightGroup.biz;

import com.github.chenlijia1111.commonModule.common.enums.CouponTypeEnum;
import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.pojo.coupon.AbstractCoupon;
import com.github.chenlijia1111.commonModule.common.requestVo.order.CouponWithGoodIds;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.commonModule.entity.*;
import com.github.chenlijia1111.commonModule.service.*;
import com.github.chenlijia1111.fightGroup.common.enums.FightGroupStstusEnum;
import com.github.chenlijia1111.fightGroup.common.requestVo.fightGroupOrder.FightGroupOrderAddParams;
import com.github.chenlijia1111.fightGroup.entity.FightGroup;
import com.github.chenlijia1111.fightGroup.entity.FightGroupProduct;
import com.github.chenlijia1111.fightGroup.entity.FightGroupUserOrder;
import com.github.chenlijia1111.fightGroup.service.FightGroupProductServiceI;
import com.github.chenlijia1111.fightGroup.service.FightGroupServiceI;
import com.github.chenlijia1111.fightGroup.service.FightGroupUserOrderServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.*;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 拼团订单
 * <p>
 * 关于创建拼团的时机问题,可以直接在创建订单的时候就创建团或者加入团
 * 然后在前端查询正在拼团的团列表的时候,将团里面一个支付人数为0的团以及拼团成功的团过滤掉即可
 * <p>
 * 在用户支付之后更新团的人数以及团的状态
 * <p>
 * 假设场景:用户A开启了一个两人团并且支付,两人团开启成功
 * 此时B加入了拼团但是并没有支付
 * 此时C也加入了拼团也没有支付
 * 若干分钟后B支付了。拼团成功，团结束
 * 此时C支付，应提示该团已拼团成功
 * <p>
 * 所以具体调用方应该在支付的时候进行判断
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/26 0026 下午 1:36
 **/
@Service
public class FightGroupOrderBiz {

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
    @Autowired
    private CommonModuleUserServiceI commonModuleUserService;//用户
    @Autowired
    private CouponServiceI couponService;//优惠券 主要用于计算物流费,一般拼团无法使用优惠券,但也有例外
    @Autowired
    private FightGroupProductServiceI fightGroupProductService;//商品参与拼团记录
    @Autowired
    private FightGroupServiceI fightGroupService;//拼团-团
    @Autowired
    private FightGroupUserOrderServiceI fightGroupUserOrderService;//拼团订单关联记录


    /**
     * 添加拼团订单
     *
     * @param params      下单参数
     * @param retryLength 重试次数,这个参数可以给vip使用,也就是说普通用户只会抢一次,抢不到就失败了,
     *                    而vip用户可以多抢几次,这样当有很多人都想拼同一个团的时候,这个vip就会更有优势
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:49 2019/11/26 0026
     **/
    @Transactional
    public Result addFightGroupOrder(FightGroupOrderAddParams params, Integer retryLength) {

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

        //查询商品参与拼团信息,判断时间段与当前时间是否符合
        FightGroupProduct fightGroupProduct = fightGroupProductService.findById(params.getFightGroupProductId());
        if (Objects.isNull(fightGroupProduct) ||
                Objects.equals(BooleanConstant.YES_INTEGER, fightGroupProduct.getDeleteStatus())) {
            return Result.failure("无法查询到拼团信息");
        }

        Date startTime = fightGroupProduct.getStartTime();
        Date endTime = fightGroupProduct.getEndTime();
        if (Objects.isNull(startTime) || Objects.isNull(endTime)) {
            return Result.failure("秒杀信息不合法");
        }

        //判断当前时间端
        Date currentTime = new Date();
        if (currentTime.getTime() < startTime.getTime()) {
            Result.failure("拼团还未开始");
        }
        if (currentTime.getTime() >= fightGroupProduct.getEndTime().getTime()) {
            return Result.failure("拼团已结束");
        }
        //判断库存
        if (fightGroupProduct.getStockCount() < params.getCount()) {
            return Result.failure("商品已被拼完");
        }

        //可以进行拼团
        //下单
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

        //添加拼团订单记录
        //新版本号
        String newUpdateVersion = RandomUtil.createUUID();
        //减去之后的库存
        int newStockCount = fightGroupProduct.getStockCount() - params.getCount();
        fightGroupProduct.setUpdateVersion(newUpdateVersion);
        Result updateStockByVersion = fightGroupProductService.updateStockByVersion(fightGroupProduct.getId(), newStockCount,
                fightGroupProduct.getUpdateVersion(), newUpdateVersion);
        if (!updateStockByVersion.getSuccess()) {
            //判断是否可以重试
            while (retryLength > 0) {
                //进行重试
                fightGroupProduct = fightGroupProductService.findById(params.getFightGroupProductId());
                newStockCount = fightGroupProduct.getStockCount() - params.getCount();
                //判断库存
                if (fightGroupProduct.getStockCount() < params.getCount()) {
                    return Result.failure("商品已被秒杀完");
                }
                updateStockByVersion = fightGroupProductService.updateStockByVersion(fightGroupProduct.getId(), newStockCount, fightGroupProduct.getUpdateVersion(), newUpdateVersion);
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

        //如果是加入其他人的拼团,判断这个拼团是否已经成功,如果该拼团已经拼团成功了就不允许再加入拼团了
        FightGroup fightGroup = null;
        if (Objects.nonNull(params.getFightGroupId())) {
            fightGroup = fightGroupService.findById(params.getFightGroupId());
            if (Objects.isNull(fightGroup)) {
                return Result.failure("团信息不存在");
            }
            //判断该团状态,判断该团是否已经拼团成功
            if (Objects.equals(FightGroupStstusEnum.FIGHT_SUCCESS.getStatus(), fightGroup.getFightStatus())) {
                return Result.failure("该团已拼团成功,请重新开团或选择其他团");
            }
            if (Objects.equals(FightGroupStstusEnum.FIGHT_FAUILE.getStatus(), fightGroup.getFightStatus())) {
                return Result.failure("该团解散,请重新开团或选择其他团");
            }
        } else {
            //创建一个新团
            fightGroup = new FightGroup().
                    setId(String.valueOf(IDGenerateFactory.FIGHT_GROUP_ID_UTIL.nextId())).
                    setFightGroupProductId(params.getFightGroupProductId()).
                    setCreateUser(userId).
                    setCreateTime(new Date()).
                    setGroupPersonCount(fightGroupProduct.getGroupPersonCount()).
                    setCurrentGroupCount(0).
                    setFightStatus(FightGroupStstusEnum.FIGHTTING.getStatus());
            fightGroupService.add(fightGroup);
        }

        //添加订单拼团关联记录
        FightGroupUserOrder fightGroupUserOrder = new FightGroupUserOrder().
                setId(String.valueOf(IDGenerateFactory.FIGHT_GROUP_USER_ORDER_ID_UTIL.nextId())).
                setOrderNo(orderNo).
                setFightGroupId(fightGroup.getId()).
                setGroupUserId(userId).setCreateTime(new Date());
        fightGroupUserOrderService.add(fightGroupUserOrder);

        //构建订单
        ShoppingOrder shoppingOrder = new ShoppingOrder().setOrderNo(orderNo).
                setCustom(userId).
                setShops(product.getShops()).
                setGoodsId(goodId).
                setCount(count).
                setState(CommonMallConstants.ORDER_INIT).
                setProductAmountTotal(fightGroupProduct.getFightPrice() * count).
                setGoodPrice(fightGroupProduct.getFightPrice()).
                setOrderAmountTotal(fightGroupProduct.getFightPrice() * count).
                setGroupId(groupId).
                setCreateTime(currentTime).setRemarks(params.getRemarks());

        //订单快照
        AdminProductVo adminProductVo = productService.findAdminProductVoByProductId(goodVo.getProductId());
        String productSnapshot = JSONUtil.objToStr(adminProductVo);
        shoppingOrder.setDetailsJson(productSnapshot);

        //订单备注
        shoppingOrder.setRemarks(params.getRemarks());

        //添加发货单
        //发货单单号
        String sendOrderNo = String.valueOf(IDGenerateFactory.ORDER_ID_UTIL.nextId());
        ImmediatePaymentOrder immediatePaymentOrder = new ImmediatePaymentOrder().
                setOrderNo(sendOrderNo).
                setCustom(userId).
                setShops(product.getShops() + "").
                setState(CommonMallConstants.ORDER_INIT).
                setRecUser(params.getReceiverName()).
                setRecTel(params.getReceiverTelephone()).
                setRecAddr(params.getReceiverAddress()).
                setRecProvince(params.getReceiveProvince()).
                setFrontOrder(orderNo).
                setShoppingOrder(orderNo).
                setCreateTime(currentTime);

        shoppingOrder.setImmediatePaymentOrder(immediatePaymentOrder);

        //添加收货单
        //收货单单号
        String receiveOrderNo = String.valueOf(IDGenerateFactory.ORDER_ID_UTIL.nextId());
        ReceivingGoodsOrder receivingGoodsOrder = new ReceivingGoodsOrder().
                setOrderNo(receiveOrderNo).
                setCustom(userId).
                setShops(product.getShops() + "").
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


}
