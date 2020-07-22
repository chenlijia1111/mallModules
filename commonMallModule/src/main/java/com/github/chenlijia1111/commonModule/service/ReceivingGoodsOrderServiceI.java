package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder;
import com.github.chenlijia1111.utils.common.Result;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;

/**
 * 收货单
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
public interface ReceivingGoodsOrderServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result add(ReceivingGoodsOrder params);

    /**
     * 批量添加
     * @param receivingGoodsOrderList
     * @return
     */
    Result batchAdd(List<ReceivingGoodsOrder> receivingGoodsOrderList);

    /**
     * 编辑
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result update(ReceivingGoodsOrder params);

    /**
     * 根据前一个单号集合查询收货单集合
     *
     * @param frontNoSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder>
     * @since 上午 10:00 2019/11/8 0008
     **/
    List<ReceivingGoodsOrder> listByFrontNoSet(Set<String> frontNoSet);

    /**
     * 根据售后订单删除收货单
     * @param returnNo
     */
    void deleteByReturnNo(String returnNo);

    /**
     * 修改
     * @param receivingGoodsOrder
     * @param condition
     * @return
     */
    Result update(ReceivingGoodsOrder receivingGoodsOrder, Example condition);
}
