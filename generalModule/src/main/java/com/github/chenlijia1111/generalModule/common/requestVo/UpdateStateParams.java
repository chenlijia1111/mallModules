package com.github.chenlijia1111.generalModule.common.requestVo;

import com.github.chenlijia1111.generalModule.common.checkFunction.StateCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 全局修改状态参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 上午 9:02
 **/
@ApiModel
@Setter
@Getter
public class UpdateStateParams {

    /**
     * id
     *
     * @since 上午 9:03 2019/11/1 0001
     **/
    @ApiModelProperty(value = "id")
    @PropertyCheck(name = "id")
    private Integer id;

    /**
     * 状态 0关闭 1开启
     *
     * @since 上午 9:03 2019/11/1 0001
     **/
    @ApiModelProperty(value = "状态 0关闭 1开启")
    @PropertyCheck(name = "状态", checkFunction = StateCheck.class)
    private Integer state;

}
