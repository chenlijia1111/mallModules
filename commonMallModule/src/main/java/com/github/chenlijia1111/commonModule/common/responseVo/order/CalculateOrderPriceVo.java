package com.github.chenlijia1111.commonModule.common.responseVo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 试算价格返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/22 0022 下午 4:04
 **/
@ApiModel
@Setter
@Getter
public class CalculateOrderPriceVo {

    /**
     * 总应付金额
     *
     * @since 下午 4:05 2019/11/22 0022
     **/
    @ApiModelProperty(value = "总应付金额")
    private Double payAble;

    /**
     * 满数量折扣优惠券抵扣金额
     *
     * @since 下午 4:05 2019/11/22 0022
     **/
    @ApiModelProperty(value = "满数量折扣优惠券抵扣金额")
    private Double countDiscountPrice;

    /**
     * 满数量减优惠券抵扣金额
     *
     * @since 下午 4:05 2019/11/22 0022
     **/
    @ApiModelProperty(value = "满数量减优惠券抵扣金额")
    private Double countSubPrice;

    /**
     * 满价格折扣优惠券抵扣金额
     *
     * @since 下午 4:05 2019/11/22 0022
     **/
    @ApiModelProperty(value = "满价格折扣优惠券抵扣金额")
    private Double priceDiscountPrice;

    /**
     * 满价格减优惠券抵扣金额
     *
     * @since 下午 4:05 2019/11/22 0022
     **/
    @ApiModelProperty(value = "满价格减优惠券抵扣金额")
    private Double priceSubPrice;

    /**
     * 积分抵扣金额
     *
     * @since 下午 4:05 2019/11/22 0022
     **/
    @ApiModelProperty(value = "积分抵扣金额")
    private Double ScorePrice;

    /**
     * 物流费用
     *
     * @since 下午 4:05 2019/11/22 0022
     **/
    @ApiModelProperty(value = "物流费用")
    private Double expressPrice;

}
