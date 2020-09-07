package com.github.chenlijia1111.commonModule.common.schedules;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 订单延时对象
 * 用来判断订单是否需要出队
 * @author Chen LiJia
 * @since 2020/9/5
 */
public class OrderDelayPojo implements Delayed {

    /**
     * 具体的处理实现类
     */
    private Class<? extends IOrderAutoTask> orderAutoTaskClass;

    /**
     * 订单编号
     * 这个编号是 orderNo 还是 shopGroupNo 还是 groupNo
     * 由实现者自己定义约束
     */
    private String orderNo;

    /**
     * 限制时间
     * 即出队时间
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
        OrderDelayPojo that = (OrderDelayPojo) o;
        return this.limitTime.compareTo(that.limitTime);
    }

    public Class<? extends IOrderAutoTask> getOrderAutoTaskClass() {
        return orderAutoTaskClass;
    }

    public void setOrderAutoTaskClass(Class<? extends IOrderAutoTask> orderAutoTaskClass) {
        this.orderAutoTaskClass = orderAutoTaskClass;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Date limitTime) {
        this.limitTime = limitTime;
    }
}
