package com.github.chenlijia1111.commonModule.common.pojo.coupon;

import com.github.chenlijia1111.commonModule.common.enums.CouponTypeEnum;
import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 抽象优惠券
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/22 0022 上午 11:49
 **/
public abstract class AbstractCoupon {

    /**
     * 生效金额
     *
     * @since 下午 6:09 2019/11/5 0005
     **/
    private Double effectiveMoney = 0.0;


    /**
     * 计算当前优惠券对于目标金额可以抵扣多少金额
     *
     * @param orderList 目标订单
     * @return java.lang.Double 返回具体可以抵扣的金额
     * @since 上午 11:51 2019/11/22 0022
     **/
    public abstract Double calculatePayable(List<ShoppingOrder> orderList);

    /**
     * 获取抵扣金额
     *
     * @return java.lang.Double 返回具体抵扣的金额
     * @since 上午 11:51 2019/11/22 0022
     **/
    public Double getEffectiveMoney(){
        return effectiveMoney;
    }

    public void setEffectiveMoney(Double effectiveMoney) {
        this.effectiveMoney = effectiveMoney;
    }

    /**
     * 获取当前优惠券的类别
     *
     * @return java.lang.Integer
     * @since 下午 4:02 2019/11/22 0022
     **/
    public Integer getType() {
        Optional<CouponTypeEnum> any = Lists.asList(CouponTypeEnum.values()).stream().filter(e -> Objects.equals(this.getClass(), e.getCouponClass())).findAny();
        if (any.isPresent()) {
            CouponTypeEnum couponTypeEnum = any.get();
            return couponTypeEnum.getType();
        }
        return null;
    }


    /**
     * 根据优惠券类型以及json 转化为优惠券对象
     *
     * @param type       1
     * @param couponJson 2
     * @return com.github.chenlijia1111.commonModule.common.pojo.coupon.AbstractCoupon
     * @see CouponTypeEnum 优惠券类型枚举
     * @since 下午 3:50 2019/11/22 0022
     **/
    public static AbstractCoupon transferTypeToCoupon(Integer type, String couponJson) {
        if (Objects.nonNull(type) && StringUtils.isNotEmpty(couponJson)) {
            Optional<CouponTypeEnum> any = Lists.asList(CouponTypeEnum.values()).stream().filter(e -> Objects.equals(e.getType(), type)).findAny();
            if (any.isPresent()) {
                CouponTypeEnum couponTypeEnum = any.get();
                Class<? extends AbstractCoupon> couponClass = couponTypeEnum.getCouponClass();
                AbstractCoupon abstractCoupon = JSONUtil.strToObj(couponJson, couponClass);
                return abstractCoupon;
            }
        }
        return null;
    }

}
