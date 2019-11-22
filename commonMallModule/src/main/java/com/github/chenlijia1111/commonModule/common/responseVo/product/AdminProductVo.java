package com.github.chenlijia1111.commonModule.common.responseVo.product;

import com.github.chenlijia1111.commonModule.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 后台商品返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 上午 9:09
 **/
@ApiModel
@Setter
@Getter
public class AdminProductVo extends Product {

    /**
     * 产品规格集合
     *
     * @since 上午 9:16 2019/11/5 0005
     **/
    @ApiModelProperty(value = "产品规格集合")
    private List<ProductSpecVo> productSpecVoList;

    /**
     * 商品集合
     *
     * @since 上午 9:19 2019/11/5 0005
     **/
    @ApiModelProperty(value = "商品集合")
    private List<GoodVo> goodVoList;

}
