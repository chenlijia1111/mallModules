package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.commonModule.entity.Goods;

import java.util.List;
import java.util.Set;

/**
 * 商品表
 * @author chenLiJia
 * @since 2019-11-01 13:46:54
 **/
public interface GoodsServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 13:46:54
     **/
    Result add(Goods params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 13:46:54
     **/
    Result update(Goods params);

    /**
     * 条件查询商品列表
     * @param condition
     * @return
     */
    List<GoodVo> listByCondition(Goods condition);

    /**
     * 批量删除商品
     * @param goodIdSet 商品id集合
     * @return
     */
    Result batchDelete(Set<String> goodIdSet);

    /**
     * 通过产品id集合查询商品列表
     * @since 上午 9:58 2019/11/5 0005
     * @param productIdSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo>
     **/
    List<GoodVo> listByProductIdSet(Set<String> productIdSet);

    /**
     * 通过商品id查询商品
     * @since 下午 5:11 2019/11/5 0005
     * @param goodId 1
     * @return com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo
     **/
    GoodVo findByGoodId(String goodId);

    /**
     * 根据商品id集合查询商品集合
     * @since 上午 11:04 2019/11/22 0022
     * @param goodIdSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo>
     **/
    List<GoodVo> listByGoodIdSet(Set<String> goodIdSet);

}
