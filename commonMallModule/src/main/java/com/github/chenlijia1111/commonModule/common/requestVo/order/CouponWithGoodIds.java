package com.github.chenlijia1111.commonModule.common.requestVo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 在用户下单时，
 * 传递使用的优惠券时
 * 需要将这个优惠券所作用的商品id集合一并传给后台
 * <p>
 * 这就需要在查询优惠券的时候，
 * 就需要调用者将这个优惠券对应订单的商品哪些可用列好再返回前端
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/22 0022 上午 11:11
 **/
@ApiModel
@Setter
@Getter
public class CouponWithGoodIds {

    /**
     * 优惠券id
     *
     * @since 上午 11:12 2019/11/22 0022
     **/
    @ApiModelProperty(value = "优惠券id")
    private String couponId;

    /**
     * 在订单中,该优惠券对应的可以使用的商品
     *
     * @since 上午 11:13 2019/11/22 0022
     **/
    @ApiModelProperty(value = "在订单中,该优惠券对应的可以使用的商品")
    private List<String> goodIdList;

}
