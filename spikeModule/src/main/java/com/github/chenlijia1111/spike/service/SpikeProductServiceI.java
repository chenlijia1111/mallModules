package com.github.chenlijia1111.spike.service;

import com.github.chenlijia1111.spike.entity.SpikeProduct;
import com.github.chenlijia1111.utils.common.Result;

/**
 * 产品参与秒杀表
 *
 * @author chenLiJia
 * @since 2019-11-25 15:06:11
 **/
public interface SpikeProductServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 15:06:11
     **/
    Result add(SpikeProduct params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 15:06:11
     **/
    Result update(SpikeProduct params);

    /**
     * 主键查询商品产品参与秒杀记录
     *
     * @param id 1
     * @return com.github.chenlijia1111.spike.entity.SpikeProduct
     * @since 下午 3:21 2019/11/25 0025
     **/
    SpikeProduct findById(String id);

    /**
     * 根据版本号更新秒杀产品库存
     *
     * @param id
     * @param stockCount   库存
     * @param oldVersionNo 旧版本号
     * @param newVersionNo 新版本号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:44 2019/11/25 0025
     **/
    Result updateStockByVersion(String id, Integer stockCount, String oldVersionNo, String newVersionNo);

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
    SpikeProduct findByTimeRange(String startTime, String endTime, String productId, String goodId);
}
