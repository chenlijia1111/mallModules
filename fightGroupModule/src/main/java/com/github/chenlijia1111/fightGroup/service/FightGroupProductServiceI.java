package com.github.chenlijia1111.fightGroup.service;

import com.github.chenlijia1111.fightGroup.entity.FightGroupProduct;
import com.github.chenlijia1111.utils.common.Result;

import java.math.BigDecimal;

/**
 * 商品参加拼团活动
 *
 * @author chenLiJia
 * @since 2019-11-26 12:09:02
 **/
public interface FightGroupProductServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    Result add(FightGroupProduct params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    Result update(FightGroupProduct params);

    /**
     * 查询商品是否在这一实践区间内参与了拼团
     *
     * @param startTime 时间范围 2019-08-08 12:00:00
     * @param endTime   时间范围
     * @param productId 产品id
     * @param goodId    商品id
     * @return com.github.chenlijia1111.fightGroup.entity.FightGroupProduct
     * @since 下午 2:18 2019/11/26 0026
     **/
    FightGroupProduct findByTimeRange(String startTime, String endTime, String productId, String goodId);

    /**
     * 主键查询商品参与拼团记录
     * @since 下午 3:05 2019/11/26 0026
     * @param id 1
     * @return com.github.chenlijia1111.fightGroup.entity.FightGroupProduct
     **/
    FightGroupProduct findById(String id);


    /**
     * 根据版本号更新拼团产品库存
     *
     * @param id
     * @param stockCount   库存
     * @param oldVersionNo 旧版本号
     * @param newVersionNo 新版本号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:44 2019/11/25 0025
     **/
    Result updateStockByVersion(String id, BigDecimal stockCount, String oldVersionNo, String newVersionNo);


}
