package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 退货单
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
public interface ReturnGoodsOrderServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result add(ReturnGoodsOrder params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result update(ReturnGoodsOrder params);

    /**
     * 条件查询退货单列表
     * @since 上午 11:19 2019/11/25 0025
     * @param condition 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder>
     **/
    List<ReturnGoodsOrder> listByCondition(ReturnGoodsOrder condition);

    /**
     * 通过退货单单号查询退货单
     * @since 上午 11:20 2019/11/25 0025
     * @param returnNo 退货单单号
     * @return com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder
     **/
    ReturnGoodsOrder findByReturnNo(String returnNo);

    /**
     * 根据退货单号集合查询退货状态
     * @param returnOrderNoSet
     * @return
     */
    Map<String,Integer> findReturnStatusByReturnOrderNoSet(Set<String> returnOrderNoSet);

    /**
     * 根据订单编号集合查询退货状态
     * @param orderNoSet
     * @return
     */
    Map<String,Integer> findReturnStatusByOrderNoSet(Set<String> orderNoSet);

    /**
     * 根据订单编号查询售后信息
     * @param orderNo
     * @return
     */
    ReturnGoodsOrder findByOrderNo(String orderNo);

    /**
     * 根据售后订单集合查询售后订单列表
     * @param returnOrderNoSet
     * @return
     */
    List<ReturnGoodsOrder> listByReturnOrderNoSet(Set<String> returnOrderNoSet);

}
