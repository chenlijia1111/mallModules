package com.github.chenlijia1111.commonModule.common.requestVo.product;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品标签价格添加参数
 * @author Chen LiJia
 * @since 2020/12/3
 */
@Setter
@Getter
@ApiModel
public class GoodLabelPriceAddParams {

    private String labelName;

    private Double goodPrice;

}
