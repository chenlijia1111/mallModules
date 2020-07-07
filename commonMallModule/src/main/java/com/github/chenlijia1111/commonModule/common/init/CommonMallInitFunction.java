package com.github.chenlijia1111.commonModule.common.init;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.responseVo.order.DelayNotEvaluateOrder;
import com.github.chenlijia1111.commonModule.common.responseVo.order.DelayNotPayOrder;
import com.github.chenlijia1111.commonModule.common.responseVo.order.DelayNotReceiveOrder;
import com.github.chenlijia1111.commonModule.common.schedules.AutoClearLimitVerifyCode;
import com.github.chenlijia1111.commonModule.common.schedules.OrderAutoEvaluateTask;
import com.github.chenlijia1111.commonModule.common.schedules.OrderAutoReceiveTask;
import com.github.chenlijia1111.commonModule.common.schedules.OrderCancelTimeLimitTask;
import com.github.chenlijia1111.commonModule.dao.*;
import com.github.chenlijia1111.commonModule.service.impl.*;
import com.github.chenlijia1111.commonModule.utils.SpringContextHolder;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.timer.TimerTaskUtil;
import com.github.chenlijia1111.utils.timer.TriggerUtil;
import org.joda.time.LocalDate;
import org.quartz.Trigger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 初始化方法
 * 定时监听拼团-团解散
 * 查询到达时间限制且还没有拼团成功的拼团,将拼团的状态修改为拼团失败
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/27 0027 上午 9:19
 **/
