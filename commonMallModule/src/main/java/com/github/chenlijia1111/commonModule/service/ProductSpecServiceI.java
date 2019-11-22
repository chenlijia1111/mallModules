package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.common.responseVo.product.ProductSpecVo;
import com.github.chenlijia1111.commonModule.entity.ProductSpec;

import java.util.List;
import java.util.Set;

/**
 * 产品的规格选项
 * @author chenLiJia
 * @since 2019-11-01 14:34:10
 **/
public interface ProductSpecServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 14:34:10
     **/
    Result add(ProductSpec params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 14:34:10
     **/
    Result update(ProductSpec params);

    /**
     * 根据产品id删除规格以及规格值
     * @since 下午 3:59 2019/11/1 0001
     * @param productId 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    Result deleteByProductId(String productId);

    /**
     * 根据产品id集合查询产品的
     * @since 上午 9:28 2019/11/5 0005
     * @param productIdSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.common.responseVo.product.ProductSpecVo>
     **/
    List<ProductSpecVo> listProductSpecVoByProductIdSet(Set<String> productIdSet);
}
