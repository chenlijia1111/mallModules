package com.github.chenlijia1111.commonModule.common.responseVo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 客户端产品列表对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 上午 11:43
 **/
@ApiModel
@Setter
@Getter
public class AppProductListVo {

    /**
     * 商品id
     */
    @ApiModelProperty("产品id")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 小图
     */
    @ApiModelProperty("小图")
    private String smallPic;


    /**
     * 市场价
     *
     * @since 上午 9:20 2019/11/4 0004
     **/
    @ApiModelProperty(value = "市场价")
    private String marketPriceRange;

    /**
     * 售价
     *
     * @since 上午 9:20 2019/11/4 0004
     **/
    @ApiModelProperty(value = "售价")
    private String priceRange;

    /**
     * 会员价
     *
     * @since 上午 9:20 2019/11/4 0004
     **/
    @ApiModelProperty(value = "会员价")
    private String VipPriceRange;


    /**
     * 销量
     *
     * @since 上午 11:46 2019/11/5 0005
     **/
    @ApiModelProperty(value = "销量")
    private Integer salesVolume = 0;

}
