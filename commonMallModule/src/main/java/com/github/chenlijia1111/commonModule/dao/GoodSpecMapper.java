package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodSpecVo;
import com.github.chenlijia1111.commonModule.entity.GoodSpec;
import com.github.chenlijia1111.utils.common.Result;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 商品规格
 * @author chenLiJia
 * @since 2019-11-01 13:46:43
 * @version 1.0
 **/
public interface GoodSpecMapper extends Mapper<GoodSpec> {

    /**
     * 通过商品id集合查询商品规格集合
     * @param goodIdSet
     * @return
     */
    List<GoodSpecVo> listGoodSpecVoByGoodIdSet(@Param("goodIdSet") Set<String> goodIdSet);

    /**
     * 删除该产品的所有商品的规格值
     * @since 上午 9:39 2019/11/5 0005
     * @param productId 1
     * @return java.lang.Integer
     **/
    Integer deleteByProductId(@Param("productId") String productId);


    /**
     * 批量添加
     * @param goodSpecList
     * @return
     */
    Integer batchAdd(@Param("goodSpecList") List<GoodSpec> goodSpecList);

}
