package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.GoodLabelPrice;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;
import java.util.Set;

/**
 * 商品标签价
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2020-12-03 15:38:17
 **/
public interface GoodLabelPriceMapper extends Mapper<GoodLabelPrice>, InsertListMapper<GoodLabelPrice> {

    /**
     * 查询商品标签价格
     *
     * @param goodIdSet 商品id集合
     * @param labelName 标签名称
     * @return
     */
    List<GoodLabelPrice> listByGoodIdSet(@Param("goodIdSet") Set<String> goodIdSet,@Param("labelName") String labelName);

    /**
     * 删除产品下的所有商品标签价格
     *
     * @param productId
     */
    void deleteByProductId(@Param("productId") String productId);

}
