package com.github.chenlijia1111.commonModule.common.responseVo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * app 子订单列表对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/15 0015 上午 9:59
 **/
@Setter
@Getter
@ApiModel
public class AppSingleOrderListVo {

    /**
     * 子订单编号
     * @since 下午 2:13 2019/11/15 0015
     **/
    @ApiModelProperty(value = "子订单编号")
    private String shoppingOrderNo;

    /**
     * 产品图片
     *
     * @since 上午 10:03 2019/11/15 0015
     **/
    @ApiModelProperty(value = "产品图片")
    private String productImage;

    /**
     * 产品名称
     *
     * @since 上午 10:03 2019/11/15 0015
     **/
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 规格名称
     *
     * @since 上午 10:03 2019/11/15 0015
     **/
    @ApiModelProperty(value = "规格名称")
    private String skuName;

    /**
     * 商品价格
     *
     * @since 上午 10:03 2019/11/15 0015
     **/
    @ApiModelProperty(value = "商品价格")
    private Double price;

    /**
     * 购买数量
     *
     * @since 上午 10:03 2019/11/15 0015
     **/
    @ApiModelProperty(value = "购买数量")
    private Integer count;

    /**
     * 订单状态 2待处理 3已发货 4已完成
     *
     * @since 上午 10:04 2019/11/15 0015
     **/
    @ApiModelProperty(value = "订单状态 2待处理 3已发货 4已完成")
    private Integer orderStatus;

    /**
     * 订单备注
     * @since 上午 10:16 2019/11/15 0015
     **/
    @ApiModelProperty(value = "订单备注")
    private String remarks;

    /**
     * 快递名称
     * @since 下午 2:11 2019/11/15 0015
     **/
    @ApiModelProperty(value = "快递名称")
    private String expressName;

    /**
     * 快递单号
     * @since 下午 2:11 2019/11/15 0015
     **/
    @ApiModelProperty(value = "快递单号")
    private String expressNo;



}
