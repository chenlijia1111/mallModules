package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.common.responseVo.shopCar.ClientShopCarGroupByShopVo;
import com.github.chenlijia1111.commonModule.common.responseVo.shopCar.ClientShopCarVo;
import com.github.chenlijia1111.commonModule.entity.ShopCar;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 购物车
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2020-02-19 17:37:03
 **/
public interface ShopCarMapper extends Mapper<ShopCar> {
    ShopCar selectByPrimaryKey(Integer id);


    /**
     * 批量删除购物车
     *
     * @param shopCarIdList 1
     * @return java.lang.Integer
     * @author chenlijia
     * @since 下午 12:35 2019/8/17 0017
     **/
    Integer batchDelete(@Param("shopCarIdList") List<Integer> shopCarIdList);


    /**
     * 查找用户购物车中商品种类总数量
     *
     * @param clientId 1
     * @return java.lang.Integer
     * @author chenlijia
     * @since 下午 12:41 2019/8/17 0017
     **/
    Integer findShopCarAllGoodsKindCount(@Param("clientId") String clientId);


    /**
     * 分组查询购物车中的店铺店家列表
     *
     * @param clientId 1
     * @return java.util.List<com.logicalthinking.jiuyou.common.reponseVo.shopCar.ClientShopCarGroupByShopVo>
     * @author chenlijia
     * @since 下午 12:59 2019/8/17 0017
     **/
    List<ClientShopCarGroupByShopVo> listPageWithClientShopCarGroupByShopVo(@Param("clientId") String clientId);


    /**
     * 根据用户 和商家 id 集合查询购物车列表
     *
     * @param shopIdSet 1
     * @param clientId  2
     * @return java.util.List<com.logicalthinking.jiuyou.common.reponseVo.shopCar.ClientShopCarVo>
     * @author chenlijia
     * @since 下午 1:01 2019/8/17 0017
     **/
    List<ClientShopCarVo> listPageByClientAndShopIdSet(@Param("shopIdSet") Set<String> shopIdSet, @Param("clientId") String clientId);


    /**
     * 通过 购物车 id 集合 以及 客户id 查询购物车信息
     *
     * @param shopCarIdSet 1
     * @param clientId     2
     * @return java.util.List<com.logicalthinking.jiuyou.common.reponseVo.shopCar.ClientShopCarVo>
     * @author chenlijia
     * @since 14:11 2019/8/23
     **/
    List<ClientShopCarVo> listPageByShopCarIdSetAndClientId(@Param("shopCarIdSet") Set<Integer> shopCarIdSet, @Param("clientId") String clientId);


    /**
     * 根据购物车id集合查询购物车
     *
     * @param shopCarIdSet
     * @return
     */
    List<ShopCar> listByShopCarIdSet(@Param("shopCarIdSet") Set<Integer> shopCarIdSet);
}