package com.github.chenlijia1111.commonModule.common.requestVo.shopCar;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 修改购物车商品id参数
 * @author Chen LiJia
 * @since 2020/5/12
 */
@ApiModel
@Setter
@Getter
public class UpdateShopCarGoodIdParams {

    /**
     * 购物车id
     */
    @ApiModelProperty(value = "购物车id")
    @PropertyCheck(name = "购物车id")
    private Integer shopCarId;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    @PropertyCheck(name = "商品id")
    private String goodId;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    @PropertyCheck(name = "商品数量")
    private Integer goodCount;

}
