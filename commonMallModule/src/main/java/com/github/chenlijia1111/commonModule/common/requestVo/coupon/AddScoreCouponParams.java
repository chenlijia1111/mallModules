package com.github.chenlijia1111.commonModule.common.requestVo.coupon;

import com.github.chenlijia1111.commonModule.common.checkFunction.RatioCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
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
public class AddScoreCouponParams {

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
     * 抵扣的积分
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    @ApiModelProperty(value = "抵扣的积分")
    @PropertyCheck(name = "抵扣的积分")
    private BigDecimal score;

    /**
     * 兑换比率 不可以为空,没有这个无法换算出对应可以抵扣的金额
     * ratio 为 1.5 表示 1 个积分可以抵扣 1.5 元
     *
     * @since 下午 4:07 2019/11/21 0021
     **/
    @ApiModelProperty(value = "兑换比率")
    @PropertyCheck(name = "兑换比率")
    private BigDecimal ratio;

    /**
     * 最多可以抵扣的积分
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    @ApiModelProperty(value = "最多可以抵扣的积分")
    private BigDecimal maxScore;

    /**
     * 最多可以抵扣的金额比率
     * 比如maxMoneyRatio 是0.2 那么对多只能抵扣订单金额的 百分之二十
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    @ApiModelProperty(value = "最多可以抵扣的金额比率")
    private BigDecimal maxMoneyRatio;

}
