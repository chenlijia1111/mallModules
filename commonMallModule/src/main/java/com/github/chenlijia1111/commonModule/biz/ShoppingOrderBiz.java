package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.commonModule.common.enums.CouponTypeEnum;
import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.pojo.coupon.AbstractCoupon;
import com.github.chenlijia1111.commonModule.common.requestVo.order.CouponWithGoodIds;
import com.github.chenlijia1111.commonModule.common.requestVo.order.OrderAddParams;
import com.github.chenlijia1111.commonModule.common.requestVo.order.SingleOrderAddParams;
import com.github.chenlijia1111.commonModule.common.responseVo.order.CalculateOrderPriceVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.commonModule.entity.*;
import com.github.chenlijia1111.commonModule.service.*;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.NumberUtil;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@Service
@Slf4j
public class ShoppingOrderBiz {

    @Autowired
    private ShoppingOrderServiceI shoppingOrderService;//购物单
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
    private CouponServiceI couponService;//优惠券

    /**
     * 添加订单
     * 如果需要在此基础上进行扩展，调用者可以继承这个类，然后进行扩展
     * <p>
     * 优惠券的使用方式的话,一般有以下方式:
     * 不指定,所有都可以用
     * 指定类别可用
     * 指定商品可用
     * <p>
     * 先计算每个订单的订单单个订单的应付金额 {@link ShoppingOrder#getOrderAmountTotal()}
     * 计算完之后再根据所有的优惠券计算每个订单的单个应付金额
     * 注意,物流券是最后算的
     * <p>
     * 总应付金额等于所有单个订单的应付金额之和 {@link ShoppingOrder#getPayable()}
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:53 2019/11/5 0005
     **/
    @Transactional
    public Result add(OrderAddParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }
        //校验订单中的商品参数
        List<SingleOrderAddParams> singleOrderList = params.getSingleOrderList();
        for (SingleOrderAddParams addParams : singleOrderList) {
            result = PropertyCheckUtil.checkProperty(addParams);
            if (!result.getSuccess()) {
                return result;
            }
        }

        //当前用户
        String userId = commonModuleUserService.currentUserId();
        if (StringUtils.isEmpty(userId)) {
            return Result.notLogin();
        }


        //组订单Id
        String groupId = String.valueOf(IDGenerateFactory.ORDER_ID_UTIL.nextId());
        //当前时间
        Date currentTime = new Date();

