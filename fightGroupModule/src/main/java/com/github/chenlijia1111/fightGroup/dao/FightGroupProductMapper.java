package com.github.chenlijia1111.fightGroup.dao;

import com.github.chenlijia1111.fightGroup.entity.FightGroupProduct;
import com.github.chenlijia1111.utils.common.Result;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * 商品参加拼团活动
 * @author chenLiJia
 * @since 2019-11-26 12:11:28
 * @version 1.0
 **/
public interface FightGroupProductMapper extends Mapper<FightGroupProduct> {


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
    FightGroupProduct findByTimeRange(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                      @Param("productId") String productId, @Param("goodId") String goodId);


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
    Integer updateStockByVersion(@Param("id") String id, @Param("stockCount") Integer stockCount,
                                @Param("oldVersionNo") String oldVersionNo, @Param("newVersionNo") String newVersionNo);

}
