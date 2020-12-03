package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;

import java.util.List;
import java.util.Set;

import tk.mybatis.mapper.entity.Example;
import com.github.chenlijia1111.commonModule.entity.GoodLabelPrice;

/**
 * 商品标签价
 *
 * @author chenLiJia
 * @since 2020-12-03 15:38:34
 **/
public interface GoodLabelPriceServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-12-03 15:38:34
     **/
    Result add(GoodLabelPrice params);

    /**
     * 编辑
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-12-03 15:38:34
     **/
    Result update(GoodLabelPrice params);

    /**
     * 按条件编辑
     *
     * @param entity
     * @param condition
     * @return
     */
    Result update(GoodLabelPrice entity, Example condition);

    /**
     * 条件查询
     *
     * @param condition 1
     * @return
     * @since 2020-12-03 15:38:34
     **/
    List<GoodLabelPrice> listByCondition(GoodLabelPrice condition);

    /**
     * 条件查询
     *
     * @param condition 1
     * @return
     * @since 2020-12-03 15:38:34
     **/
    List<GoodLabelPrice> listByCondition(Example condition);

    /**
     * 查询商品标签价格
     *
     * @param goodIdSet 商品id集合
     * @param labelName 标签名称
     * @return
     */
    List<GoodLabelPrice> listByGoodIdSet(Set<String> goodIdSet, String labelName);

    /**
     * 批量添加
     *
     * @param list
     */
    void batchAdd(List<GoodLabelPrice> list);

    /**
     * 删除产品下的所有商品标签价格
     *
     * @param productId
     */
    void deleteByProductId(String productId);
}
