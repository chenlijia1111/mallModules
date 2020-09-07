package com.github.chenlijia1111.commonModule.common.schedules;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.dao.ReceivingGoodsOrderMapper;
import com.github.chenlijia1111.commonModule.dao.ShoppingOrderMapper;
import com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder;
import com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder;
import com.github.chenlijia1111.commonModule.service.IAutoReceiveOrderHook;
import com.github.chenlijia1111.commonModule.utils.SpringContextHolder;
import com.github.chenlijia1111.utils.core.StringUtils;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 订单隔指定时间自动收货
 * <p>
 * 一般情况都是根据物流情况，物流签收之后，隔一定时间如果还没没有收货就自动收货
 * <p>
 * 如果要使用的话就把他注入到spring中去
 * 在项目启动时，已经进行查询了待收货的订单进延时队列了，调用者不用自己实现，
 * {@link ShoppingOrderMapper#listDelayNotReceiveOrder()} 查询已签收但是没收货的订单，
 * 调用者需要定时查询物流状态，修改发货单的签收状态为已签收，并把数据放到延时队列
 * {@link ImmediatePaymentOrder#getExpressSignStatus()}
 * {@link ImmediatePaymentOrder#getExpressSignTime()}
 * 我会判断项目是否注入了这个实例，如果注入了才会去查询
 *
 * @author Chen LiJia
 * @since 2020/5/15
 */
public class OrderAutoReceiveTask implements IOrderAutoTask {

    @Resource
    private ReceivingGoodsOrderMapper receivingGoodsOrderMapper;//收货单

    @Override
    public void autoDealDelayOrder(OrderDelayPojo pojo) {
        if (Objects.equals(pojo.getOrderAutoTaskClass(), this.getClass())) {
            String orderNo = pojo.getOrderNo();
            //查询是否已收货
            ReceivingGoodsOrder receiveOrder = receivingGoodsOrderMapper.findReceiveOrderByOrderNo(orderNo);
            if (Objects.nonNull(receiveOrder) && Objects.equals(receiveOrder.getState(), CommonMallConstants.ORDER_INIT)) {
                //还没有收货
                //把它修改为已收货
                receiveOrder.setState(CommonMallConstants.ORDER_COMPLETE);
                receiveOrder.setReceiveTime(new Date());
                receivingGoodsOrderMapper.updateByPrimaryKeySelective(receiveOrder);

                try {
                    //执行自动收货之后的钩子函数
                    IAutoReceiveOrderHook autoReceiveOrderHook = SpringContextHolder.getBean(IAutoReceiveOrderHook.class);
                    autoReceiveOrderHook.receiveHook(orderNo);
                } catch (Exception e) {
                    //没有注入，找不到钩子实现对象
                }
            }
        }
    }


}
