package com.github.chenlijia1111.commonModule.common.requestVo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 假支付参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/6 0006 上午 10:48
 **/
@ApiModel
@Setter
@Getter
public class PayParams {

    /**
     * 组订单号
     * @since 上午 10:51 2019/11/6 0006
     **/
    @ApiModelProperty(value = "组订单号")
    private String groupId;

    /**
     * 支付商户订单号
     * @since 上午 10:49 2019/11/6 0006
     **/
    @ApiModelProperty(value = "支付商户订单号")
    private String payRecode;

    /**
     * 支付第三方交易流水号
     * @since 上午 10:49 2019/11/6 0006
     **/
    @ApiModelProperty(value = "支付第三方交易流水号")
    private String transactionId;

    /**
     * 支付渠道
     * @since 上午 10:49 2019/11/6 0006
     **/
    @ApiModelProperty(value = "支付渠道")
    private String payChannel;

}
