package com.github.chenlijia1111.commonModule.service;

/**
 * 订单编号生成策略
 * 约定订单类型
 * 订单类型代号 1代表组订单 2代表订单编号 3代表发货单 4代表收货单 5代表退货单 6秒杀 7拼团 8商家组订单
 * 9产品id
 *
 * @author Chen LiJia
 * @since 2019/12/30
 */
public interface IdGeneratorServiceI {

    /**
     * 订单生成策略
     * @return
     */
    String createOrderNo();

}
