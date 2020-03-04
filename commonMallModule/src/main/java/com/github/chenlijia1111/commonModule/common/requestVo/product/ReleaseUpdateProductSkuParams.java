package com.github.chenlijia1111.commonModule.common.requestVo.product;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 修改产品时根据产品规格信息构建sku 请求参数
 * @author Chen LiJia
 * @since 2020/2/29
 */
@ApiModel
@Setter
@Getter
public class ReleaseUpdateProductSkuParams {

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    @PropertyCheck(name = "产品id")
    private String productId;

    /**
     * 产品规格参数
     */
    @ApiModelProperty(value = "产品规格参数")
    @PropertyCheck(name = "产品规格参数")
    private List<ProductSpecParams> productSpecParamsList;

}
