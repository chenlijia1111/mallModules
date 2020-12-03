package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.dao.ProductSnapshotMapper;
import com.github.chenlijia1111.commonModule.entity.ProductSnapshot;
import com.github.chenlijia1111.commonModule.service.ProductSnapshotServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 产品快照表
 *
 * @author chenLiJia
 * @since 2020-12-03 09:12:20
 **/
@Service
public class ProductSnapshotServiceImpl implements ProductSnapshotServiceI {


    @Resource
    private ProductSnapshotMapper productSnapshotMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-12-03 09:12:20
     **/
    @Override
    public Result add(ProductSnapshot params) {

        int i = productSnapshotMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-12-03 09:12:20
     **/
    @Override
    public Result update(ProductSnapshot params) {

        int i = productSnapshotMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 按条件编辑
     *
     * @param entity
     * @param condition
     * @return
     */
    @Override
    public Result update(ProductSnapshot entity, Example condition) {
        if (Objects.nonNull(entity) && Objects.nonNull(condition)) {
            int i = productSnapshotMapper.updateByExampleSelective(entity, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @since 2020-12-03 09:12:20
     **/
    @Override
    public List<ProductSnapshot> listByCondition(ProductSnapshot condition) {

        PropertyCheckUtil.transferObjectNotNull(condition, true);
        return productSnapshotMapper.select(condition);
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @since 2020-12-03 09:12:20
     **/
    @Override
    public List<ProductSnapshot> listByCondition(Example condition) {

        if (null != condition) {
            List<ProductSnapshot> list = productSnapshotMapper.selectByExample(condition);
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * 查询最近的产品快照信息
     *
     * @param productIdSet 产品id集合
     * @return
     */
    @Override
    public List<ProductSnapshot> listByProductIdSet(Set<String> productIdSet) {
        if (Sets.isNotEmpty(productIdSet)) {
            return productSnapshotMapper.listByProductIdSet(productIdSet);
        }
        return new ArrayList<>();
    }
}
