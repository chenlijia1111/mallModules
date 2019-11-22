package com.github.chenlijia1111.commonModule.common.requestVo.product;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/4 0004 上午 9:25
 **/
@ApiModel
@Setter
@Getter
public class UpdateSortNumberParams {

    /**
     * 产品id
     *
     * @since 上午 9:26 2019/11/4 0004
     **/
    @ApiModelProperty(value = "产品id")
    @PropertyCheck(name = "产品id")
    private String id;

    /**
     * 排序值
     *
     * @since 上午 9:26 2019/11/4 0004
     **/
    @ApiModelProperty(value = "排序值")
    @PropertyCheck(name = "排序值")
    private Integer sortNumber;

}
