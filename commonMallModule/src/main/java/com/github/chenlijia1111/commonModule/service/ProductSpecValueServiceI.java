package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.common.responseVo.product.ProductSpecValueNameWrapperVo;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.entity.ProductSpecValue;

import java.util.List;
import java.util.Set;

/**
 * 产品规格值
 *
 * @author chenLiJia
 * @since 2019-11-01 14:34:10
 **/
public interface ProductSpecValueServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 14:34:10
     **/
    Result add(ProductSpecValue params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 14:34:10
     **/
    Result update(ProductSpecValue params);

    /**
     * 批量添加规格值
     *
     * @param list 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:54 2019/11/1 0001
     **/
    Result batchAdd(List<ProductSpecValue> list);

    /**
     * 查询产品id的所有规格值对象映射
     * @param productIdSet
     * @return
     */
    List<ProductSpecValueNameWrapperVo> listProductSpecValueNameWrapperVo(Set<String> productIdSet);


}
