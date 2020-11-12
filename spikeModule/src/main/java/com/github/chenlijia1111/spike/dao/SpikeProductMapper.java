package com.github.chenlijia1111.spike.dao;

import com.github.chenlijia1111.spike.entity.SpikeProduct;
import com.github.chenlijia1111.utils.common.Result;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;

/**
 * 产品参与秒杀表
 * @author chenLiJia
 * @since 2019-11-25 15:06:02
 * @version 1.0
 **/
public interface SpikeProductMapper extends Mapper<SpikeProduct> {

    /**
     * 根据版本号更新秒杀产品库存
     *
     * @param id
     * @param stockCount   库存
     * @param oldVersionNo 旧版本号
     * @param newVersionNo 新版本号
     * @return java.lang.Integer
     * @since 下午 3:44 2019/11/25 0025
     **/
    Integer updateStockByVersion(@Param("id") String id, @Param("stockCount") BigDecimal stockCount,
                                @Param("oldVersionNo") String oldVersionNo, @Param("newVersionNo") String newVersionNo);


    /**
     * 根据时间区间查询商品是否参与了秒杀
     *
     * @param startTime 时间范围 2019-08-08 12:00:00
     * @param endTime   时间范围
     * @param productId 产品id
     * @param goodId    商品id
     * @return com.github.chenlijia1111.spike.entity.SpikeProduct
     * @since 下午 6:30 2019/11/25 0025
     **/
    SpikeProduct findByTimeRange(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                 @Param("productId") String productId, @Param("goodId") String goodId);

}
