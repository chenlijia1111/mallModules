package com.github.chenlijia1111.spike.service;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.spike.entity.SpikeOrderRecode;

/**
 * 秒杀下单记录
 * @author chenLiJia
 * @since 2019-11-25 15:06:11
 **/
public interface SpikeOrderRecodeServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 15:06:11
     **/
    Result add(SpikeOrderRecode params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 15:06:11
     **/
    Result update(SpikeOrderRecode params);


}
