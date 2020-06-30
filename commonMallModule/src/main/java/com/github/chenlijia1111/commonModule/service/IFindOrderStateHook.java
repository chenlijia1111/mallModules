package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;

import java.util.Map;
import java.util.Set;

/**
 * 查询订单状态 钩子
 * 如果调用者需要自定义订单状态
 * 比如如下情况，系统中存在预订单，预订单支付了定金之后，仍然属于待支付的状态
 * 而不是仅仅判断 {@link ShoppingOrder#getState()}
 * @author Chen LiJia
 * @since 2020/6/30
 */
public interface IFindOrderStateHook {

    /**
     * 通过订单编号集合查询订单状态 钩子
     * @param orderNoSet
     * @param statueMap
     * @return
     */
    Map<String, Integer> findOrderStateByOrderNoSet(Set<String> orderNoSet,Map<String, Integer> statueMap);


    /**
     * 通过组订单id集合查询组订单状态 钩子
     * @param groupIdSet
     * @param statueMap
     * @return
     */
    Map<String, Integer> findGroupStateByGroupIdSet(Set<String> groupIdSet,Map<String, Integer> statueMap);

}