        //开始处理订单
        ArrayList<ShoppingOrder> orderList = Lists.newArrayList();
        for (SingleOrderAddParams addParams : singleOrderList) {
            //商品id
            String goodId = addParams.getGoodId();
            //商品数量
            Integer count = addParams.getCount();

            //查询商品信息
            GoodVo goodVo = goodsService.findByGoodId(goodId);
            if (Objects.isNull(goodVo)) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure("商品不存在");
            }
            //判断产品是否存在且上架
            Product product = productService.findByProductId(goodVo.getProductId());
            if (Objects.isNull(product)) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure("产品不存在");
            }
            if (Objects.equals(BooleanConstant.NO_INTEGER, product.getShelfStatus())) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure("产品未上架");
            }

            //订单编号
            String orderNo = String.valueOf(IDGenerateFactory.ORDER_ID_UTIL.nextId());

            ShoppingOrder shoppingOrder = new ShoppingOrder().setOrderNo(orderNo).
                    setCustom(userId).
                    setShops(product.getShops()).
                    setGoodsId(goodId).
                    setCount(count).
                    setState(CommonMallConstants.ORDER_INIT).
                    setProductAmountTotal(goodVo.getPrice() * count).
                    setOrderAmountTotal(goodVo.getPrice() * count).
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

            orderList.add(shoppingOrder);
        }

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
                    //作用的订单
                    List<ShoppingOrder> hitOrderList = orderList.stream().filter(e -> goodIdList.contains(e.getGoodsId())).collect(Collectors.toList());
                    if (Lists.isNotEmpty(hitOrderList)) {
                        String couponJson = coupon.getCouponJson();
                        AbstractCoupon abstractCoupon = AbstractCoupon.transferTypeToCoupon(coupon.getCouponType(), couponJson);
                        abstractCoupon.calculatePayable(hitOrderList);
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
                        List<ShoppingOrder> hitOrderList = orderList.stream().filter(e -> goodIdList.contains(e.getGoodsId())).collect(Collectors.toList());
                        if (Lists.isNotEmpty(hitOrderList)) {
                            String couponJson = coupon.getCouponJson();
                            AbstractCoupon abstractCoupon = AbstractCoupon.transferTypeToCoupon(coupon.getCouponType(), couponJson);
                            abstractCoupon.calculatePayable(hitOrderList);
                        }
                    }
                }

            }
        }


        //总应付金额
        Double payAble = orderList.stream().collect(Collectors.summingDouble(ShoppingOrder::getOrderAmountTotal));
        //保留两位小数
        payAble = NumberUtil.doubleToFixLengthDouble(payAble, 2);
        for (ShoppingOrder order : orderList) {
            //最终的应付金额,待考虑
            order.setPayable(payAble);

            //订单使用的优惠券
            List<AbstractCoupon> couponList = order.getCouponList();
            String s = JSONUtil.objToStr(couponList);
            order.setOrderCoupon(s);

            shoppingOrderService.add(order);
            ImmediatePaymentOrder immediatePaymentOrder = order.getImmediatePaymentOrder();
            immediatePaymentOrderService.add(immediatePaymentOrder);
            ReceivingGoodsOrder receivingGoodsOrder = immediatePaymentOrder.getReceivingGoodsOrder();
            receivingGoodsOrderService.add(receivingGoodsOrder);
        }


        //返回groupId
        return Result.success("操作成功", groupId);
    }

    /**
     * 试算订单金额
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:20 2019/11/22 0022
     **/
    public Result calculatePrice(OrderAddParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params, Lists.asList("singleOrderList"));
        if (!result.getSuccess()) {
            return result;
        }
        //校验订单中的商品参数
        List<SingleOrderAddParams> singleOrderList = params.getSingleOrderList();
        for (SingleOrderAddParams addParams : singleOrderList) {
            result = PropertyCheckUtil.checkProperty(addParams);
            if (!result.getSuccess()) {
                return result;
            }
        }

        //当前用户
        String userId = commonModuleUserService.currentUserId();
        if (StringUtils.isEmpty(userId)) {
            return Result.notLogin();
        }


        //组订单Id
        String groupId = String.valueOf(IDGenerateFactory.ORDER_ID_UTIL.nextId());
        //当前时间
        Date currentTime = new Date();

        //开始处理订单
        ArrayList<ShoppingOrder> orderList = Lists.newArrayList();
        for (SingleOrderAddParams addParams : singleOrderList) {
            //商品id
            String goodId = addParams.getGoodId();
            //商品数量
            Integer count = addParams.getCount();

            //查询商品信息
            GoodVo goodVo = goodsService.findByGoodId(goodId);
            if (Objects.isNull(goodVo)) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure("商品不存在");
            }
            //判断产品是否存在且上架
            Product product = productService.findByProductId(goodVo.getProductId());
            if (Objects.isNull(product)) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure("产品不存在");
            }
            if (Objects.equals(BooleanConstant.NO_INTEGER, product.getShelfStatus())) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure("产品未上架");
            }

            //订单编号
            String orderNo = String.valueOf(IDGenerateFactory.ORDER_ID_UTIL.nextId());

            ShoppingOrder shoppingOrder = new ShoppingOrder().setOrderNo(orderNo).
                    setCustom(userId).
                    setShops(product.getShops()).
                    setGoodsId(goodId).
                    setCount(count).
                    setState(CommonMallConstants.ORDER_INIT).
                    setProductAmountTotal(goodVo.getPrice() * count).
                    setOrderAmountTotal(goodVo.getPrice() * count).
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

            orderList.add(shoppingOrder);
        }

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
                    //作用的订单
                    List<ShoppingOrder> hitOrderList = orderList.stream().filter(e -> goodIdList.contains(e.getGoodsId())).collect(Collectors.toList());
                    if (Lists.isNotEmpty(hitOrderList)) {
                        String couponJson = coupon.getCouponJson();
                        AbstractCoupon abstractCoupon = AbstractCoupon.transferTypeToCoupon(coupon.getCouponType(), couponJson);
                        abstractCoupon.calculatePayable(hitOrderList);
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
                        List<ShoppingOrder> hitOrderList = orderList.stream().filter(e -> goodIdList.contains(e.getGoodsId())).collect(Collectors.toList());
                        if (Lists.isNotEmpty(hitOrderList)) {
                            String couponJson = coupon.getCouponJson();
                            AbstractCoupon abstractCoupon = AbstractCoupon.transferTypeToCoupon(coupon.getCouponType(), couponJson);
                            abstractCoupon.calculatePayable(hitOrderList);
                        }
                    }
                }

            }
        }


        //总应付金额
        Double payAble = orderList.stream().collect(Collectors.summingDouble(ShoppingOrder::getOrderAmountTotal));
        //保留两位小数
        payAble = NumberUtil.doubleToFixLengthDouble(payAble, 2);

        CalculateOrderPriceVo vo = new CalculateOrderPriceVo();
        vo.setPayAble(payAble);

        //查询各个优惠券的抵扣金额
        List<AbstractCoupon> abstractCouponList = Lists.newArrayList();
        for (ShoppingOrder order : orderList) {
            List<AbstractCoupon> couponList = order.getCouponList();
            if (Lists.isNotEmpty(couponList)) {
                abstractCouponList.addAll(couponList);
            }
        }
        //满数量折扣优惠券抵扣金额
        vo.setCountDiscountPrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.CountDiscountCoupon.getType(), e.getType())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));
        //满数量减优惠券抵扣金额
        vo.setCountSubPrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.CountSubCoupon.getType(), e.getType())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));
        //满价格折扣优惠券抵扣金额
        vo.setPriceDiscountPrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.PriceDiscountCoupon.getType(), e.getType())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));
        //满价格减优惠券抵扣金额
        vo.setPriceSubPrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.PriceSubCoupon.getType(), e.getType())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));
        //积分抵扣金额
        vo.setScorePrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.ScoreCoupon.getType(), e.getType())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));
        //物流费用
        vo.setExpressPrice(abstractCouponList.stream().filter(e -> Objects.equals(CouponTypeEnum.ExpressCoupon.getType(), e.getType())).
                collect(Collectors.summingDouble(AbstractCoupon::getEffectiveMoney)));


        return Result.success("操作成功", vo);
    }


}
