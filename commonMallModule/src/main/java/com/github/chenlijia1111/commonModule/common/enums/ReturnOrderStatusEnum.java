package com.github.chenlijia1111.commonModule.common.enums;

/**
 * 退货订单状态枚举
 * -1未退款 1初始化 2客户端取消 3商家端拒绝 4商家端同意 5客户端发货 6商家端收货 7完成
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/25 0025 上午 9:45
 **/
public enum ReturnOrderStatusEnum {

    NOT_RETURN(-1), //未退款
    INIT(1), //初始化状态
    CUSTOM_CANCEL(2), //客户端取消
    SHOP_DENY(3), //商家端拒绝
    SHOP_AGREE(4), //商家端同意
    CUSTOM_SEND(5), //客户端发货
    SHOP_RECEIVE(6), //商家端收货
    COMPLETE(7), //完成  退货根据商家是否收货判断是否已完成 退款根据是否退款来判断是否完成
    ;

    ReturnOrderStatusEnum(Integer returnOrderStatus) {
        this.returnOrderStatus = returnOrderStatus;
    }

    private Integer returnOrderStatus;

    public Integer getReturnOrderStatus() {
        return returnOrderStatus;
    }
}
