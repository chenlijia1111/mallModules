package com.github.chenlijia1111.commonModule.common.schedules;

import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.dao.EvaluationMapper;
import com.github.chenlijia1111.commonModule.dao.GoodsMapper;
import com.github.chenlijia1111.commonModule.dao.ReceivingGoodsOrderMapper;
import com.github.chenlijia1111.commonModule.dao.ShoppingOrderMapper;
import com.github.chenlijia1111.commonModule.entity.Evaluation;
import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.IDUtil;
import com.github.chenlijia1111.utils.core.JSONUtil;
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
 * 订单隔指定时间自动评价
 * <p>
 * 一般情况都是根据订单收货时间开始 间隔一定时间 自动好评
 * <p>
 * 如果要使用的话就把他注入到spring中去
 * 在项目启动时，已经进行查询了待支付的订单进延时队列了，调用者不用自己实现，
 * 我会判断项目是否注入了这个实例，如果注入了才会去查询
 *
 * @author Chen LiJia
 * @since 2020/5/15
 */
public class OrderAutoEvaluateTask {

    @Resource
    private ShoppingOrderMapper shoppingOrderMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private ReceivingGoodsOrderMapper receivingGoodsOrderMapper;//收货单
    @Resource
    private EvaluationMapper evaluationMapper;//评价

    /**
     * 默认好评内容
     */
    private String defaultEvaluateComment = "此用户未填写评价内容";

    /**
     * 好评级别 星级
     */
    private Double defaultEvaluateLevel = 5.0;


    /**
     * 延时队列，存储未评价订单
     */
    private DelayQueue<DelayNotEvaluateOrder> notEvaluateOrderList = new DelayQueue<>();

    /**
     * 构造器
     *
     * @param defaultEvaluateComment 默认好评内容
     * @param defaultEvaluateLevel   默认评价级别 星级
     */
    public OrderAutoEvaluateTask(String defaultEvaluateComment, Double defaultEvaluateLevel) {

        //默认好评内容
        if (StringUtils.isNotEmpty(defaultEvaluateComment)) {
            this.defaultEvaluateComment = defaultEvaluateComment;
        }
        //默认评价级别
        if (Objects.nonNull(defaultEvaluateLevel)) {
            this.defaultEvaluateLevel = defaultEvaluateLevel;
        }


        new ConsumeNotEvaluateThread().start();
    }

    /**
     * 添加未评价的订单到延时队列去
     * 添加时机 一般为 订单确认收货以后
     * 为了防止之前的队列数据在重启之后丢失，应该在重启的时候从数据库查询哪些是已确认收货的数据，并重新放入队列
     *
     * @param orderNo
     * @param receiveTime
     * @param limitMinutes
     */
    public void addNotReceiveOrder(String orderNo, Date receiveTime, Integer limitMinutes) {
        if (StringUtils.isNotEmpty(orderNo) && Objects.nonNull(receiveTime) && Objects.nonNull(limitMinutes)) {
            DelayNotEvaluateOrder delayNotEvaluateOrder = new DelayNotEvaluateOrder();
            delayNotEvaluateOrder.orderNo = orderNo;
            delayNotEvaluateOrder.rceiveTime = receiveTime;
            delayNotEvaluateOrder.limitTime = new DateTime(receiveTime.getTime()).plusMinutes(limitMinutes).toDate();
            notEvaluateOrderList.put(delayNotEvaluateOrder);
        }
    }


    /**
     * 未评价订单对象
     */
    private class DelayNotEvaluateOrder implements Delayed {

        /**
         * 订单编号
         */
        private String orderNo;

        /**
         * 订单确认收货时间
         */
        private Date rceiveTime;

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
    private class ConsumeNotEvaluateThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    OrderAutoEvaluateTask.DelayNotEvaluateOrder delayNotReceiveOrder = notEvaluateOrderList.take();
                    //查询是否已评价
                    List<Evaluation> evaluationList = evaluationMapper.listByOrderNoSet(Sets.asSets(delayNotReceiveOrder.orderNo));
                    if (Lists.isEmpty(evaluationList)) {
                        //还没有评价，默认好评
                        //查询订单信息
                        ShoppingOrder shoppingOrder = shoppingOrderMapper.selectByPrimaryKey(delayNotReceiveOrder.orderNo);
                        if (Objects.nonNull(shoppingOrder)) {
                            //查询产品id
                            String detailsJson = shoppingOrder.getDetailsJson();
                            AdminProductVo adminProductVo = JSONUtil.strToObj(detailsJson, AdminProductVo.class);
                            //添加评价
                            String evaluateId = String.valueOf(IDGenerateFactory.EVALUATION_ID_UTIL.nextId());
                            Evaluation evaluation = new Evaluation().setId(evaluateId).
                                    setClientId(shoppingOrder.getCustom()).
                                    setShopId(shoppingOrder.getShops()).
                                    setOrderNo(shoppingOrder.getOrderNo()).
                                    setGoodId(shoppingOrder.getGoodsId()).
                                    setProductId(adminProductVo.getId()).
                                    setComment(defaultEvaluateComment).
                                    setProductLevel(defaultEvaluateLevel).
                                    setShopLevel(defaultEvaluateLevel).
                                    setServiceLevel(defaultEvaluateLevel).
                                    setExpressLevel(defaultEvaluateLevel).
                                    setCreateTime(new Date()).
                                    setDeleteStatus(BooleanConstant.NO_INTEGER);
                            evaluationMapper.insertSelective(evaluation);
                            //评价之后，订单状态修改已完成
                            shoppingOrder.setCompleteStatus(BooleanConstant.YES_INTEGER);
                            shoppingOrderMapper.updateByPrimaryKeySelective(shoppingOrder);
                        }

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