@Service
public class CommonMallInitFunction implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            //执行方法
            initMaxOrderNo();
            //添加定时清理验证码
            addClearVerifyCodeTask();
            //初始化未支付的订单到延时队列进行处理
            initNotPayOrderList();
            //初始化未评价的订单到延时队列进行处理
            initNotEvaluateOrderList();
            //初始化未收货，但是物流已经签收的订单到延时队列进行处理
            initNotReceiveOrderList();
        }
    }

    /**
     * 初始化未评价的订单到延时队列进行处理
     */
    private void initNotEvaluateOrderList() {
        OrderAutoEvaluateTask orderAutoEvaluateTask = null;
        try {
            orderAutoEvaluateTask = SpringContextHolder.getBean(OrderAutoEvaluateTask.class);
            if (Objects.nonNull(orderAutoEvaluateTask)) {
                ShoppingOrderMapper shoppingOrderMapper = SpringContextHolder.getBean(ShoppingOrderMapper.class);
                //查询已收货未评价的订单
                List<DelayNotEvaluateOrder> delayNotEvaluateOrders = shoppingOrderMapper.listDelayNotEvaluateOrder();
                if (Lists.isNotEmpty(delayNotEvaluateOrders)) {
                    for (int i = 0; i < delayNotEvaluateOrders.size(); i++) {
                        DelayNotEvaluateOrder delayNotEvaluateOrder = delayNotEvaluateOrders.get(i);
                        orderAutoEvaluateTask.addNotReceiveOrder(delayNotEvaluateOrder.getOrderNo(), delayNotEvaluateOrder.getReceiveTime(), CommonMallConstants.NOT_EVALUATE_ORDER_LIMIT_MINUTES);
                    }
                }
            }

        } catch (Exception e) {
            //没注入
//            e.printStackTrace();
        }


    }


    /**
     * 初始化未收货，但是物流已经签收的订单到延时队列进行处理
     */
    private void initNotReceiveOrderList() {
        OrderAutoReceiveTask orderAutoReceiveTask = null;
        try {
            orderAutoReceiveTask = SpringContextHolder.getBean(OrderAutoReceiveTask.class);
            if (Objects.nonNull(orderAutoReceiveTask)) {
                ShoppingOrderMapper shoppingOrderMapper = SpringContextHolder.getBean(ShoppingOrderMapper.class);
                //查询已收货未评价的订单
                List<DelayNotReceiveOrder> delayNotReceiveOrders = shoppingOrderMapper.listDelayNotReceiveOrder();
                if (Lists.isNotEmpty(delayNotReceiveOrders)) {
                    for (int i = 0; i < delayNotReceiveOrders.size(); i++) {
                        DelayNotReceiveOrder delayNotReceiveOrder = delayNotReceiveOrders.get(i);
                        orderAutoReceiveTask.addNotReceiveOrder(delayNotReceiveOrder.getOrderNo(), delayNotReceiveOrder.getSignTime(), CommonMallConstants.NOT_RECEIVE_ORDER_LIMIT_MINUTES);
                    }
                }
            }

        } catch (Exception e) {
            //没注入
//            e.printStackTrace();
        }


    }

    /**
     * 初始化订单流水号
     *
     * @return void
     * @since 上午 9:52 2019/11/27 0027
     **/
    private void initMaxOrderNo() {
        ShoppingOrderMapper orderMapper = SpringContextHolder.getBean(ShoppingOrderMapper.class);
        ImmediatePaymentOrderMapper immediatePaymentOrderMapper = SpringContextHolder.getBean(ImmediatePaymentOrderMapper.class);
        ReceivingGoodsOrderMapper receivingGoodsOrderMapper = SpringContextHolder.getBean(ReceivingGoodsOrderMapper.class);
        ReturnGoodsOrderMapper returnGoodsOrderMapper = SpringContextHolder.getBean(ReturnGoodsOrderMapper.class);
        ProductMapper productMapper = SpringContextHolder.getBean(ProductMapper.class);

        //组订单编号
        String maxGroupId = orderMapper.maxGroupId();
        AtomicInteger groupIdSerialNum = createInitOrderAtomicInteger(maxGroupId);
        GroupIdGeneratorServiceImpl.currentNumber = groupIdSerialNum;

        //商家组订单编号
        String maxShopGroupId = orderMapper.maxShopGroupId();
        AtomicInteger shopGroupIdSerialNum = createInitOrderAtomicInteger(maxShopGroupId);
        ShopGroupIdGeneratorServiceImpl.currentNumber = shopGroupIdSerialNum;

        //订单编号
        String maxOrderNo = orderMapper.maxOrderNo();
        AtomicInteger orderNoSerialNum = createInitOrderAtomicInteger(maxOrderNo);
        ShoppingIdGeneratorServiceImpl.currentNumber = orderNoSerialNum;

        //发货单编号
        String maxSendOrderNo = immediatePaymentOrderMapper.maxOrderNo();
        AtomicInteger sendOrderNoSerialNum = createInitOrderAtomicInteger(maxSendOrderNo);
        SendIdGeneratorServiceImpl.currentNumber = sendOrderNoSerialNum;

        //收货单编号
        String maxReceiveOrderNo = receivingGoodsOrderMapper.maxOrderNo();
        AtomicInteger receiveOrderNoSerialNum = createInitOrderAtomicInteger(maxReceiveOrderNo);
        ReceiveIdGeneratorServiceImpl.currentNumber = receiveOrderNoSerialNum;

        //退货单编号
        String maxReturnOrderNo = returnGoodsOrderMapper.maxOrderNo();
        AtomicInteger returnOrderNoSerialNum = createInitOrderAtomicInteger(maxReturnOrderNo);
        ReturnIdGeneratorServiceImpl.currentNumber = returnOrderNoSerialNum;

        //产品id编号
        String maxProductNo = productMapper.maxProductNo();
        AtomicInteger productNoSerailNum = createInitOrderAtomicInteger(maxProductNo);
        ProductIdGeneratorServiceImpl.currentNumber = productNoSerailNum;
    }


    /**
     * 根据单号生成当日当前流水号
     * 订单单号组成规则
     * 一位代号 + 年月日 + 6位流水
     * 如 120200111000001
     *
     * @param orderNo
     * @return
     */
    private AtomicInteger createInitOrderAtomicInteger(String orderNo) {
        if (StringUtils.isNotEmpty(orderNo) && orderNo.length() == 15) {
            //判断年月日,判断是否是今天
            String substring = orderNo.substring(1, 9);
            if (Objects.equals(substring, LocalDate.now().toString("yyyyMMdd"))) {
                //是今天
                //判断流水号
                String serialNum = orderNo.substring(9);
                Integer integer = Integer.valueOf(serialNum);
                return new AtomicInteger(integer);
            }
        }
        return new AtomicInteger(0);
    }


    /**
     * 添加定时清理验证码定时任务
     */
    private void addClearVerifyCodeTask() {
        String groupName = "autoClearCode";

        //每天3点运行
        Trigger autoClearTrigger = TriggerUtil.createCronTrigger("0 0 3 * * ?", "autoClearCodeTrigger", groupName);

        TimerTaskUtil.doTask(autoClearTrigger, AutoClearLimitVerifyCode.class, AutoClearLimitVerifyCode.class.getSimpleName(), groupName);
    }

    /**
     * 初始化未支付的订单到延时队列进行处理
     */
    private void initNotPayOrderList() {
        try {
            OrderCancelTimeLimitTask task = SpringContextHolder.getBean(OrderCancelTimeLimitTask.class);
            if (Objects.nonNull(task)) {
                ShoppingOrderMapper shoppingOrderMapper = SpringContextHolder.getBean(ShoppingOrderMapper.class);
                List<DelayNotPayOrder> delayNotPayOrders = shoppingOrderMapper.listDelayNotPayOrder();
                if (Lists.isNotEmpty(delayNotPayOrders)) {
                    for (int i = 0; i < delayNotPayOrders.size(); i++) {
                        DelayNotPayOrder delayNotPayOrder = delayNotPayOrders.get(i);
                        task.addNotPayOrder(delayNotPayOrder.getGroupId(), delayNotPayOrder.getCreateTime(),
                                CommonMallConstants.CANCEL_NOT_PAY_ORDER_LIMIT_MINUTES);
                    }
                }
            }
        } catch (Exception e) {
            //没有获取到bean说明没有注入
        }

    }

}
