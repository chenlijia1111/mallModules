package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;
import java.util.List;
import tk.mybatis.mapper.entity.Example;
import com.github.chenlijia1111.commonModule.entity.ShopGroupOrder;

/**
 * 商家组订单
 * @author chenLiJia
 * @since 2021-01-20 09:37:25
 **/
public interface ShopGroupOrderServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2021-01-20 09:37:25
     **/
    Result add(ShopGroupOrder params);

    /**
     * 编辑
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2021-01-20 09:37:25
     **/
    Result update(ShopGroupOrder params);

   /**
     * 按条件编辑
     * @param entity
     * @param condition
     * @return
     */
    Result update(ShopGroupOrder entity,Example condition);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return
     * @since 2021-01-20 09:37:25
     **/
    List<ShopGroupOrder> listByCondition(ShopGroupOrder condition);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return
     * @since 2021-01-20 09:37:25
     **/
    List<ShopGroupOrder> listByCondition(Example condition);

    /**
     * 批量添加
     * @param list
     */
    void batchAdd(List<ShopGroupOrder> list);
}
