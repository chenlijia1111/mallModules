package com.github.chenlijia1111.spike.dao;

import com.github.chenlijia1111.spike.entity.SpikeOrderRecode;
import tk.mybatis.mapper.common.Mapper;

/**
 * 秒杀下单记录
 * @author chenLiJia
 * @since 2019-11-25 15:06:02
 * @version 1.0
 **/
public interface SpikeOrderRecodeMapper extends Mapper<SpikeOrderRecode> {
    SpikeOrderRecode selectByPrimaryKey(String orderNo);
}