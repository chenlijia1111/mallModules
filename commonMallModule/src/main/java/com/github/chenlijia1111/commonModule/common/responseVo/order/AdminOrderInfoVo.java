package com.github.chenlijia1111.commonModule.common.responseVo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 后台订单详情-组订单
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 下午 5:07
 **/
@ApiModel
@Setter
@Getter
public class AdminOrderInfoVo {

    /**
     * 组订单号
     *
     * @since 下午 5:44 2019/11/7 0007
     **/
    @ApiModelProperty(value = "组订单号")
    private String groupId;

    /**
     * 用户账号
     *
     * @since 下午 5:43 2019/11/7 0007
     **/
    @ApiModelProperty(value = "用户账号")
    private String customUserAccount;

    /**
     * 下单时间
     *
     * @since 下午 5:43 2019/11/7 0007
     **/
    @ApiModelProperty(value = "下单时间")
    private Date orderCreateTime;

    /**
     * 收货人
     *
     * @since 下午 5:43 2019/11/7 0007
     **/
    @ApiModelProperty(value = "收货人")
    private String recUserName;

    /**
     * 收货人手机号
     *
     * @since 下午 5:43 2019/11/7 0007
     **/
    @ApiModelProperty(value = "收货人手机号")
    private String recTelephone;

    /**
     * 收货地址
     *
     * @since 下午 5:43 2019/11/7 0007
     **/
    @ApiModelProperty(value = "收货地址")
    private String recAddress;

    /**
     * 付款时间
     *
     * @since 下午 5:43 2019/11/7 0007
     **/
    @ApiModelProperty(value = "付款时间")
    private Date payTime;

    /**
     * 订单总金额
     * 关于前端显示会员价的问题
     * 当订单总金额跟实付金额不一样的就代表实付金额是会员价
     *
     * @since 下午 5:42 2019/11/7 0007
     **/
    @ApiModelProperty(value = "订单总金额")
    private Double orderAmountMoney;


    /**
     * 实付金额
     *
     * @since 下午 5:42 2019/11/7 0007
     **/
    @ApiModelProperty(value = "实付金额")
    private Double payAble;

    /**
     * 备注
     *
     * @since 下午 5:42 2019/11/7 0007
     **/
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 组订单订单状态 1初始状态 2已付款 3已发货 4已收货 5已取消
     * @since 下午 5:11 2019/11/14 0014
     **/
    @ApiModelProperty(value = "组订单订单状态 1初始状态 2已付款 3已发货 4已收货 5已取消")
    private Integer groupOrderStatus;

    /**
     * 购物单
     *
     * @since 下午 5:42 2019/11/7 0007
     **/
    @ApiModelProperty(value = "购物单")
    private List<AdminSingleOrderInfoVo> singleOrderList;

}
