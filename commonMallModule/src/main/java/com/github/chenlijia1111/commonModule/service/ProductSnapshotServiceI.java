package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;
import java.util.List;
import java.util.Set;

import tk.mybatis.mapper.entity.Example;
import com.github.chenlijia1111.commonModule.entity.ProductSnapshot;

/**
 * 产品快照表
 * @author chenLiJia
 * @since 2020-12-03 09:12:20
 **/
public interface ProductSnapshotServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-12-03 09:12:20
     **/
    Result add(ProductSnapshot params);

    /**
     * 编辑
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-12-03 09:12:20
     **/
    Result update(ProductSnapshot params);

   /**
     * 按条件编辑
     * @param entity
     * @param condition
     * @return
     */
    Result update(ProductSnapshot entity,Example condition);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return
     * @since 2020-12-03 09:12:20
     **/
    List<ProductSnapshot> listByCondition(ProductSnapshot condition);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return
     * @since 2020-12-03 09:12:20
     **/
    List<ProductSnapshot> listByCondition(Example condition);

    /**
     * 查询最近的产品快照信息
     * @param productIdSet 产品id集合
     * @param productType 产品类型
     * @return
     */
    List<ProductSnapshot> listByProductIdSet(Set<String> productIdSet, Integer productType);

    /**
     * 主键查询
     * @param id
     * @return
     */
    ProductSnapshot findById(Integer id);
}
