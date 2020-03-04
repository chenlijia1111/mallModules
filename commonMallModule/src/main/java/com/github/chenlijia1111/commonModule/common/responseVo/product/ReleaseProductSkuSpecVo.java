package com.github.chenlijia1111.commonModule.common.responseVo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 产品根据产品规格构架产品sku的属性属性值对象
 * @author Chen LiJia
 * @since 2020/2/29
 */
@ApiModel
@Setter
@Getter
public class ReleaseProductSkuSpecVo {

    /**
     * 规格名称
     *
     * @since 下午 1:59 2019/11/1 0001
     **/
    @ApiModelProperty(value = "规格名称")
    private String specName;

    /**
     * 图片规格值
     *
     * @since 下午 3:02 2019/11/1 0001
     **/
    @ApiModelProperty(value = "产品图片规格值")
    private String imageValue;

    /**
     * 规格值
     *
     * @since 下午 3:02 2019/11/1 0001
     **/
    @ApiModelProperty(value = "产品规格值")
    private String value;

}
