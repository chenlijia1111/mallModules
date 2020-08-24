package com.github.chenlijia1111.commonModule.common.requestVo.coupon;

import com.github.chenlijia1111.commonModule.common.checkFunction.BigDecimalPriceCheck;
import com.github.chenlijia1111.commonModule.common.checkFunction.PriceCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 添加满数量折扣券
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/29 0029 上午 10:24
 **/
@Setter
@Getter
@ApiModel
public class AddExpressCouponParams {

    /**
     * 优惠券名称
     *
     * @since 下午 4:43 2019/11/21 0021
     **/
    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

    /**
     * 优惠券总数
     *
     * @since 下午 4:43 2019/11/21 0021
     **/
    @ApiModelProperty(value = "优惠券总数")
    private Integer couponTotalCount;

    /**
     * 1系统添加的优惠券 2后台用户添加的 3商家添加的
     * 添加用户id
     * 这个参数由调用者自己注入
     * 最好不好让前端直接传递
     */
    @ApiModelProperty("1系统添加的优惠券 2后台用户添加的 3商家添加的")
    @PropertyCheck(name = "1系统添加的优惠券 2后台用户添加的 3商家添加的")
    private Integer createUserType;

    /**
     * 添加用户id
     * 这个参数由调用者自己注入
     * 最好不好让前端直接传递
     */
    @ApiModelProperty("添加用户id")
    private String createUserId;

    /**
     * 物流费
     *
     * @since 下午 3:37 2019/11/21 0021
     **/
    @ApiModelProperty(value = "物流费")
    @PropertyCheck(name = "物流费", checkFunction = BigDecimalPriceCheck.class)
    private BigDecimal expressMoney;

    /**
     * 条件金额
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    @ApiModelProperty(value = "条件金额")
    private BigDecimal conditionMoney;

    /**
     * 条件数量
     * 与条件金额二者取其一进行计算
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    @ApiModelProperty(value = "条件数量,与条件金额二者取其一进行计算")
    private Integer conditionCount;

    /**
     * 优惠金额
     * 与折扣率二者取其一进行计算
     *
     * @since 下午 3:39 2019/11/21 0021
     **/
    @ApiModelProperty(value = "优惠金额,与折扣率二者取其一进行计算")
    private BigDecimal subMoney;

    /**
     * 折扣率
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    @ApiModelProperty(value = "折扣率")
    private BigDecimal discount;


}
