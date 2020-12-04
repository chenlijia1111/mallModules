package com.github.chenlijia1111.commonModule.common.requestVo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Chen LiJia
 * @since 2020/12/4
 */
@Setter
@Getter
@ApiModel
public class GoodLabelPriceAddParams {

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String labelName;

    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格")
    private BigDecimal goodPrice;

}
