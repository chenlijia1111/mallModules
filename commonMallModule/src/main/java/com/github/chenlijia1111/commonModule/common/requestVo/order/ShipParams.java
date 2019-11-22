package com.github.chenlijia1111.commonModule.common.requestVo.order;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单发货参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/8 0008 上午 9:15
 **/
@ApiModel
@Setter
@Getter
public class ShipParams {

    /**
     * 订单编号
     * @since 上午 9:22 2019/11/8 0008
     **/
    @ApiModelProperty(value = "订单编号")
    @PropertyCheck(name = "订单编号")
    private String orderNo;

    /**
     * 快递公司
     * @since 上午 9:22 2019/11/8 0008
     **/
    @ApiModelProperty(value = "快递公司")
    @PropertyCheck(name = "快递公司")
    private String expressName;

    /**
     * 快递公司编号
     * @since 上午 9:22 2019/11/8 0008
     **/
    @ApiModelProperty(value = "快递公司编号")
    @PropertyCheck(name = "快递公司编号")
    private String expressCom;

    /**
     * 快递单号
     * @since 上午 9:22 2019/11/8 0008
     **/
    @ApiModelProperty(value = "快递单号")
    @PropertyCheck(name = "快递单号")
    private String expressNo;

}
