package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.common.requestVo.product.AdminProductQueryParams;
import com.github.chenlijia1111.commonModule.common.requestVo.product.AppProductQueryParams;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductListVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AppProductListVo;
import com.github.chenlijia1111.commonModule.entity.Product;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 产品表
 * @author chenLiJia
 * @since 2019-11-01 13:46:43
 * @version 1.0
 **/
public interface ProductMapper extends Mapper<Product> {


    /**
     * 后台产品列表查询
     * @since 上午 9:32 2019/11/4 0004
     * @param params 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductListVo>
     **/
    List<AdminProductListVo> adminListPage(AdminProductQueryParams params);


    /**
     * 客户端查询产品列表
     * @since 上午 11:49 2019/11/5 0005
     * @param params 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.common.responseVo.product.AppProductListVo>
     **/
    List<AppProductListVo> appListPage(AppProductQueryParams params);

    /**
     * 获取最大产品编号
     * @return
     */
    String maxProductNo();

    /**
     * 根据产品id集合查询产品信息
     * @param productIdSet
     * @return
     */
    List<Product> listByProductIdSet(@Param("productIdSet") Set<String> productIdSet);


    /**
     * 通过商品id查询商家id集合
     * @param goodIdSet
     * @return
     */
    Set<String> listShopIdSetByGoodIdSet(@Param("goodIdSet") Set<String> goodIdSet);
}
