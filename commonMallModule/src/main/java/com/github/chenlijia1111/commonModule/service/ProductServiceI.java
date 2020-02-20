package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.common.requestVo.product.AdminProductQueryParams;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductListVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.entity.Product;
import com.github.chenlijia1111.utils.common.Result;

import java.util.List;
import java.util.Set;

/**
 * 产品表
 * @author chenLiJia
 * @since 2019-11-01 13:46:54
 **/
public interface ProductServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 13:46:54
     **/
    Result add(Product params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 13:46:54
     **/
    Result update(Product params);

    /**
     * 条件查询
     * @param condition
     * @return
     */
    List<Product> listByCondition(Product condition);


    /**
     * 通过产品id查询产品信息
     * @since 下午 5:17 2019/11/5 0005
     * @param productId 1
     * @return com.github.chenlijia1111.commonModule.entity.Product
     **/
    Product findByProductId(String productId);

    /**
     * 查询产品详细信息
     * @since 下午 7:36 2019/11/7 0007
     * @param productId 1
     * @return com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo
     **/
    AdminProductVo findAdminProductVoByProductId(String productId);

    /**
     * 根据产品id集合查询产品信息
     * @param productIdSet
     * @return
     */
    List<Product> listByProductIdSet(Set<String> productIdSet);

}
