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
     * 通过产品id集合查询商品规格集合
     * 因为如果商品很多的话， In 又不走索引，可能会导致查询时间过长
     * 所以用 productId 进行关联查询会更快一点
     * 一般查列表，一次也就10条数据，数据就不会很多
     * 如果是通过商品id查询，碰到规格多的可能会有上百个 goodId 要通过 in 查询
     * @param productIdSet
     * @return
     */
    List<GoodSpecVo> listGoodSpecVoByProductIdSet(@Param("productIdSet") Set<String> productIdSet);

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
