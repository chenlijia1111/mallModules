package com.github.chenlijia1111.commonModule.common.requestVo.returnOrder;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 退货退款申请参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/22 0022 下午 5:24
 **/
@ApiModel
@Setter
@Getter
public class ReturnOrderApplyParams {


    /**
     * 订单编号
     * @since 下午 5:26 2019/11/22 0022
     **/
    @ApiModelProperty(value = "订单编号")
    @PropertyCheck(name = "订单编号")
    private String orderNo;

    /**
     * 退款原因
     * @since 下午 5:39 2019/11/22 0022
     **/
    @ApiModelProperty(value = "退款原因")
    private String returnReason;

    /**
     * 退款图片
     * @since 下午 5:40 2019/11/22 0022
     **/
    @ApiModelProperty(value = "退款图片")
    private String image;

}
