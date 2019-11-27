package com.github.chenlijia1111.fightGroup.service.impl;

import com.github.chenlijia1111.fightGroup.dao.FightGroupProductMapper;
import com.github.chenlijia1111.fightGroup.entity.FightGroupProduct;
import com.github.chenlijia1111.fightGroup.service.FightGroupProductServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品参加拼团活动
 *
 * @author chenLiJia
 * @since 2019-11-26 12:09:02
 **/
@Service
public class FightGroupProductServiceImpl implements FightGroupProductServiceI {


    @Resource
    private FightGroupProductMapper fightGroupProductMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    public Result add(FightGroupProduct params) {

        int i = fightGroupProductMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    public Result update(FightGroupProduct params) {

        int i = fightGroupProductMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询商品是否在这一实践区间内参与了拼团
     *
     * @param startTime 时间范围 2019-08-08 12:00:00
     * @param endTime   时间范围
     * @param productId 产品id
     * @param goodId    商品id
     * @return com.github.chenlijia1111.fightGroup.entity.FightGroupProduct
     * @since 下午 2:18 2019/11/26 0026
     **/
    public FightGroupProduct findByTimeRange(String startTime, String endTime, String productId, String goodId) {
        return fightGroupProductMapper.findByTimeRange(startTime, endTime, productId, goodId);
    }

    /**
     * 主键查询商品参与拼团记录
     *
     * @param id 1
     * @return com.github.chenlijia1111.fightGroup.entity.FightGroupProduct
     * @since 下午 3:05 2019/11/26 0026
     **/
    @Override
    public FightGroupProduct findById(String id) {
        if (StringUtils.isNotEmpty(id)) {
            return fightGroupProductMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    /**
     * 根据版本号更新拼团产品库存
     *
     * @param id
     * @param stockCount   库存
     * @param oldVersionNo 旧版本号
     * @param newVersionNo 新版本号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:44 2019/11/25 0025
     **/
    @Override
    public Result updateStockByVersion(String id, Integer stockCount, String oldVersionNo, String newVersionNo) {
        return fightGroupProductMapper.updateStockByVersion(id, stockCount, oldVersionNo, newVersionNo);
    }


}
