package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 退货单
 * @author chenLiJia
 * @since 2019-11-05 16:39:11
 * @version 1.0
 **/
public interface ReturnGoodsOrderMapper extends Mapper<ReturnGoodsOrder> {


    /**
     * 查找当前最大组订单号
     * @return
     */
    String maxOrderNo();

    /**
     * 根据退货单Id集合查询退货单列表
     * @param returnOrderNoSet
     * @return
     */
    List<ReturnGoodsOrder> listByReturnOrderNoSet(@Param("returnOrderNoSet") Set<String> returnOrderNoSet);

    /**
     * 根据订单集合查询退货单集合
     * @param orderNoSet
     * @return
     */
    List<ReturnGoodsOrder> listByOrderNoSet(@Param("orderNoSet") Set<String> orderNoSet);

}
