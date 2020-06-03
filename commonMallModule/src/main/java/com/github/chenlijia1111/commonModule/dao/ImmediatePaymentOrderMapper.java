package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder;
import com.github.chenlijia1111.utils.common.Result;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 发货单
 * @author chenLiJia
 * @since 2019-11-05 16:39:11
 * @version 1.0
 **/
public interface ImmediatePaymentOrderMapper extends Mapper<ImmediatePaymentOrder> {

    /**
     * 通过前一个的单号集合查询发货单集合
     * @since 下午 3:51 2019/11/7 0007
     * @param frontNoSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder>
     **/
    List<ImmediatePaymentOrder> listByFrontOrderNoSet(@Param("frontNoSet") Set<String> frontNoSet);

    /**
     * 获取最大订单号
     * @return
     */
    String maxOrderNo();

    /**
     * 通过发货单编号集合查询发货单集合
     * @param sendOrderNoSet
     * @return
     */
    List<ImmediatePaymentOrder> listBySendOrderNoSet(@Param("sendOrderNoSet") Set<String> sendOrderNoSet);


    /**
     * 批量添加发货单
     * @param immediatePaymentOrderList
     * @return
     */
    Integer batchAdd(@Param("immediatePaymentOrderList") List<ImmediatePaymentOrder> immediatePaymentOrderList);

    /**
     * 查询已经发货了还没有签收的发货单
     * @return
     */
    List<ImmediatePaymentOrder> listNotReceiveSendOrder();

}
