package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.utils.common.Result;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
public interface ShoppingOrderServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result add(ShoppingOrder params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result update(ShoppingOrder params);

    /**
     * 条件查询订单
     *
     * @param condition 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ShoppingOrder>
     * @since 上午 10:54 2019/11/6 0006
     **/
    List<ShoppingOrder> listByCondition(ShoppingOrder condition);

    /**
     * 条件统计数量
     * @since 上午 11:58 2019/11/21 0021
     * @param condition 1
     * @return java.lang.Integer
     **/
    Integer countByCondition(ShoppingOrder condition);


    /**
     * 通过订单编号集合查询订单状态
     * 订单状态定义 1初始状态 2已付款 3已发货 4已收货 5已取消
     *
     * @param orderNoSet 1
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @since 下午 3:38 2019/11/7 0007
     **/
    Map<String, Integer> findOrderStateByOrderNoSet(Set<String> orderNoSet);

    /**
     * 通过组订单id集合查询组订单状态
     * 订单状态定义 1初始状态 2已付款 3已发货 4已收货 5已取消
     *
     * @param groupIdSet 1
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @since 下午 3:39 2019/11/7 0007
     **/
    Map<String, Integer> findGroupStateByGroupIdSet(Set<String> groupIdSet);


    /**
     * 通过订单编号查询订单信息
     *
     * @param orderNo 1
     * @return com.github.chenlijia1111.commonModule.entity.ShoppingOrder
     * @since 上午 9:37 2019/11/8 0008
     **/
    ShoppingOrder findByOrderNo(String orderNo);


}
