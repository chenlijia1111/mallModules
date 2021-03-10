package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.common.responseVo.product.ProductSpecVo;
import com.github.chenlijia1111.commonModule.entity.ProductSpec;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;
import java.util.Set;

/**
 * 产品的规格选项
 * @author chenLiJia
 * @since 2019-11-01 13:46:43
 * @version 1.0
 **/
public interface ProductSpecMapper extends Mapper<ProductSpec>, InsertListMapper<ProductSpec> {

    /**
     * 批量删除
     * @since 下午 4:18 2019/11/1 0001
     * @param idSet 1
     * @return java.lang.Integer
     **/
    Integer batchDelete(@Param("idSet") Set<Integer> idSet);


    /**
     * 根据产品id集合查询产品的
     * @since 上午 9:28 2019/11/5 0005
     * @param productIdSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.common.responseVo.product.ProductSpecVo>
     **/
    List<ProductSpecVo> listProductSpecVoByProductIdSet(@Param("productIdSet") Set<String> productIdSet);

}
