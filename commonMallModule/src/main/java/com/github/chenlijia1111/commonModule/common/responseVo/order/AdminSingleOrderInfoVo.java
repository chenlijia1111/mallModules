package com.github.chenlijia1111.commonModule.common.responseVo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 后台订单详情 -单个订单
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 下午 5:08
 **/
@ApiModel
@Setter
@Getter
public class AdminSingleOrderInfoVo {

    /**
     * 购物单单号
     *
     * @since 下午 6:00 2019/11/7 0007
     **/
    @ApiModelProperty(value = "购物单单号")
    private String shoppingOrderNo;

    /**
     * 产品id
     *
     * @since 下午 6:00 2019/11/7 0007
     **/
    @ApiModelProperty(value = "产品id")
    private String productId;

    /**
     * 商品id
     *
     * @since 下午 6:00 2019/11/7 0007
     **/
    @ApiModelProperty(value = "商品id")
    private String goodId;

    /**
     * 产品图片
     *
     * @since 下午 6:01 2019/11/7 0007
     **/
    @ApiModelProperty(value = "产品图片")
    private String productImage;

    /**
     * 产品名称
     * @since 下午 4:10 2019/11/14 0014
     **/
    @ApiModelProperty(value = "产品名称")
    private String productName;


    /**
     * sku名称
     *
     * @since 下午 6:01 2019/11/7 0007
     **/
    @ApiModelProperty(value = "sku名称")
    private String sku;

    /**
     * 商品编号
     *
     * @since 下午 6:01 2019/11/7 0007
     **/
    @ApiModelProperty(value = "商品编号")
    private String goodNo;

    /**
     * 单价
     *
     * @since 下午 6:02 2019/11/7 0007
     **/
    @ApiModelProperty(value = "单价")
    private Double unitPrice;

    /**
     * 数量
     *
     * @since 下午 6:02 2019/11/7 0007
     **/
    @ApiModelProperty(value = "数量")
    private Integer count;

    /**
     * 商家备注
     *
     * @since 下午 6:02 2019/11/7 0007
     **/
    @ApiModelProperty(value = "商家备注")
    private String remarks;


    /**
     * 子订单订单状态 1初始状态 2已付款 3已发货 4已收货 5已取消
     * @since 下午 5:11 2019/11/14 0014
     **/
    @ApiModelProperty(value = "子订单订单状态 1初始状态 2已付款 3已发货 4已收货 5已取消")
    private Integer orderStatus;

    /**
     * 快递名称
     * @since 下午 2:07 2019/11/15 0015
     **/
    @ApiModelProperty(value = "快递名称")
    private String expressName;

    /**
     * 快递单号
     * @since 下午 2:08 2019/11/15 0015
     **/
    @ApiModelProperty(value = "快递单号")
    private String expressNo;

}
