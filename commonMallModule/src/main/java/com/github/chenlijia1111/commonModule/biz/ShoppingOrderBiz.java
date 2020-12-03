package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.commonModule.common.enums.OrderTypeEnum;
import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.pojo.coupon.AbstractCoupon;
import com.github.chenlijia1111.commonModule.common.requestVo.order.*;
import com.github.chenlijia1111.commonModule.common.responseVo.order.CalculateOrderPriceVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.commonModule.common.schedules.OrderAutoEvaluateTask;
import com.github.chenlijia1111.commonModule.common.schedules.OrderAutoTasks;
import com.github.chenlijia1111.commonModule.common.schedules.OrderCancelTimeLimitTask;
import com.github.chenlijia1111.commonModule.entity.*;
import com.github.chenlijia1111.commonModule.service.*;
import com.github.chenlijia1111.commonModule.utils.BigDecimalUtil;
import com.github.chenlijia1111.commonModule.utils.SpringContextHolder;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单
 * 订单付款以及付款之后的回调,需要调用者自己实现
 * <p>
 * 调用者在调用这些方法前可以对这些方法再进行一次封装,进行其他的一些定制操作,
 * 比如在订单之后计算分销等等
 *
 * @author chenLiJia
 * @see OrderCancelTimeLimitTask 超时未支付自动取消订单，这里已经实现了，调用者只需注入即可
 * 设置超时时间 {@link CommonMallConstants#CANCEL_NOT_PAY_ORDER_LIMIT_MINUTES}
 * 这里为取消订单设置了钩子函数，调用者可以把自己需要在取消的业务实现的逻辑(像回补库存之类的业务)写在{@link ICancelOrderHook} 中
 *
 * <p>
 * 下单后一段时间内自动收货  这里已经实现了，调用者只需要注入即可，因为可能涉及物流的查询，一般都是物流查询签收了之后一段时间内自动收货
 * 所以调用者需要定时查询物流情况，物流签收了之后就需要修改发货单的物流签收状态以及物流签收时间
 * {@link ImmediatePaymentOrder#getExpressSignStatus()} {@link ImmediatePaymentOrder#getExpressSignTime()} ()}
 * @see com.github.chenlijia1111.commonModule.common.schedules.OrderAutoReceiveTask
 * <p>
 * 确认收货后一段时间内自动评价 这里已经实现了，调用者只需要注入即可
 * @see OrderAutoEvaluateTask
 * @since 2019-11-05 16:39:24
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "ShoppingOrderBiz")
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
    private ProductSnapshotServiceI productSnapshotService;// 产品快照信息
    @Resource
    private CommonModuleUserServiceI commonModuleUserService;//用户
    @Autowired
    private CouponServiceI couponService;//优惠券


    /**
     * 下单是否需要判断库存
     * 默认是需要判断
     * 因为有一些场景没库存也能买，比如预订单这些，可以付定金购买 CHECK_GOOD_STOCK_STATUS
     * 默认要检查
     */
    public static Integer CHECK_GOOD_STOCK_STATUS = BooleanConstant.YES_INTEGER;

    /**
     * 下单之后是否需要加入到待支付处理队列中去
     * 因为有些特殊的系统可能有些特别，需要自己单独处理
     */
    public static Integer ADD_DELAY_NOT_PAY_AFTER_ADD_ORDER_STATUS = BooleanConstant.YES_INTEGER;


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
     * @param params                   1
     * @param groupIdGenerateImpl      组订单单号生成规则
     * @param shoppingIdGenerateImpl   购物订单单号生成规则
     * @param sendIdGenerateImpl       发货订单单号生成规则
     * @param receiveIdGenerateImpl    收货订单单号生成规则
     * @param shopGroupIdGeneratorImpl 商家组订单号生成规则
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:53 2019/11/5 0005
     **/
    @Transactional
    public Result add(OrderAddParams params, IdGeneratorServiceI groupIdGenerateImpl,
                      IdGeneratorServiceI shoppingIdGenerateImpl, IdGeneratorServiceI sendIdGenerateImpl,
                      IdGeneratorServiceI receiveIdGenerateImpl, IdGeneratorServiceI shopGroupIdGeneratorImpl) {

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

        // 校验数据，判断商品信息是否都存在
        Set<String> goodIdSet = singleOrderList.stream().map(e -> e.getGoodId()).collect(Collectors.toSet());
        List<GoodVo> goodVoList = goodsService.listByGoodIdSet(goodIdSet);
        // 查询对应的产品信息列表，判断产品是否都存在
        Set<String> productIdSet = goodVoList.stream().map(e -> e.getProductId()).collect(Collectors.toSet());
        List<Product> productList = productService.listByProductIdSet(productIdSet);
        // 查询产品快照信息，避免单个查询消耗时间
        List<ProductSnapshot> productSnapshotList = productSnapshotService.listByProductIdSet(productIdSet);


        //组订单Id
        String groupId = groupIdGenerateImpl.createOrderNo();
        //当前时间
        Date currentTime = new Date();
        //商家组订单id集合-用于判断是否生成过了商家组订单号,防止重复生成
        Map<String, String> shopGroupIdMap = new HashMap<>();

        //开始处理订单
        ArrayList<ShoppingOrder> orderList = Lists.newArrayList();
        for (SingleOrderAddParams addParams : singleOrderList) {
            //商品id
            String goodId = addParams.getGoodId();
            //商品数量
            BigDecimal count = addParams.getCount();

            //这个商品所有的下单数量，允许参数中多个相同的 goodId ，但是要判断库存
            BigDecimal goodTotalCount = new BigDecimal("0.0");
            for (SingleOrderAddParams singleOrderAddParams : singleOrderList) {
                goodTotalCount = BigDecimalUtil.add(goodTotalCount, singleOrderAddParams.getCount());
            }

            //查询商品信息
            GoodVo goodVo = goodVoList.stream().filter(e -> Objects.equals(e.getId(), goodId)).findAny().orElse(null);
            if (Objects.isNull(goodVo)) {
                return Result.failure("商品不存在");
            }
            //判断产品是否存在且上架
            String productId = goodVo.getProductId();
            Product product = productList.stream().filter(e -> Objects.equals(e.getId(), productId)).findAny().orElse(null);
            if (Objects.isNull(product) || Objects.equals(BooleanConstant.YES_INTEGER, product.getDeleteStatus())) {
                return Result.failure("产品不存在");
            }
            if (Objects.equals(BooleanConstant.NO_INTEGER, product.getShelfStatus())) {
                return Result.failure("产品未上架");
            }

            //判断库存是否充足
            if (Objects.equals(BooleanConstant.YES_INTEGER, CHECK_GOOD_STOCK_STATUS) &&
                    goodVo.getStockCount().compareTo(goodTotalCount) < 0) {
                return Result.failure("商品库存不足");
            }

            //订单编号
            String orderNo = shoppingIdGenerateImpl.createOrderNo();
            //商家组订单编号
            String shopGroupId = shopGroupIdMap.get(product.getShops());
            if (Objects.isNull(shopGroupId)) {
                shopGroupId = shopGroupIdGeneratorImpl.createOrderNo();
                shopGroupIdMap.put(product.getShops(), shopGroupId);
            }

            //商品金额
            BigDecimal productAmountTotal = BigDecimalUtil.mul(goodVo.getPrice(), count);

            ShoppingOrder shoppingOrder = new ShoppingOrder().setOrderNo(orderNo).
                    setCustom(userId).
                    setShops(product.getShops()).
                    setGoodsId(goodId).
                    setCount(count).
                    setState(CommonMallConstants.ORDER_INIT).
                    setOrderType(OrderTypeEnum.ORDINARY_ORDER.getType()).
                    setProductAmountTotal(productAmountTotal).
                    setGoodPrice(goodVo.getPrice()).
                    setOrderAmountTotal(productAmountTotal).
                    setShopGroupId(shopGroupId).
                    setGroupId(groupId).
                    setCreateTime(currentTime).setRemarks(params.getRemarks()).
                    setDeleteStatus(BooleanConstant.NO_INTEGER).
                    setCompleteStatus(BooleanConstant.NO_INTEGER).
                    setOrderType(params.getOrderType()).
                    setSingleOrderAppend(addParams.getSingleOrderAppend());

            //订单快照--2020-12-03 从快照表里查询
            String productSnapshotId = productSnapshotList.stream().filter(e -> Objects.equals(e.getProductId(), productId)).findAny().map(e -> String.valueOf(e.getId())).orElse(null);
            // 如果没有快照信息，就存 null
            // 存快照关联的 id ，这样可以避免订单表过大
            shoppingOrder.setDetailsJson(productSnapshotId);

            //订单备注
            shoppingOrder.setRemarks(params.getRemarks());
            //订单对应的商品详情
            shoppingOrder.setGoodsVO(goodVo);

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
                    setCreateTime(currentTime).
                    setExpressSignStatus(BooleanConstant.NO_INTEGER);

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
                    Coupon coupon = any.get();
                    //找出符合条件的订单进行计算
                    //作用的订单
                    List<ShoppingOrder> hitOrderList = orderList.stream().filter(e -> goodIdList.contains(e.getGoodsId())).collect(Collectors.toList());
                    if (Lists.isNotEmpty(hitOrderList)) {
                        String couponJson = coupon.getCouponJson();
                        AbstractCoupon abstractCoupon = AbstractCoupon.transferTypeToCoupon(couponJson);
                        abstractCoupon.calculatePayable(hitOrderList);
                    }
                }

            }
        }


        //总应付金额
        BigDecimal payAble = new BigDecimal(0.0);
        for (ShoppingOrder order : orderList) {
            payAble = payAble.add(order.getOrderAmountTotal());
        }
        for (ShoppingOrder order : orderList) {
            //最终的应付金额,待考虑
            order.setPayable(payAble);

            //订单使用的优惠券
            List<AbstractCoupon> couponList = order.getCouponList();
            String s = JSONUtil.objToStr(couponList);
            order.setOrderCoupon(s);
        }

        //批量插入数据
        shoppingOrderService.batchAdd(orderList);
        List<ImmediatePaymentOrder> immediatePaymentOrders = orderList.stream().map(e -> e.getImmediatePaymentOrder()).collect(Collectors.toList());
        immediatePaymentOrderService.batchAdd(immediatePaymentOrders);
        List<ReceivingGoodsOrder> receivingGoodsOrders = immediatePaymentOrders.stream().map(e -> e.getReceivingGoodsOrder()).collect(Collectors.toList());
        receivingGoodsOrderService.batchAdd(receivingGoodsOrders);

        //修改库存
        for (ShoppingOrder order : orderList) {

            //下单成功之后,减库存
            GoodVo goodsVO = order.getGoodsVO();
            BigDecimal stockCount = goodsVO.getStockCount();
            stockCount = BigDecimalUtil.divide(stockCount, order.getCount());
            Goods goods = new Goods().setId(goodsVO.getId()).
                    setStockCount(stockCount);
            goodsService.update(goods);
        }


        //操作成功，添加订单到延时队列，超时未支付取消订单
        if (Objects.equals(ADD_DELAY_NOT_PAY_AFTER_ADD_ORDER_STATUS, BooleanConstant.YES_INTEGER)) {
            OrderAutoTasks.addOrderDelay(groupId, currentTime, CommonMallConstants.CANCEL_NOT_PAY_ORDER_LIMIT_MINUTES, OrderCancelTimeLimitTask.class);
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


        //组订单Id 只做计算，不消耗实际id
        String groupId = String.valueOf(IDGenerateFactory.ORDER_ID_UTIL.nextId());
        //当前时间
        Date currentTime = new Date();

        //开始处理订单
        ArrayList<ShoppingOrder> orderList = Lists.newArrayList();
        for (SingleOrderAddParams addParams : singleOrderList) {
            //商品id
            String goodId = addParams.getGoodId();
            //商品数量
            BigDecimal count = addParams.getCount();

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

            //订单编号 只做计算，不消耗实际id
            String orderNo = String.valueOf(IDGenerateFactory.ORDER_ID_UTIL.nextId());

            //商品金额
            BigDecimal productAmountTotal = BigDecimalUtil.mul(goodVo.getPrice(), count);

            ShoppingOrder shoppingOrder = new ShoppingOrder().setOrderNo(orderNo).
                    setCustom(userId).
                    setShops(product.getShops()).
                    setGoodsId(goodId).
                    setCount(count).
                    setState(CommonMallConstants.ORDER_INIT).
                    setOrderType(OrderTypeEnum.ORDINARY_ORDER.getType()).
                    setProductAmountTotal(productAmountTotal).
                    setGoodPrice(goodVo.getPrice()).
                    setOrderAmountTotal(productAmountTotal).
                    setGroupId(groupId).
                    setCreateTime(currentTime).setRemarks(params.getRemarks()).
                    setDeleteStatus(BooleanConstant.NO_INTEGER).
                    setCompleteStatus(BooleanConstant.NO_INTEGER).
                    setOrderType(params.getOrderType());

            //订单快照
            AdminProductVo adminProductVo = productService.findAdminProductVoByProductId(goodVo.getProductId());
            String productSnapshot = JSONUtil.objToStr(adminProductVo);
            shoppingOrder.setDetailsJson(productSnapshot);

            //订单备注
            shoppingOrder.setRemarks(params.getRemarks());
            //订单对应的商品详情
            shoppingOrder.setGoodsVO(goodVo);

            orderList.add(shoppingOrder);
        }

        //计算各种优惠券,结算最终应付金额
        List<CouponWithGoodIds> couponWithGoodIdsList = params.getCouponWithGoodIdsList();
        if (Lists.isNotEmpty(couponWithGoodIdsList)) {
            //优惠券id集合
            Set<String> couponIdSet = couponWithGoodIdsList.stream().filter(e -> StringUtils.isNotEmpty(e.getCouponId())).map(e -> e.getCouponId()).collect(Collectors.toSet());
            if (Sets.isNotEmpty(couponIdSet)) {
                List<Coupon> coupons = couponService.listByIdSet(couponIdSet);
                if (Lists.isNotEmpty(coupons)) {
                    for (CouponWithGoodIds couponWithGoodId : couponWithGoodIdsList) {
                        //优惠券Id
                        String couponId = couponWithGoodId.getCouponId();
                        //优惠券作用的商品id集合
                        List<String> goodIdList = couponWithGoodId.getGoodIdList();

                        Optional<Coupon> any = coupons.stream().filter(e -> Objects.equals(e.getId(), couponId)).findAny();
                        if (any.isPresent()) {
                            Coupon coupon = any.get();
                            //找出符合条件的订单进行计算
                            //作用的订单
                            List<ShoppingOrder> hitOrderList = orderList.stream().filter(e -> goodIdList.contains(e.getGoodsId())).collect(Collectors.toList());
                            if (Lists.isNotEmpty(hitOrderList)) {
                                String couponJson = coupon.getCouponJson();
                                AbstractCoupon abstractCoupon = AbstractCoupon.transferTypeToCoupon(couponJson);
                                abstractCoupon.calculatePayable(hitOrderList);
                            }
                        }
                    }

                }
            }
        }


        //总应付金额
        BigDecimal payAble = new BigDecimal(0.0);
        for (ShoppingOrder order : orderList) {
            payAble = payAble.add(order.getOrderAmountTotal());
        }

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

        //费用详情信息map
        Map<String, BigDecimal> feeMap = new HashMap<>();
        for (AbstractCoupon coupon : abstractCouponList) {
            String couponImplClassName = coupon.getCouponImplClassName();
            BigDecimal couponMoney = feeMap.get(couponImplClassName);
            if (Objects.isNull(couponMoney)) {
                couponMoney = new BigDecimal(0);
            }

            couponMoney = couponMoney.add(coupon.getEffectiveMoney());
            feeMap.put(couponImplClassName, couponMoney);
        }
        vo.setFeeMap(feeMap);


        return Result.success("操作成功", vo);
    }

    /**
     * 客户取消订单
     * 客户主动取消订单,只能是待支付的订单
     * 并且是组订单全部取消
     *
     * @param groupId         1
     * @param canCancelStatus 可以取消的订单状态
     * @return com.github.chenlijia1111.utils.common.Result
     * @see com.github.chenlijia1111.commonModule.common.enums.OrderStatusEnum
     * @since 下午 5:51 2019/11/22 0022
     **/
    public Result customCancelOrder(String groupId, List<Integer> canCancelStatus) {

        //校验参数
        if (StringUtils.isEmpty(groupId) || Lists.isEmpty(canCancelStatus)) {
            return Result.failure("参数不合法");
        }

        Result result = shoppingOrderService.cancelOrder(groupId, canCancelStatus);
        if (result.getSuccess()) {
            //执行钩子函数
            try {
                ICancelOrderHook cancelOrderHook = SpringContextHolder.getBean(ICancelOrderHook.class);
                cancelOrderHook.cancelOrderByGroupId(groupId);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 客户取消订单
     * 客户主动取消订单,只能是待支付或者已完成的订单
     *
     * @param orderNo         1
     * @param canCancelStatus 可以取消的订单状态
     * @return com.github.chenlijia1111.utils.common.Result
     * @see com.github.chenlijia1111.commonModule.common.enums.OrderStatusEnum
     * @since 下午 5:51 2019/11/22 0022
     **/
    public Result customCancelOrderByOrderNo(String orderNo, List<Integer> canCancelStatus) {

        //校验参数
        if (StringUtils.isEmpty(orderNo) || Lists.isEmpty(canCancelStatus)) {
            return Result.failure("参数不合法");
        }

        Result result = shoppingOrderService.cancelOrderByOrderNo(orderNo, canCancelStatus);
        if (result.getSuccess()) {
            //执行钩子函数
            try {
                ICancelOrderHook cancelOrderHook = SpringContextHolder.getBean(ICancelOrderHook.class);
                cancelOrderHook.cancelOrderByOrderNo(orderNo);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 商家发货
     *
     * @param params
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:51 2019/11/22 0022
     **/
    public Result shopSendExpress(ShipParams params) {
        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断订单是否存在
        ShoppingOrder shoppingOrder = shoppingOrderService.findByOrderNo(params.getOrderNo());
        if (Objects.isNull(shoppingOrder)) {
            return Result.failure("订单不存在");
        }

        //查询该订单的发货单
        List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderService.listByFrontNoSet(Sets.asSets(params.getOrderNo()));
        if (Lists.isEmpty(immediatePaymentOrders)) {
            //如果发货单不存在,说明这个订单有问题，可能是下单的时候重启或者其他状况导致发货单不存在
            return Result.failure("发货单不存在");
        }

        ImmediatePaymentOrder immediatePaymentOrder = immediatePaymentOrders.get(0);

        //判断是不是已经发过货了
        if (Objects.equals(CommonMallConstants.ORDER_COMPLETE, immediatePaymentOrder.getState())) {
            return Result.failure("该订单已经发过货了");
        }

        //发货
        immediatePaymentOrder.setState(CommonMallConstants.ORDER_COMPLETE);
        immediatePaymentOrder.setExpressName(params.getExpressName());
        immediatePaymentOrder.setExpressCom(params.getExpressCom());
        immediatePaymentOrder.setExpressNo(params.getExpressNo());
        immediatePaymentOrder.setSendTime(new Date());

        return immediatePaymentOrderService.update(immediatePaymentOrder);
    }

    /**
     * 商家批量发货
     *
     * @param params
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:51 2019/11/22 0022
     **/
    @Transactional
    public Result shopBatchSendExpress(BatchShipParams params) {
        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        List<String> orderNos = params.getOrderNos();
        for (String orderNo : orderNos) {
            ShipParams shipParams = new ShipParams().setOrderNo(orderNo).
                    setExpressCom(params.getExpressCom()).
                    setExpressName(params.getExpressName()).
                    setExpressNo(params.getExpressNo());
            Result sendExpress = shopSendExpress(shipParams);
            if (!sendExpress.getSuccess()) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return sendExpress;
            }
        }

        return Result.success("操作成功");
    }

    /**
     * 客户收货
     *
     * @param orderNo 订单编号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:51 2019/11/22 0022
     **/
    public Result customReceiveExpress(String orderNo) {

        if (Objects.isNull(orderNo)) {
            return Result.failure("订单编号为空");
        }

        //查询订单判断是否存在
        ShoppingOrder shoppingOrder = shoppingOrderService.findByOrderNo(orderNo);
        if (Objects.isNull(shoppingOrder)) {
            return Result.failure("订单不存在");
        }

        //查询发货单与收货单,判断收货单是否已收货,在本系统中收货就是完成的意思
        //查询发货单
        List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderService.listByFrontNoSet(Sets.asSets(orderNo));
        if (Lists.isEmpty(immediatePaymentOrders)) {
            //如果发货单不存在,说明这个订单有问题，可能是下单的时候重启或者其他状况导致发货单不存在
            return Result.failure("发货单不存在");
        }

        ImmediatePaymentOrder immediatePaymentOrder = immediatePaymentOrders.get(0);
        //判断是否已发货,没有发货是不允许收货的
        if (!Objects.equals(CommonMallConstants.ORDER_COMPLETE, immediatePaymentOrder.getState())) {
            return Result.failure("订单未发货,无法收货");
        }

        //查询收货单
        List<ReceivingGoodsOrder> receivingGoodsOrders = receivingGoodsOrderService.listByFrontNoSet(Sets.asSets(immediatePaymentOrder.getOrderNo()));
        if (Lists.isEmpty(receivingGoodsOrders)) {
            //如果收货单不存在,说明这个订单有问题，可能是下单的时候重启或者其他状况导致发货单不存在
            return Result.failure("收货单不存在");
        }

        ReceivingGoodsOrder receivingGoodsOrder = receivingGoodsOrders.get(0);

        //判断是否已经收过货了
        if (Objects.equals(CommonMallConstants.ORDER_COMPLETE, receivingGoodsOrder.getState())) {
            return Result.failure("该订单已收货,请勿重复请求");
        }

        //当前时间
        Date currentTime = new Date();
        receivingGoodsOrder.setState(CommonMallConstants.ORDER_COMPLETE);
        receivingGoodsOrder.setReceiveTime(currentTime);

        Result update = receivingGoodsOrderService.update(receivingGoodsOrder);

        if (update.getSuccess()) {
            //收货之后，添加到自动评价延时队列
            OrderAutoTasks.addOrderDelay(orderNo, currentTime, CommonMallConstants.NOT_EVALUATE_ORDER_LIMIT_MINUTES, OrderAutoEvaluateTask.class);
        }
        return update;
    }


    /**
     * 支付回调
     * 只处理关于普通订单的回调业务,不处理其他不相关业务(拼团、秒杀)
     *
     * @param payRecode     商家支付订单号
     * @param transactionId 第三方支付交易流水号
     * @param payChannel    支付渠道
     * @return void
     * @since 上午 9:57 2019/11/27 0027
     **/
    public void payNotify(String payRecode, String transactionId, String payChannel) {
        ShoppingOrder shoppingOrderCondition = new ShoppingOrder().setPayRecord(payRecode);
        List<ShoppingOrder> shoppingOrders = shoppingOrderService.listByCondition(shoppingOrderCondition);
        if (Lists.isNotEmpty(shoppingOrders)) {
            Date currentTime = new Date();
            for (ShoppingOrder shoppingOrder : shoppingOrders) {
                shoppingOrder.setTransactionId(transactionId);
                shoppingOrder.setPayChannel(payChannel);
                shoppingOrder.setPayTime(currentTime);
                shoppingOrder.setState(CommonMallConstants.ORDER_COMPLETE);
                shoppingOrderService.update(shoppingOrder);
            }
        }
    }


    /**
     * 删除订单
     *
     * @param orderNo
     * @param canDeleteOrderStatus 可以删除的订单状态
     * @return
     * @see com.github.chenlijia1111.commonModule.common.enums.OrderStatusEnum
     */
    public Result deleteOrder(String orderNo, List<Integer> canDeleteOrderStatus) {

        //校验参数
        if (StringUtils.isEmpty(orderNo) || Lists.isEmpty(canDeleteOrderStatus)) {
            return Result.failure("参数不合法");
        }

        ShoppingOrder shoppingOrder = shoppingOrderService.findByOrderNo(orderNo);
        if (Objects.isNull(shoppingOrder)) {
            return Result.failure("订单不存在");
        }

        Map<String, Integer> orderStateMap = shoppingOrderService.findOrderStateByOrderNoSet(Sets.asSets(orderNo));
        Integer orderStatus = orderStateMap.get(orderNo);
        if (!canDeleteOrderStatus.contains(orderStatus)) {
            return Result.failure("操作失败");
        }

        shoppingOrder.setDeleteStatus(BooleanConstant.YES_INTEGER);
        return shoppingOrderService.update(shoppingOrder);
    }

}
