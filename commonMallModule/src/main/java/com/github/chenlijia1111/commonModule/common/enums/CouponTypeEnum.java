package com.github.chenlijia1111.commonModule.common.enums;

import com.github.chenlijia1111.commonModule.common.pojo.coupon.AbstractCoupon;

/**
 * 优惠券类型
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/22 0022 上午 11:40
 **/
public enum CouponTypeEnum {

    PriceSubCoupon(1, com.github.chenlijia1111.commonModule.common.pojo.coupon.PriceSubCoupon.class), //满价格减优惠券
    PriceDiscountCoupon(2, com.github.chenlijia1111.commonModule.common.pojo.coupon.PriceDiscountCoupon.class), //满价格折扣优惠券
    CountSubCoupon(3, com.github.chenlijia1111.commonModule.common.pojo.coupon.CountSubCoupon.class), //满数量减优惠券
    CountDiscountCoupon(4, com.github.chenlijia1111.commonModule.common.pojo.coupon.CountDiscountCoupon.class), //满数量折扣优惠券
    ScoreCoupon(5, com.github.chenlijia1111.commonModule.common.pojo.coupon.ScoreCoupon.class), //积分券
    ExpressCoupon(6, com.github.chenlijia1111.commonModule.common.pojo.coupon.ExpressCoupon.class), //物流券
    ;

    CouponTypeEnum(Integer type, Class<? extends AbstractCoupon> couponClass) {
        this.type = type;
        this.couponClass = couponClass;
    }

    private Integer type;

    private Class<? extends AbstractCoupon> couponClass;

    public Integer getType() {
        return type;
    }

    public Class<? extends AbstractCoupon> getCouponClass() {
        return couponClass;
    }
}
