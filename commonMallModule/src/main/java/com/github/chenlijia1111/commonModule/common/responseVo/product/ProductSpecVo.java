package com.github.chenlijia1111.commonModule.common.responseVo.product;

import com.github.chenlijia1111.commonModule.entity.ProductSpec;
import com.github.chenlijia1111.commonModule.entity.ProductSpecValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 产品规格返回对象
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 上午 9:12
 **/
@ApiModel
@Setter
@Getter
public class ProductSpecVo extends ProductSpec {

    /**
     * 产品规格值
     * @since 上午 9:13 2019/11/5 0005
     **/
    @ApiModelProperty(value = "产品规格值")
    private List<ProductSpecValue> productSpecValueList;

}
