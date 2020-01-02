package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.commonModule.common.enums.OrderStatusEnum;
import com.github.chenlijia1111.commonModule.common.enums.ReturnTypeEnum;
import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.requestVo.returnOrder.ReturnOrderApplyParams;
import com.github.chenlijia1111.commonModule.common.requestVo.returnOrder.ReturnShipParams;
import com.github.chenlijia1111.commonModule.common.requestVo.returnOrder.ShopHandleParams;
import com.github.chenlijia1111.commonModule.common.requestVo.returnOrder.ShopRefundParams;
import com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder;
import com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder;
import com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder;
import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.commonModule.service.*;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 退货退款
 * 用户相对于一个订单逻辑上可以申请多个退货，一直申请一直申请,
 * 所以调用者需要在申请退货的时候做限制,只有申请处理了才可以继续下一个申请
 * 在查询订单的退货状态的时候都是直接根据最后一个退货单的状态来进行判断的
 * 对于发货、收货，对于大部分的商城来说，这个发货收货都是可有可无，
 * 可以在线下沟通完成
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/22 0022 下午 5:07
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "ReturnOrderBiz")
public class ReturnOrderBiz {

    @Autowired
    private ReturnGoodsOrderServiceI returnGoodsOrderService;//退货单
    @Autowired
    private ImmediatePaymentOrderServiceI immediatePaymentOrderService;//发货单
    @Autowired
    private ReceivingGoodsOrderServiceI receivingGoodsOrderService;//收货单
    @Autowired
    private ShoppingOrderServiceI shoppingOrderService;//订单


    /**
     * 用户申请退货退款
     *
     * @param params
     * @param returnOrderNoGenerator  退货单单号生成规则
     * @param sendOrderNoGenerator    发货单单号生成规则
     * @param receiveOrderNoGenerator 收货单单号生成规则
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:13 2019/11/22 0022
     **/
    public Result applyReturnGoodsAndMoney(ReturnOrderApplyParams params, OrderIdGeneratorServiceI returnOrderNoGenerator,
                                           OrderIdGeneratorServiceI sendOrderNoGenerator, OrderIdGeneratorServiceI receiveOrderNoGenerator) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断订单是否存在
        ShoppingOrder order = shoppingOrderService.findByOrderNo(params.getOrderNo());
        if (Objects.isNull(order)) {
            return Result.failure("订单不存在");
        }
        //判断订单的状态,之后已收货的订单才可以申请退货退款
        Map<String, Integer> orderStateMap = shoppingOrderService.findOrderStateByOrderNoSet(Sets.asSets(params.getOrderNo()));
        Integer orderStatus = orderStateMap.get(params.getOrderNo());
        //不合法的订单状态
        List<Integer> illegalStatus = Lists.asList(OrderStatusEnum.INIT.getOrderStatus(),
                OrderStatusEnum.CANCEL.getOrderStatus(),
                OrderStatusEnum.PAYED.getOrderStatus(),
                OrderStatusEnum.SEND.getOrderStatus());
        if (illegalStatus.contains(orderStatus)) {
            return Result.failure("订单不合法,只有已收货的订单才可以退货退款");
        }

        Date currentTime = new Date();

        //退货单
        String returnNo = returnOrderNoGenerator.createOrderNo();
        ReturnGoodsOrder returnGoodsOrder = new ReturnGoodsOrder().
                setReOrderNo(returnNo).
                setCustom(order.getCustom()).
                setShops(order.getShops()).
                setReCreateTime(currentTime).
                setReExplain(params.getReturnReason()).
                setReFund(order.getOrderAmountTotal()).
                setCheckStatus(CommonMallConstants.ORDER_INIT).
                setShoppingOrder(order.getOrderNo()).
                setFrontOrder(order.getOrderNo()).
                setReFundStatus(CommonMallConstants.ORDER_INIT).
                setState(CommonMallConstants.ORDER_INIT).
                setReType(ReturnTypeEnum.RETURN_GOOD_AND_MONEY.getReturnType());
        returnGoodsOrderService.add(returnGoodsOrder);

        //添加发货单
        //发货单单号
        String sendOrderNo = sendOrderNoGenerator.createOrderNo();
        ImmediatePaymentOrder immediatePaymentOrder = new ImmediatePaymentOrder().
                setOrderNo(sendOrderNo).
                setCustom(order.getCustom()).
                setShops(order.getShops()).
                setState(CommonMallConstants.ORDER_INIT).
                setFrontOrder(returnNo).
                setShoppingOrder(order.getOrderNo()).
                setCreateTime(currentTime);
        immediatePaymentOrderService.add(immediatePaymentOrder);

