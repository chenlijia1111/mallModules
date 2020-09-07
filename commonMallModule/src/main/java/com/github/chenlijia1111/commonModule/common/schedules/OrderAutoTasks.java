package com.github.chenlijia1111.commonModule.common.schedules;

import com.github.chenlijia1111.commonModule.utils.SpringContextHolder;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.DelayQueue;

/**
 * 订单自动处理 执行者
 * 调用者使用这个类进行添加延时数据
 *
 * @author Chen LiJia
 * @since 2020/9/5
 */
public class OrderAutoTasks {

    /**
     * 延时队列，存储待处理的订单数据
     */
    private static DelayQueue<OrderDelayPojo> delayQueue = new DelayQueue<>();

    /**
     * 处理队列数据的实现
     * 懒加载获取
     */
    private static List<IOrderAutoTask> orderAutoTaskList = null;

    /**
     * 是否初始化第一次添加延时数据
     * 如果是的话，启用线程消费数据
     */
    private static boolean initAddData = true;

    /**
     * 添加延时订单处理数据
     *
     * @param orderNo
     * @param startTime
     * @param limitMinutes
     */
    public static void addOrderDelay(String orderNo, Date startTime, Integer limitMinutes, Class<? extends IOrderAutoTask> orderAutoTaskClass) {
        if (StringUtils.isNotEmpty(orderNo) && Objects.nonNull(startTime) && Objects.nonNull(limitMinutes) && Objects.nonNull(orderAutoTaskClass)) {
            //构造数据
            OrderDelayPojo pojo = new OrderDelayPojo();
            pojo.setOrderNo(orderNo);
            pojo.setOrderAutoTaskClass(orderAutoTaskClass);

            //计算出队时间
            Date limitTime = new DateTime(startTime.getTime()).plusMinutes(limitMinutes).toDate();
            pojo.setLimitTime(limitTime);

            delayQueue.put(pojo);

            if (initAddData) {
                //是初始化
                //启动消费线程
                initAddData = false;
                new ConsumeNotEvaluateThread().start();
            }
        }
    }


    /**
     * 消费线程,处理超时未收货订单，修改为收货
     */
    private static class ConsumeNotEvaluateThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    OrderDelayPojo delayPojo = delayQueue.take();
                    //使用责任链模式进行处理
                    if (Lists.isEmpty(orderAutoTaskList)) {
                        //使用 spring 进行获取 bean
                        Map<String, IOrderAutoTask> beansOfType = SpringContextHolder.getBeansOfType(IOrderAutoTask.class);
                        if (Objects.nonNull(beansOfType)) {
                            for (IOrderAutoTask value : beansOfType.values()) {
                                orderAutoTaskList.add(value);
                            }
                        }
                    }

                    //开始处理
                    if (Lists.isNotEmpty(orderAutoTaskList)) {
                        for (IOrderAutoTask orderAutoTask : orderAutoTaskList) {
                            orderAutoTask.autoDealDelayOrder(delayPojo);
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}





