package com.github.chenlijia1111.commonModule.common.responseVo.shopCar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 购物车数量信息
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/9/21 0021 下午 4:37
 **/
@Setter
@Getter
@ApiModel("购物车数量信息")
public class ShopCarCountInfo {

    /**
     * 购物车里面所有商品数量
     *
     * @since 下午 4:39 2019/9/21 0021
     **/
    @ApiModelProperty("购物车里面所有商品数量")
    private Integer allGoodsCount = 0;

    /**
     * 购物车里所有商品种类数量
     *
     * @since 下午 4:39 2019/9/21 0021
     **/
    @ApiModelProperty("购物车里所有商品种类数量")
    private Integer allGoodsKindCount = 0;

}
