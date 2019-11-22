package com.github.chenlijia1111.commonModule.common.responseVo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 后台列表订单对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 下午 2:31
 **/
@ApiModel
@Setter
@Getter
public class AdminOrderListVo {

    /**
     * 下单时间
     *
     * @since 下午 2:34 2019/11/7 0007
     **/
    @ApiModelProperty(value = "下单时间")
    private Date orderCreateTime;

    /**
     * 组订单id
     *
     * @since 下午 2:34 2019/11/7 0007
     **/
    @ApiModelProperty(value = "组订单id")
    private String groupId;

    /**
     * 用户账户
     *
     * @since 下午 2:34 2019/11/7 0007
     **/
    @ApiModelProperty(value = "用户账户")
    private String userAccount;


    /**
     * 用户角色名称
     *
     * @since 下午 2:35 2019/11/7 0007
     **/
    @ApiModelProperty(value = "用户角色名称")
    private String userRoleName;

    /**
     * 实付金额
     *
     * @since 下午 2:35 2019/11/7 0007
     **/
    @ApiModelProperty(value = "实付金额")
    private Double payAble;

    /**
     * 收货人姓名
     *
     * @since 下午 2:36 2019/11/7 0007
     **/
    @ApiModelProperty(value = "收货人姓名")
    private String recUserName;

    /**
     * 收货人地址
     *
     * @since 下午 2:36 2019/11/7 0007
     **/
    @ApiModelProperty(value = "收货人地址")
    private String recAddress;

    /**
     * 收货人手机号
     *
     * @since 下午 2:36 2019/11/7 0007
     **/
    @ApiModelProperty(value = "收货人手机号")
    private String recTelephone;

    /**
     * 订单状态
     * 订单状态定义 1初始状态 2已付款 3已发货 4已收货 5已取消
     *
     * @since 下午 2:36 2019/11/7 0007
     **/
    @ApiModelProperty(value = "订单状态 1初始状态 2已付款 3已发货 4已收货 5已取消")
    private Integer orderState;

}
