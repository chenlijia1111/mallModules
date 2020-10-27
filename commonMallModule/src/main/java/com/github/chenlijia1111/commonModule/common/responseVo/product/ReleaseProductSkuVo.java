package com.github.chenlijia1111.commonModule.common.responseVo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品根据产品规格构架产品sku对象
 * 供前端调用
 * @author Chen LiJia
 * @since 2020/2/29
 */
@ApiModel
@Setter
@Getter
public class ReleaseProductSkuVo {

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private Double price;

    /**
     * 市场价格
     */
    @ApiModelProperty(value = "市场价格")
    private Double marketPrice;

    /**
     * vip价格
     */
    @ApiModelProperty(value = "vip价格")
    private Double vipPrice;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private BigDecimal stockCount;

    /**
     * 产品根据产品规格构架产品sku的属性属性值对象列表
     */
    @ApiModelProperty(value = "产品根据产品规格构架产品sku的属性属性值对象列表")
    private List<ReleaseProductSkuSpecVo> skuSpecVos;
}
