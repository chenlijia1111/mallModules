package com.github.chenlijia1111.commonModule.common.requestVo.order;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 商家添加备注参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/8 0008 上午 9:25
 **/
@ApiModel
@Setter
@Getter
public class AddRemarksParams {

    /**
     * 订单编号
     * @since 上午 9:27 2019/11/8 0008
     **/
    @ApiModelProperty(value = "订单编号")
    @PropertyCheck(name = "订单编号")
    private String orderNo;

    /**
     * 商家备注
     * @since 上午 9:27 2019/11/8 0008
     **/
    @ApiModelProperty(value = "商家备注")
    @PropertyCheck(name = "商家备注")
    private String remarks;

}
