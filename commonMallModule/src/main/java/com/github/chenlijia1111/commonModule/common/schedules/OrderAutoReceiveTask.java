package com.github.chenlijia1111.commonModule.common.schedules;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.dao.ReceivingGoodsOrderMapper;
import com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder;
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
 * 在项目启动时，已经进行查询了待支付的订单进延时队列了，调用者不用自己实现，
 * 我会判断项目是否注入了这个实例，如果注入了才会去查询
 *
 * @author Chen LiJia
 * @since 2020/5/15
 */
public class OrderAutoReceiveTask {

    @Resource
    private ReceivingGoodsOrderMapper receivingGoodsOrderMapper;//收货单

    /**
     * 延时队列，存储未收货订单
     */
    private DelayQueue<DelayNotReceiveOrder> notReceiveOrderList = new DelayQueue<>();

    public OrderAutoReceiveTask() {
        new ConsumeNotReceiveThread().start();
    }

    /**
     * 添加未收货的订单到延时队列去
     * 添加时机 一般为 订单物流签收以后
     * 为了防止之前的队列数据在重启之后丢失，应该在重启的时候从数据库查询哪些是物流已签收的数据，并重新放入队列
     * @param orderNo
     * @param signTime
     * @param limitMinutes
     */
    public void addNotReceiveOrder(String orderNo, Date signTime, Integer limitMinutes) {
        if (StringUtils.isNotEmpty(orderNo) && Objects.nonNull(signTime) && Objects.nonNull(limitMinutes)) {
            DelayNotReceiveOrder delayNotReceiveOrder = new DelayNotReceiveOrder();
            delayNotReceiveOrder.orderNo = orderNo;
            delayNotReceiveOrder.signTime = signTime;
            delayNotReceiveOrder.limitTime = new DateTime(signTime.getTime()).plusMinutes(limitMinutes).toDate();
            notReceiveOrderList.put(delayNotReceiveOrder);
        }
    }


    /**
     * 未收货订单对象
     */
    private class DelayNotReceiveOrder implements Delayed {

        /**
         * 订单编号
         */
        private String orderNo;

        /**
         * 订单物流签收时间
         */
        private Date signTime;

        /**
         * 最迟确认收货时间
         */
        private Date limitTime;


        /**
         * 核心方法，根据这个方法来延时等待
         * 延时的时间是根据时间差来进行返回的
         *
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {

            //时间差--毫秒 MILLISECONDS
            long delayOfMilliSeconds = limitTime.getTime() - System.currentTimeMillis();

            //延时队列默认使用 NANOSECONDS 进行调用  纳秒
            if (Objects.equals(TimeUnit.NANOSECONDS, unit)) {
                return delayOfMilliSeconds * 1000 * 1000L;
            }
            if (Objects.equals(TimeUnit.MILLISECONDS, unit)) {
                return delayOfMilliSeconds;
            }
            if (Objects.equals(TimeUnit.SECONDS, unit)) {
                return delayOfMilliSeconds / 1000;
            }
            //默认返回分钟
            return delayOfMilliSeconds / 1000 / 60;
        }

        /**
         * 通过这个比较判断谁最先出队
         * 默认是从小到大排序的
         *
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
            Long l = limitTime.getTime() - System.currentTimeMillis();
            return l.intValue();
        }
    }


    /**
     * 消费线程,处理超时未收货订单，修改为收货
     */
    private class ConsumeNotReceiveThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    OrderAutoReceiveTask.DelayNotReceiveOrder delayNotReceiveOrder = notReceiveOrderList.take();
                    //查询是否已收货
                    ReceivingGoodsOrder receiveOrder = receivingGoodsOrderMapper.findReceiveOrderByOrderNo(delayNotReceiveOrder.orderNo);
                    if (Objects.nonNull(receiveOrder) && Objects.equals(receiveOrder.getState(), CommonMallConstants.ORDER_INIT)) {
                        //还没有收货
                        //把它修改为已收货
                        receiveOrder.setState(CommonMallConstants.ORDER_COMPLETE);
                        receiveOrder.setReceiveTime(new Date());
                        receivingGoodsOrderMapper.updateByPrimaryKeySelective(receiveOrder);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
