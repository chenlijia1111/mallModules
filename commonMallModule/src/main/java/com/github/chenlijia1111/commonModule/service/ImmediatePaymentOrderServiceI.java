package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder;

import java.util.List;
import java.util.Set;

/**
 * 发货单
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
public interface ImmediatePaymentOrderServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result add(ImmediatePaymentOrder params);

    /**
     * 批量添加发货单
     * @param immediatePaymentOrderList
     * @return
     */
    Result batchAdd(List<ImmediatePaymentOrder> immediatePaymentOrderList);


    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result update(ImmediatePaymentOrder params);

    /**
     * 通过前一个订单集合查询发货单集合
     * @since 上午 9:40 2019/11/8 0008
     * @param frontNoSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder>
     **/
    List<ImmediatePaymentOrder> listByFrontNoSet(Set<String> frontNoSet);

    /**
     * 删除售后的发货单
     * @param returnOrderNo
     */
    void deleteByReturnOrderNoSet(String returnOrderNo);

    /**
     * 查询已经发货了还没有签收的发货单
     * @return
     */
    List<ImmediatePaymentOrder> listNotReceiveSendOrder();

}
