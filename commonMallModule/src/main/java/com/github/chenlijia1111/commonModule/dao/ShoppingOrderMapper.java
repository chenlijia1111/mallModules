package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.common.responseVo.order.DelayNotEvaluateOrder;
import com.github.chenlijia1111.commonModule.common.responseVo.order.DelayNotPayOrder;
import com.github.chenlijia1111.commonModule.common.responseVo.order.DelayNotReceiveOrder;
import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.utils.common.Result;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:11
 * @version 1.0
 **/
public interface ShoppingOrderMapper extends Mapper<ShoppingOrder> {


    /**
     * 通过组订单Id集合查询订单集合
     * @since 下午 3:29 2019/11/7 0007
     * @param groupIdSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ShoppingOrder>
     **/
    List<ShoppingOrder> listByGroupIdSet(@Param("groupIdSet") Set<String> groupIdSet);

    /**
     * 通过组订单Id集合查询订单集合---过滤掉长内容的字段，减少查询时间
     * @since 下午 3:29 2019/11/7 0007
     * @param groupIdSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ShoppingOrder>
     **/
    List<ShoppingOrder> listByGroupIdSetFilterLongField(@Param("groupIdSet") Set<String> groupIdSet);

    /**
     * 根据订单编号查询
     * @since 下午 3:42 2019/11/7 0007
     * @param orderNoSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ShoppingOrder>
     **/
    List<ShoppingOrder> listByOrderNoSet(@Param("orderNoSet") Set<String> orderNoSet);

    /**
     * 根据订单编号查询---过滤掉长内容的字段，减少查询时间
     * @since 下午 3:42 2019/11/7 0007
     * @param orderNoSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ShoppingOrder>
     **/
    List<ShoppingOrder> listByOrderNoSetFilterLongField(@Param("orderNoSet") Set<String> orderNoSet);

    /**
     * 根据shopGroupId集合查询订单
     * @param shopGroupIdSet
     * @return
     */
    List<ShoppingOrder> listByShopGroupIdSet(@Param("shopGroupIdSet") Set<String> shopGroupIdSet);

    /**
     * 根据shopGroupId集合查询订单---过滤掉长内容的字段，减少查询时间
     * @param shopGroupIdSet
     * @return
     */
    List<ShoppingOrder> listByShopGroupIdSetFilterLongField(@Param("shopGroupIdSet") Set<String> shopGroupIdSet);


    /**
     * 查找当日最大订单号
     * @return
     */
    String maxOrderNo();

    /**
     * 查找当前最大组订单号
     * @return
     */
    String maxGroupId();


    /**
     * 查找当前最大商家组订单号
     * @return
     */
    String maxShopGroupId();


    /**
     * 批量添加购物单
     * @param shoppingOrderList
     * @return
     */
    Integer batchAdd(@Param("shoppingOrderList") List<ShoppingOrder> shoppingOrderList);


    /**
     * 列出未支付的订单
     * 用处：在系统启动的时候获取未支付的订单放入延时队列中去，等待处理
     * 根据组订单进行聚合
     * @return
     */
    List<DelayNotPayOrder> listDelayNotPayOrder();

    /**
     * 列出未支付的订单
     * 用处：在系统启动的时候获取未支付的订单放入延时队列中去，等待处理
     * 根据商家组订单进行聚合
     * @return
     */
    List<DelayNotPayOrder> listDelayNotPayOrderWithShopGroupNo();

    /**
     * 列出未支付的订单
     * 用处：在系统启动的时候获取未支付的订单放入延时队列中去，等待处理
     * 以单个订单为一个数据
     * @return
     */
    List<DelayNotPayOrder> listDelayNotPayOrderWithOrderNo();


    /**
     * 查询订单状态 state
     * 排除多余字段，节省资源
     * 只取一个，用于判断是否已付款
     *
     * @param groupId
     * @return
     */
    Integer findOrderState(@Param("groupId") String groupId);

    /**
     * 查询未评价订单到延时队列处理
     * @return
     */
    List<DelayNotEvaluateOrder> listDelayNotEvaluateOrder();


    /**
     * 查询物流已签收但是还没有收货的订单 放到延时队列 自动收货
     * @return
     */
    List<DelayNotReceiveOrder> listDelayNotReceiveOrder();


}
