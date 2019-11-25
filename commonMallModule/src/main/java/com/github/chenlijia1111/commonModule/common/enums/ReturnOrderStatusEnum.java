package com.github.chenlijia1111.commonModule.common.enums;

/**
 * 退货订单状态枚举
 * 订单的评价与完成各个系统的定义不一样,需要调用者定制化实现,这里只定义状态
 * 1初始化 2客户端取消 3商家端拒绝 4商家端同意 5客户端发货 6商家端收货 7商家端打款
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/25 0025 上午 9:45
 **/
public enum ReturnOrderStatusEnum {

    INIT(1), //初始化状态
    CUSTOM_CANCEL(2), //客户端取消
    SHOP_DENY(3), //商家端拒绝
    SHOP_AGREE(4), //商家端同意
    CUSTOM_SEND(5), //客户端发货
    CUSTOM_RECEIVE(6), //商家端收货
    SHOP_REFUND(7), //商家端打款
    ;

    ReturnOrderStatusEnum(Integer returnOrderStatus) {
        this.returnOrderStatus = returnOrderStatus;
    }

    private Integer returnOrderStatus;

    public Integer getReturnOrderStatus() {
        return returnOrderStatus;
    }
}
