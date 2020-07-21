package com.github.chenlijia1111.commonModule.common.requestVo.shopCar;

import com.github.chenlijia1111.commonModule.common.checkFunction.PositiveNumberCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 购物车添加参数
 * 添加的时候，如果元购物车已经有这个商品了，就直接在原有的基础上加上添加的数量
 * 购物车存的是规格
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/8/17 0017 上午 11:12
 **/
@Setter
@Getter
@ApiModel
public class ShopCarAddOrUpdateParams {

    /**
     * 商品id
     *
     * @author chenlijia
     * @since 上午 11:13 2019/8/17 0017
     **/
    @ApiModelProperty(value = "商品id")
    @PropertyCheck(name = "商品id")
    private String goodsId;

    /**
     * 商品数量
     *
     * @author chenlijia
     * @since 上午 11:13 2019/8/17 0017
     **/
    @ApiModelProperty(value = "商品数量")
    @PropertyCheck(name = "商品数量", checkFunction = PositiveNumberCheck.class)
    private Integer goodsCount;


}
