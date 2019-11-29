package com.github.chenlijia1111.commonModule.common.requestVo.coupon;

import com.github.chenlijia1111.commonModule.common.checkFunction.PositiveNumberCheck;
import com.github.chenlijia1111.commonModule.common.checkFunction.PriceCheck;
import com.github.chenlijia1111.commonModule.common.checkFunction.RatioCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
public class AddPriceDiscountCouponParams {

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
    @PropertyCheck(name = "优惠券总数", checkFunction = PositiveNumberCheck.class)
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
     * 条件金额
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    @ApiModelProperty(value = "条件金额")
    @PropertyCheck(name = "条件金额", checkFunction = PriceCheck.class)
    private Double conditionMoney;


    /**
     * 折扣率
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    @ApiModelProperty(value = "折扣率")
    @PropertyCheck(name = "折扣率", checkFunction = RatioCheck.class)
    private Double discount;

}