        //添加收货单
        //收货单单号
        String receiveOrderNo = receiveOrderNoGenerator.createOrderNo();
        ReceivingGoodsOrder receivingGoodsOrder = new ReceivingGoodsOrder().
                setOrderNo(receiveOrderNo).
                setCustom(order.getCustom()).
                setShops(order.getShops()).
                setState(CommonMallConstants.ORDER_INIT).
                setShoppingOrder(order.getOrderNo()).
                setImmediatePaymentOrder(sendOrderNo).
                setFrontOrder(sendOrderNo).
                setCreateTime(currentTime);
        receivingGoodsOrderService.add(receivingGoodsOrder);

        return Result.success("操作成功");
    }

    /**
     * 用户申请退货
     *
     * @param params
     * @param returnOrderNoGenerator  退货单单号生成规则
     * @param sendOrderNoGenerator    发货单单号生成规则
     * @param receiveOrderNoGenerator 收货单单号生成规则
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:13 2019/11/22 0022
     **/
    public Result applyReturnGoods(ReturnOrderApplyParams params, OrderIdGeneratorServiceI returnOrderNoGenerator,
                                   OrderIdGeneratorServiceI sendOrderNoGenerator, OrderIdGeneratorServiceI receiveOrderNoGenerator) {
        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断订单是否存在
        ShoppingOrder order = shoppingOrderService.findByOrderNo(params.getOrderNo());
        if (Objects.isNull(order)) {
            return Result.failure("订单不存在");
        }
        //判断订单的状态,之后已收货的订单才可以申请退货退款
        Map<String, Integer> orderStateMap = shoppingOrderService.findOrderStateByOrderNoSet(Sets.asSets(params.getOrderNo()));
        Integer orderStatus = orderStateMap.get(params.getOrderNo());
        //不合法的订单状态
        List<Integer> illegalStatus = Lists.asList(OrderStatusEnum.INIT.getOrderStatus(),
                OrderStatusEnum.CANCEL.getOrderStatus(),
                OrderStatusEnum.PAYED.getOrderStatus(),
                OrderStatusEnum.SEND.getOrderStatus());
        if (illegalStatus.contains(orderStatus)) {
            return Result.failure("订单不合法,只有已收货的订单才可以退货");
        }

        Date currentTime = new Date();

        //退货单
        String returnNo = returnOrderNoGenerator.createOrderNo();
        ReturnGoodsOrder returnGoodsOrder = new ReturnGoodsOrder().
                setReOrderNo(returnNo).
                setCustom(order.getCustom()).
                setShops(order.getShops()).
                setReCreateTime(currentTime).
                setReExplain(params.getReturnReason()).
                setReFund(order.getOrderAmountTotal()).
                setCheckStatus(CommonMallConstants.ORDER_INIT).
                setShoppingOrder(order.getOrderNo()).
                setFrontOrder(order.getOrderNo()).
                setReFundStatus(CommonMallConstants.ORDER_INIT).
                setState(CommonMallConstants.ORDER_INIT).
                setReType(ReturnTypeEnum.RETURN_GOOD.getReturnType());
        returnGoodsOrderService.add(returnGoodsOrder);

        //添加发货单
        //发货单单号
        String sendOrderNo = sendOrderNoGenerator.createOrderNo();
        ImmediatePaymentOrder immediatePaymentOrder = new ImmediatePaymentOrder().
                setOrderNo(sendOrderNo).
                setCustom(order.getCustom()).
                setShops(order.getShops()).
                setState(CommonMallConstants.ORDER_INIT).
                setFrontOrder(returnNo).
                setShoppingOrder(order.getOrderNo()).
                setCreateTime(currentTime);
        immediatePaymentOrderService.add(immediatePaymentOrder);

        //添加收货单
        //收货单单号
        String receiveOrderNo = receiveOrderNoGenerator.createOrderNo();
        ReceivingGoodsOrder receivingGoodsOrder = new ReceivingGoodsOrder().
                setOrderNo(receiveOrderNo).
                setCustom(order.getCustom()).
                setShops(order.getShops()).
                setState(CommonMallConstants.ORDER_INIT).
                setShoppingOrder(order.getOrderNo()).
                setImmediatePaymentOrder(sendOrderNo).
                setFrontOrder(sendOrderNo).
                setCreateTime(currentTime);
        receivingGoodsOrderService.add(receivingGoodsOrder);

        return Result.success("操作成功");
    }

    /**
     * 用户申请退款
     *
     * @param params
     * @param returnOrderNoGenerator 退货单单号生成规则
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:14 2019/11/22 0022
     **/
    public Result applyReturnMoney(ReturnOrderApplyParams params, OrderIdGeneratorServiceI returnOrderNoGenerator) {
        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断订单是否存在
        ShoppingOrder order = shoppingOrderService.findByOrderNo(params.getOrderNo());
        if (Objects.isNull(order)) {
            return Result.failure("订单不存在");
        }
        //判断订单的状态,之后已收货的订单才可以申请退货退款
        Map<String, Integer> orderStateMap = shoppingOrderService.findOrderStateByOrderNoSet(Sets.asSets(params.getOrderNo()));
        Integer orderStatus = orderStateMap.get(params.getOrderNo());
        //不合法的订单状态
        List<Integer> illegalStatus = Lists.asList(OrderStatusEnum.INIT.getOrderStatus(),
                OrderStatusEnum.CANCEL.getOrderStatus(),
                OrderStatusEnum.PAYED.getOrderStatus());
        if (illegalStatus.contains(orderStatus)) {
            return Result.failure("订单不合法,只有已付款的订单才可以退货");
        }

        Date currentTime = new Date();

        //退货单
        String returnNo = returnOrderNoGenerator.createOrderNo();
        ReturnGoodsOrder returnGoodsOrder = new ReturnGoodsOrder().
                setReOrderNo(returnNo).
                setCustom(order.getCustom()).
                setShops(order.getShops()).
                setReCreateTime(currentTime).
                setReExplain(params.getReturnReason()).
                setReFund(order.getOrderAmountTotal()).
                setCheckStatus(CommonMallConstants.ORDER_INIT).
                setShoppingOrder(order.getOrderNo()).
                setFrontOrder(order.getOrderNo()).
                setReFundStatus(CommonMallConstants.ORDER_INIT).
                setState(CommonMallConstants.ORDER_INIT).
                setReType(ReturnTypeEnum.RETURN_MONEY.getReturnType());
        returnGoodsOrderService.add(returnGoodsOrder);
        return Result.success("操作成功");
    }

    /**
     * 用户取消退货退款
     * 具体的取消限制需要具体的系统进行制定,这里不做详细的限定
     *
     * @param returnOrderNo 退货编号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:14 2019/11/22 0022
     **/
    public Result customCancelApply(String returnOrderNo) {

        //校验参数
        if (StringUtils.isEmpty(returnOrderNo)) {
            return Result.failure("退货单单号为空");
        }

        //判断退货单是否存在
        ReturnGoodsOrder returnGoodsOrder = returnGoodsOrderService.findByReturnNo(returnOrderNo);
        if (Objects.isNull(returnGoodsOrder)) {
            return Result.failure("退货单不存在");
        }

        if (Objects.equals(CommonMallConstants.ORDER_CANCEL, returnGoodsOrder.getState())) {
            return Result.failure("操作失败,该退货单已取消");
        }

        returnGoodsOrder.setState(CommonMallConstants.ORDER_CANCEL);
        return returnGoodsOrderService.update(returnGoodsOrder);
    }

    /**
     * 用户发货
     *
     * @param params
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:14 2019/11/22 0022
     **/
    public Result customSendExpress(ReturnShipParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断退货单是否存在
        ReturnGoodsOrder returnGoodsOrder = returnGoodsOrderService.findByReturnNo(params.getReturnOrderNo());
        if (Objects.isNull(returnGoodsOrder)) {
            return Result.failure("退货单不存在");
        }

        //判断该订单是否是退货订单,否则无法发货
        if (Objects.equals(ReturnTypeEnum.RETURN_MONEY.getReturnType(), returnGoodsOrder.getReType())) {
            return Result.failure("退款订单无法发货");
        }

        //查询发货单
        List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderService.listByFrontNoSet(Sets.asSets(returnGoodsOrder.getReOrderNo()));
        if (Lists.isEmpty(immediatePaymentOrders)) {
            //正常情况是不会出现这种情况的
            //出现了这种情况可能是有人手动清空了数据库
            return Result.failure("发货单不存在");
        }

        ImmediatePaymentOrder immediatePaymentOrder = immediatePaymentOrders.get(0);
        //发货
        immediatePaymentOrder.setState(CommonMallConstants.ORDER_COMPLETE);
        immediatePaymentOrder.setExpressName(params.getExpressName());
        immediatePaymentOrder.setExpressCom(params.getExpressCom());
        immediatePaymentOrder.setExpressNo(params.getExpressNo());
        immediatePaymentOrder.setSendTime(new Date());

        return immediatePaymentOrderService.update(immediatePaymentOrder);
    }

    /**
     * 商家同意申请
     *
     * @param params
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:14 2019/11/22 0022
     **/
    public Result shopAgreeApply(ShopHandleParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断退货单是否存在
        ReturnGoodsOrder returnGoodsOrder = returnGoodsOrderService.findByReturnNo(params.getReturnOrderNo());
        if (Objects.isNull(returnGoodsOrder)) {
            return Result.failure("退货单不存在");
        }

        //判断当前退货单状态,只有初始状态的退货单才可以进行操作
        if (!Objects.equals(CommonMallConstants.ORDER_INIT, returnGoodsOrder.getState())) {
            return Result.failure("操作失败,只有初始状态的退货订单才可以进行审核");
        }

        returnGoodsOrder.setState(CommonMallConstants.ORDER_COMPLETE);
        returnGoodsOrder.setCheckStatus(CommonMallConstants.ORDER_COMPLETE);
        //处理时间
        returnGoodsOrder.setReHandleTime(new Date());
        //处理结果
        returnGoodsOrder.setReReason(params.getHandleResult());

        return returnGoodsOrderService.update(returnGoodsOrder);
    }

    /**
     * 商家拒绝申请
     *
     * @param params
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:14 2019/11/22 0022
     **/
    public Result shopDenyApply(ShopHandleParams params) {
        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断退货单是否存在
        ReturnGoodsOrder returnGoodsOrder = returnGoodsOrderService.findByReturnNo(params.getReturnOrderNo());
        if (Objects.isNull(returnGoodsOrder)) {
            return Result.failure("退货单不存在");
        }

        //判断当前退货单状态,只有初始状态的退货单才可以进行操作
        if (!Objects.equals(CommonMallConstants.ORDER_INIT, returnGoodsOrder.getState())) {
            return Result.failure("操作失败,只有初始状态的退货订单才可以进行审核");
        }

        returnGoodsOrder.setState(CommonMallConstants.ORDER_CANCEL);
        returnGoodsOrder.setCheckStatus(CommonMallConstants.ORDER_CANCEL);
        //处理时间
        returnGoodsOrder.setReHandleTime(new Date());
        //拒绝原因
        returnGoodsOrder.setReReason(params.getHandleResult());

        return returnGoodsOrderService.update(returnGoodsOrder);
    }

    /**
     * 商家收货
     *
     * @param returnOrderNo 退货单单号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:15 2019/11/22 0022
     **/
    public Result shopReceiveExpress(String returnOrderNo) {

        //校验参数
        if (StringUtils.isEmpty(returnOrderNo)) {
            return Result.failure("退货单单号为空");
        }

        //判断退货单是否存在
        ReturnGoodsOrder returnGoodsOrder = returnGoodsOrderService.findByReturnNo(returnOrderNo);
        if (Objects.isNull(returnGoodsOrder)) {
            return Result.failure("退货单不存在");
        }

        //判断该订单是否是退货订单,否则无法发货
        if (Objects.equals(ReturnTypeEnum.RETURN_MONEY.getReturnType(), returnGoodsOrder.getReType())) {
            return Result.failure("退款订单无法收货");
        }

        //查询发货单
        List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderService.listByFrontNoSet(Sets.asSets(returnGoodsOrder.getReOrderNo()));
        if (Lists.isEmpty(immediatePaymentOrders)) {
            //正常情况是不会出现这种情况的
            //出现了这种情况可能是有人手动清空了数据库
            return Result.failure("发货单不存在");
        }
        ImmediatePaymentOrder immediatePaymentOrder = immediatePaymentOrders.get(0);
        //判断是否发货,未发货时不允许收货的
        if (!Objects.equals(CommonMallConstants.ORDER_COMPLETE, immediatePaymentOrder.getState())) {
            return Result.failure("退货订单未发货,无法收货");
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

        receivingGoodsOrder.setState(CommonMallConstants.ORDER_COMPLETE);

        Result update = receivingGoodsOrderService.update(receivingGoodsOrder);
        return update;
    }

    /**
     * 商家退款
     *
     * @param params
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:15 2019/11/22 0022
     **/
    public Result shopRefundMoney(ShopRefundParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断退货单是否存在
        ReturnGoodsOrder returnGoodsOrder = returnGoodsOrderService.findByReturnNo(params.getReturnOrderNo());
        if (Objects.isNull(returnGoodsOrder)) {
            return Result.failure("退货单不存在");
        }

        //判断订单退款状态,订单只能退一次款,不可以重复退款
        ReturnGoodsOrder returnOrderCondition = new ReturnGoodsOrder().setFrontOrder(returnGoodsOrder.getFrontOrder());
        List<ReturnGoodsOrder> returnGoodsOrders = returnGoodsOrderService.listByCondition(returnOrderCondition);
        Optional<ReturnGoodsOrder> any = returnGoodsOrders.stream().filter(e -> Objects.equals(e.getReFundStatus(), CommonMallConstants.ORDER_COMPLETE)).findAny();
        if (any.isPresent()) {
            return Result.failure("该订单已退款,不允许重复退款");
        }

        //进行退款
        returnGoodsOrder.setReFundStatus(CommonMallConstants.ORDER_COMPLETE);
        returnGoodsOrder.setReturnPayNo(params.getRefundPayNo());
        returnGoodsOrder.setReturnPayTime(new Date());

        return returnGoodsOrderService.update(returnGoodsOrder);
    }


}
