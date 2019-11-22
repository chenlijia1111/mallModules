package com.github.chenlijia1111.commonModule.common.responseVo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * app 组订单列表对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/15 0015 上午 9:58
 **/
@Setter
@Getter
@ApiModel
public class AppOrderListVo {

    /**
     * 组订单单号
     *
     * @since 上午 10:01 2019/11/15 0015
     **/
    @ApiModelProperty(value = "组订单单号")
    private String groupId;

    /**
     * 组订单状态 2待处理 3已发货 4已完成
     *
     * @since 上午 10:02 2019/11/15 0015
     **/
    @ApiModelProperty(value = "组订单状态 2待处理 3已发货 4已完成")
    private Integer groupOrderStatus;

    /**
     * 订单总金额
     *
     * @since 上午 10:02 2019/11/15 0015
     **/
    @ApiModelProperty(value = "订单总金额")
    private Double groupOrderTotalMoney;

    /**
     * 重复购买抵扣金额
     *
     * @since 上午 10:05 2019/11/15 0015
     **/
    @ApiModelProperty(value = "重复购买抵扣金额")
    private Double repeatBuyDiscountMoney;

    /**
     * 支付金额
     *
     * @since 上午 10:02 2019/11/15 0015
     **/
    @ApiModelProperty(value = "支付金额")
    private Double payable;

    /**
     * 下单时间
     * @since 上午 10:19 2019/11/15 0015
     **/
    @ApiModelProperty(value = "下单时间")
    private Date createTime;

    /**
     * 收货人
     * @since 上午 10:20 2019/11/15 0015
     **/
    @ApiModelProperty(value = "收货人")
    private String recUserName;

    /**
     * 收货电话
     * @since 上午 10:20 2019/11/15 0015
     **/
    @ApiModelProperty(value = "收货电话")
    private String recTelephone;

    /**
     * 收货地址
     * @since 上午 10:20 2019/11/15 0015
     **/
    @ApiModelProperty(value = "收货地址")
    private String recAddress;

    /**
     * 子订单信息
     *
     * @since 上午 10:03 2019/11/15 0015
     **/
    @ApiModelProperty(value = "子订单信息")
    private List<AppSingleOrderListVo> list;

}
