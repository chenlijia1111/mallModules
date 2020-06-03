package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder;
import com.github.chenlijia1111.utils.common.Result;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 收货单
 * @author chenLiJia
 * @since 2019-11-05 16:39:11
 * @version 1.0
 **/
public interface ReceivingGoodsOrderMapper extends Mapper<ReceivingGoodsOrder> {

    /**
     * 通过前一个单号集合查询收货单集合
     * @since 下午 3:54 2019/11/7 0007
     * @param frontOrderNoSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder>
     **/
    List<ReceivingGoodsOrder> listByFrontOrderNoSet(@Param("frontOrderNoSet") Set<String> frontOrderNoSet);

    /**
     * 获取最大订单号
     * @return
     */
    String maxOrderNo();


    /**
     * 批量添加
     * @param receivingGoodsOrderList
     * @return
     */
    Integer batchAdd(@Param("receivingGoodsOrderList") List<ReceivingGoodsOrder> receivingGoodsOrderList);

    /**
     * 根据订单编号查询收货单
     * @param orderNo
     * @return
     */
    ReceivingGoodsOrder findReceiveOrderByOrderNo(@Param("orderNo") String orderNo);


    /**
     * 根据售后订单删除收货单
     * @param returnNo
     */
    Integer deleteByReturnNo(@Param("returnNo") String returnNo);

}
