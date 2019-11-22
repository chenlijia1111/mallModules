package com.github.chenlijia1111.commonModule.common.requestVo.product;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.commonModule.common.checkFunction.StateCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 2:24
 **/
@ApiModel
@Setter
@Getter
public class UpdateShelfStatusParams {


    /**
     * 产品id
     *
     * @since 上午 9:03 2019/11/1 0001
     **/
    @ApiModelProperty(value = "产品id")
    @PropertyCheck(name = "产品id")
    private String productId;

    /**
     * 状态 0关闭 1开启
     *
     * @since 上午 9:03 2019/11/1 0001
     **/
    @ApiModelProperty(value = "状态 0关闭 1开启")
    @PropertyCheck(name = "状态", checkFunction = StateCheck.class)
    private Integer state;

}
