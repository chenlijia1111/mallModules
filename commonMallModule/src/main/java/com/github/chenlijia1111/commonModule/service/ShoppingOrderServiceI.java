package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.utils.common.Result;
import tk.mybatis.mapper.entity.Example;

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
     * 批量添加购物单
     * @param shoppingOrderList
     * @return
     */
    Result batchAdd(List<ShoppingOrder> shoppingOrderList);

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
     *
     * @param condition 1
     * @return java.lang.Integer
     * @since 上午 11:58 2019/11/21 0021
     **/
    Integer countByCondition(ShoppingOrder condition);


    /**
     * 通过订单编号集合查询订单状态
     * 订单状态定义 1初始化 2取消 3已付款 4已发货 5已收货 6已评价 7已完成
     *
     * @param orderNoSet 1
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @see com.github.chenlijia1111.commonModule.common.enums.OrderStatusEnum
     * @since 下午 3:38 2019/11/7 0007
     **/
    Map<String, Integer> findOrderStateByOrderNoSet(Set<String> orderNoSet);

    /**
     * 通过组订单id集合查询组订单状态
     * 订单状态定义 1初始化 2取消 3已付款 4已发货 5已收货 6已评价 7已完成
     *
     * @param groupIdSet 1
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @see com.github.chenlijia1111.commonModule.common.enums.OrderStatusEnum
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

    /**
     * 更新
     * @param shoppingOrder
     * @param condition
     * @return
     */
    Result update(ShoppingOrder shoppingOrder, Example condition);

    /**
     * 取消订单
     * @param groupId
     * @param canCancelStatus 可以取消订单的状态
     * @return
     */
    Result cancelOrder(String groupId,List<Integer> canCancelStatus);

    /**
     * 根据订单编号取消订单
     * @param orderNo
     * @param canCancelStatus 可以取消订单的状态
     * @return
     */
    Result cancelOrderByOrderNo(String orderNo,List<Integer> canCancelStatus);

    /**
     * 根据订单编号集合查询订单集合
     * @param orderNoSet
     * @return
     */
    List<ShoppingOrder> listByOrderNoSet(Set<String> orderNoSet);

    /**
     * 条件查询
     * @param condition
     * @return
     */
    List<ShoppingOrder> listByCondition(Example condition);


}
