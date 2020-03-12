package com.github.chenlijia1111.commonModule.biz.coupon;

import com.github.chenlijia1111.commonModule.common.pojo.coupon.ExpressCoupon;
import com.github.chenlijia1111.utils.core.JSONUtil;
import org.junit.Test;

/**
 * @author Chen LiJia
 * @since 2020/3/12
 */
public class TestCoupon {

    @Test
    public void test1(){
        String couponImplClassName = new ExpressCoupon().getCouponImplClassName();
        System.out.println(couponImplClassName);

        ExpressCoupon expressCoupon = new ExpressCoupon();
        String s = JSONUtil.objToStr(expressCoupon);
        System.out.println(s);
    }

}
