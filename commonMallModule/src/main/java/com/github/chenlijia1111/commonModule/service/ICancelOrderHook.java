package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;

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
    Result cancelOrderByGroupId(String groupId);

    /**
     * 根据shopGroupId取消订单的钩子函数
     *
     * @param shopGroupId
     */
    Result cancelOrderByShopGroupId(String shopGroupId);

    /**
     * 根据orderNo取消订单的钩子函数
     *
     * @param orderNo
     */
    Result cancelOrderByOrderNo(String orderNo);

}
