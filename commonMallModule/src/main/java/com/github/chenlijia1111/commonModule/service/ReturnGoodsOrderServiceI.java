package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder;

/**
 * 退货单
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
public interface ReturnGoodsOrderServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result add(ReturnGoodsOrder params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result update(ReturnGoodsOrder params);


}
