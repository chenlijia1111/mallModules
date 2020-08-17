package com.github.chenlijia1111.commonModule.service;

/**
 * 自动收货钩子
 * 可以在自动收货之后执行一些操作
 *
 * @author Chen LiJia
 * @since 2020/8/8
 */
public interface IAutoReceiveOrderHook {

    /**
     * 自动收货执行之后的钩子
     *
     * @param orderNo
     */
    void receiveHook(String orderNo);

}
