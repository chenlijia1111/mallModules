package com.github.chenlijia1111.commonModule.common.responseVo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 产品后台列表视图
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/4 0004 上午 9:02
 **/
@ApiModel
@Setter
@Getter
public class AdminProductListVo {

    /**
     * 小图
     */
    @ApiModelProperty("小图")
    private String smallPic;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

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
     * 排序
     *
     * @since 上午 9:20 2019/11/4 0004
     **/
    @ApiModelProperty(value = "排序")
    private Integer sortNumber;

    /**
     * 上架状态 0未上架 1上架
     *
     * @since 上午 9:21 2019/11/4 0004
     **/
    @ApiModelProperty(value = "上架状态 0未上架 1上架")
    private Integer shelfStatus;

}
