package com.github.chenlijia1111.commonModule.common.requestVo.clientAddress;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 收货地址编辑参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 2:03
 **/
@ApiModel
@Setter
@Getter
public class UpdateParams extends AddParams {


    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    private Integer id;

}
