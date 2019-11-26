package com.github.chenlijia1111.spike.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.spike.entity.SpikeOrderRecode;
import com.github.chenlijia1111.spike.dao.SpikeOrderRecodeMapper;
import com.github.chenlijia1111.spike.service.SpikeOrderRecodeServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 秒杀下单记录
 * @author chenLiJia
 * @since 2019-11-25 15:06:11
 **/
@Service
public class SpikeOrderRecodeServiceImpl implements SpikeOrderRecodeServiceI {


    @Resource
    private SpikeOrderRecodeMapper spikeOrderRecodeMapper;


    /**
     * 添加
     *
     * @param params      添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 15:06:11
     **/
    public Result add(SpikeOrderRecode params){
    
        int i = spikeOrderRecodeMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params      编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 15:06:11
     **/
    public Result update(SpikeOrderRecode params){
    
        int i = spikeOrderRecodeMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


}
