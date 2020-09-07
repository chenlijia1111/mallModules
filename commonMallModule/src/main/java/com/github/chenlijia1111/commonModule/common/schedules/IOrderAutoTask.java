package com.github.chenlijia1111.commonModule.common.schedules;

/**
 * 订单自动延时处理任务接口
 * 实现当前接口，并注入 spring
 * 系统将会获取对应实现者，使用责任链默认是延时订单数据进行处理
 * 如延时未支付(自动取消)，延时未收货(自动收货)，延时未评价(自动评价)
 * @author Chen LiJia
 * @since 2020/9/5
 */
public interface IOrderAutoTask {

    /**
     * 处理逻辑
     * @param pojo
     */
    void autoDealDelayOrder(OrderDelayPojo pojo);

}
