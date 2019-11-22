package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.entity.GoodSpec;

/**
 * 商品规格
 *
 * @author chenLiJia
 * @since 2019-11-01 14:34:10
 **/
public interface GoodSpecServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 14:34:10
     **/
    Result add(GoodSpec params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 14:34:10
     **/
    Result update(GoodSpec params);

    /**
     * 删除该产品的所有商品的规格值
     *
     * @param productId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:38 2019/11/5 0005
     **/
    Result deleteByProductId(String productId);
}
