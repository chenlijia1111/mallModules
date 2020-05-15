package com.github.chenlijia1111.commonModule.common.schedules;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.dao.GoodsMapper;
import com.github.chenlijia1111.commonModule.dao.ShoppingOrderMapper;
import com.github.chenlijia1111.commonModule.entity.Goods;
import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.commonModule.service.ICancelOrderHook;
import com.github.chenlijia1111.commonModule.utils.SpringContextHolder;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 任务
 * 订单定时取消策略
 * 延时队列
 *
 * 如果要使用的话就把他注入到spring中去
 * 在项目启动时，没有进行查询了待支付的订单进延时队列，因为设涉及到物流数据的查询,调用者需要自己实现，
 * 我会判断项目是否注入了这个实例，如果注入了才会去查询
 *
 * 取消订单之后会调用取消订单的钩子函数
 * @see com.github.chenlijia1111.commonModule.service.ICancelOrderHook
 * 方便调用者处理其他自定义逻辑
 *
 * @author Chen LiJia
 * @since 2020/4/8
 */
public class OrderCancelTimeLimitTask {

    @Resource
    private ShoppingOrderMapper shoppingOrderMapper;
    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 延时队列，存储未支付订单
     */
    private DelayQueue<DelayNotPayOrder> notPayGroupIdList = new DelayQueue<>();


    public OrderCancelTimeLimitTask() {
        //启动消费线程
        new ConsumeNotPayThread().start();
    }

    /**
     * 添加未支付订单到延时队列
     * 调用场景：下单
     * 调用场景：项目启动，防止项目重启之前的队列数据丢失
     *
     * @param groupId
     * @param createTime
     * @param limitMinutes
     */
    public void addNotPayOrder(String groupId, Date createTime, Integer limitMinutes) {
        if (StringUtils.isNotEmpty(groupId) && Objects.nonNull(createTime) && Objects.nonNull(limitMinutes)) {
            DelayNotPayOrder delayNotPayOrder = new DelayNotPayOrder();
            delayNotPayOrder.groupId = groupId;
            delayNotPayOrder.createTime = createTime;
            delayNotPayOrder.limitTime = new DateTime(createTime.getTime()).plusMinutes(limitMinutes).toDate();
            notPayGroupIdList.put(delayNotPayOrder);
        }
    }


    /**
     * 未支付订单对象
     */
    private class DelayNotPayOrder implements Delayed {

        /**
         * 订单组id
         */
        private String groupId;

        /**
         * 订单创建时间
         */
        private Date createTime;

        /**
         * 最迟支付时间 一般限定15分钟
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
     * 消费线程,处理超时未支付订单，修改取消
     */
    private class ConsumeNotPayThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    DelayNotPayOrder delayNotPayOrder = notPayGroupIdList.take();
                    //查询是否已支付
                    Integer orderState = shoppingOrderMapper.findOrderState(delayNotPayOrder.groupId);
                    if (Objects.equals(orderState, CommonMallConstants.ORDER_INIT)) {
                        List<ShoppingOrder> shoppingOrders = shoppingOrderMapper.listByGroupIdSet(Sets.asSets(delayNotPayOrder.groupId));
                        if (Lists.isNotEmpty(shoppingOrders)) {
                            for (int i = 0; i < shoppingOrders.size(); i++) {
                                ShoppingOrder shoppingOrder = shoppingOrders.get(i);
                                //修改为取消
                                shoppingOrder.setState(CommonMallConstants.ORDER_CANCEL);
                                //取消时间
                                shoppingOrder.setCancelTime(new Date());
                                shoppingOrderMapper.updateByPrimaryKeySelective(shoppingOrder);
                                //回补库存
                                Goods goods = goodsMapper.selectByPrimaryKey(shoppingOrder.getGoodsId());
                                if (Objects.nonNull(goods)) {
                                    goods.setStockCount(goods.getStockCount() + shoppingOrder.getCount());
                                    goodsMapper.updateByPrimaryKeySelective(goods);
                                }
                            }
                            //执行钩子函数
                            try {
                                ICancelOrderHook cancelOrderHook = SpringContextHolder.getBean(ICancelOrderHook.class);
                                cancelOrderHook.cancelOrderByGroupId(delayNotPayOrder.groupId);
                            } catch (Exception e) {
                                //e.printStackTrace();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
