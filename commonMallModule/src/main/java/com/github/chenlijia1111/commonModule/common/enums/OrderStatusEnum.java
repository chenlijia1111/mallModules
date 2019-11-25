package com.github.chenlijia1111.commonModule.common.enums;

/**
 * 订单状态枚举
 * 订单的评价与完成各个系统的定义不一样,需要调用者定制化实现,这里只定义状态
 * 1初始化 2取消 3已付款 4已发货 5已收货 6已评价 7已完成
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/25 0025 上午 9:45
 **/
public enum OrderStatusEnum {

    INIT(1), //初始化状态
    CANCEL(2), //取消
    PAYED(3), //已付款
    SEND(4), //已发货
    RECEIVED(5), //已收货
    EVALUAED(6), //已评价
    COMPLETED(7), //已完成
    ;

    OrderStatusEnum(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    private Integer orderStatus;

    public Integer getOrderStatus() {
        return orderStatus;
    }
}
