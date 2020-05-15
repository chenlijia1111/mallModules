package com.github.chenlijia1111.commonModule.service;

/**
 * 取消订单钩子函数
 *
 * @author Chen LiJia
 * @since 2020/5/15
 */
public interface ICancelOrderHook {

    /**
     * 根据groupId取消订单的钩子函数
     *
     * @param groupId
     */
    void cancelOrderByGroupId(String groupId);

    /**
     * 根据orderNo取消订单的钩子函数
     *
     * @param orderNo
     */
    void cancelOrderByOrderNo(String orderNo);

}
