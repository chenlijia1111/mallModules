package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.common.responseVo.shopCar.ClientShopCarGroupByShopVo;
import com.github.chenlijia1111.commonModule.common.responseVo.shopCar.ClientShopCarVo;
import com.github.chenlijia1111.utils.common.Result;
import java.util.List;
import java.util.Set;

import com.github.chenlijia1111.commonModule.entity.ShopCar;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Example;

/**
 * 购物车
 * @author chenLiJia
 * @since 2020-02-19 17:37:09
 **/
public interface ShopCarServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-02-19 17:37:09
     **/
    Result add(ShopCar params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-02-19 17:37:09
     **/
    Result update(ShopCar params);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return
     * @since 2020-02-19 17:37:09
     **/
    List<ShopCar> listByCondition(ShopCar condition);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return
     * @since 2020-02-19 17:37:09
     **/
    List<ShopCar> listByCondition(Example condition);


    /**
     * 批量删除购物车
     *
     * @param shopCarIdList 1
     * @return
     * @author chenlijia
     * @since 上午 11:26 2019/8/17 0017
     **/
    Result batchDelete(List<Integer> shopCarIdList);


    /**
     * 查找用户购物车中商品种类总数量
     *
     * @param clientId 1
     * @return java.lang.Integer
     * @author chenlijia
     * @since 下午 12:41 2019/8/17 0017
     **/
    Integer findShopCarAllGoodsKindCount(String clientId);

    /**
     * 查找用户购物车中商品种类总数量
     * 只查询上架的
     *
     * @param clientId 1
     * @return java.lang.Integer
     * @author chenlijia
     * @since 下午 12:41 2019/8/17 0017
     **/
    Integer findShopCarAllGoodsKindCountWithShelf(String clientId);


    /**
     * 查询客户端购物车列表
     * 已商家进行分组
     * 排序按对应的最近的交易时间进行排序
     * 先查询出商家，在查询商家对应的购物车列表
     *
     * @param clientId 2
     * @return java.util.List<com.logicalthinking.jiuyou.common.reponseVo.shopCar.ClientShopCarGroupByShopVo>
     * @author chenlijia
     * @since 下午 12:49 2019/8/17 0017
     **/
    List<ClientShopCarGroupByShopVo> listPageWithClient(String clientId);


    /**
     * 通过购物车 id 集合查询购物车信息
     *
     * @param shopCarIdList 1
     * @return java.util.List<com.logicalthinking.jiuyou.common.reponseVo.shopCar.ClientShopCarVo>
     * @author chenlijia
     * @since 14:07 2019/8/23
     **/
    List<ClientShopCarVo> listByShopCarIdList(List<Integer> shopCarIdList, String currentUserId);


    /**
     * 根据购物车id集合查询购物车
     * @param shopCarIdSet
     * @return
     */
    List<ShopCar> listByShopCarIdSet(Set<Integer> shopCarIdSet);

    /**
     * 批量添加
     * @param list
     * @return
     */
    Result batchAdd(List<ShopCar> list);

}
