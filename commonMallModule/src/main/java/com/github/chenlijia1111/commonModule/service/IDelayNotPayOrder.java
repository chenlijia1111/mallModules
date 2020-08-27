package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.common.responseVo.order.DelayNotPayOrder;

import java.util.List;

/**
 * 接口约束
 * 查询待支付订单
 *
 * 系统会有一个默认实现，如果调用者有特殊需求，待支付订单可能比较特殊
 * 可以另外实现一个实现类并注入 spring 即可，系统会进行判断
 * 优先使用调用者的实现类
 * @author Chen LiJia
 * @since 2020/8/27
 */
public interface IDelayNotPayOrder {

    /**
     * 列出未支付的订单
     * 用处：在系统启动的时候获取未支付的订单放入延时队列中去，等待处理
     * @return
     */
    List<DelayNotPayOrder> listDelayNotPayOrder();

}
