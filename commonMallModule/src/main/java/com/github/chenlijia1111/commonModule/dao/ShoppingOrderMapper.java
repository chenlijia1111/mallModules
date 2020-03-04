package com.github.chenlijia1111.commonModule.dao;

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
     * 根据订单
     * @since 下午 3:42 2019/11/7 0007
     * @param orderNoSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ShoppingOrder>
     **/
    List<ShoppingOrder> listByOrderNoSet(@Param("orderNoSet") Set<String> orderNoSet);

    /**
     * 根据shopGroupId集合查询订单
     * @param shopGroupIdSet
     * @return
     */
    List<ShoppingOrder> listByShopGroupIdSet(@Param("shopGroupIdSet") Set<String> shopGroupIdSet);


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


}
