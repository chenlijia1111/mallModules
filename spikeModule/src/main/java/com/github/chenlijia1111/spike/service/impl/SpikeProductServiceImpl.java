package com.github.chenlijia1111.spike.service.impl;

import com.github.chenlijia1111.spike.dao.SpikeProductMapper;
import com.github.chenlijia1111.spike.entity.SpikeProduct;
import com.github.chenlijia1111.spike.service.SpikeProductServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 产品参与秒杀表
 *
 * @author chenLiJia
 * @since 2019-11-25 15:06:11
 **/
@Service
public class SpikeProductServiceImpl implements SpikeProductServiceI {


    @Resource
    private SpikeProductMapper spikeProductMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 15:06:11
     **/
    public Result add(SpikeProduct params) {

        int i = spikeProductMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 15:06:11
     **/
    public Result update(SpikeProduct params) {

        int i = spikeProductMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


    /**
     * 主键查询商品产品参与秒杀记录
     *
     * @param id 1
     * @return com.github.chenlijia1111.spike.entity.SpikeProduct
     * @since 下午 3:21 2019/11/25 0025
     **/
    @Override
    public SpikeProduct findById(String id) {
        if (StringUtils.isNotEmpty(id)) {
            return spikeProductMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    /**
     * 根据版本号更新秒杀产品库存
     *
     * @param id
     * @param stockCount   库存
     * @param oldVersionNo 旧版本号
     * @param newVersionNo 新版本号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:44 2019/11/25 0025
     **/
    @Override
    public Result updateStockByVersion(String id, BigDecimal stockCount, String oldVersionNo, String newVersionNo) {
        Integer i = spikeProductMapper.updateStockByVersion(id, stockCount, oldVersionNo, newVersionNo);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 根据时间区间查询商品是否参与了秒杀
     *
     * @param startTime 时间范围 2019-08-08 12:00:00
     * @param endTime   时间范围
     * @param productId 产品id
     * @param goodId    商品id
     * @return com.github.chenlijia1111.spike.entity.SpikeProduct
     * @since 下午 6:30 2019/11/25 0025
     **/
    @Override
    public SpikeProduct findByTimeRange(String startTime, String endTime, String productId, String goodId) {
        return spikeProductMapper.findByTimeRange(startTime, endTime, productId, goodId);
    }


}
