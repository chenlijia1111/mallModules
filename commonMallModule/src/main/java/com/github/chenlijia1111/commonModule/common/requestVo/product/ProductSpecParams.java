package com.github.chenlijia1111.commonModule.common.requestVo.product;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 产品规格参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 1:58
 **/
@ApiModel
@Setter
@Getter
public class ProductSpecParams {

    /**
     * 规格名称
     *
     * @since 下午 1:59 2019/11/1 0001
     **/
    @ApiModelProperty(value = "规格名称")
    @PropertyCheck(name = "规格名称")
    private String specName;

    /**
     * 规格值
     *
     * @since 下午 1:59 2019/11/1 0001
     **/
    @ApiModelProperty(value = "规格值")
    @PropertyCheck(name = "规格值")
    private List<ProductSpecValueParams> specValueList;

}
