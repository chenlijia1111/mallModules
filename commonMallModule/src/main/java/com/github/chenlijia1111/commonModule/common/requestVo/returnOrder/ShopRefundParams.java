package com.github.chenlijia1111.commonModule.common.requestVo.returnOrder;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 商家退款参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/25 0025 下午 12:05
 **/
@Setter
@Getter
@ApiModel
public class ShopRefundParams {

    /**
     * 退货订单单号
     * @since 下午 12:06 2019/11/25 0025
     **/
    @ApiModelProperty(value = "退货订单单号")
    @PropertyCheck(name = "退货订单单号")
    private String returnOrderNo;

    /**
     * 退款流水号
     * @since 下午 12:06 2019/11/25 0025
     **/
    @ApiModelProperty(value = "退款流水号")
    @PropertyCheck(name = "退款流水号")
    private String refundPayNo;

}
