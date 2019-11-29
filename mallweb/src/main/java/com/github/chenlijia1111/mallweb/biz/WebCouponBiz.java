package com.github.chenlijia1111.mallweb.biz;

import com.github.chenlijia1111.commonModule.biz.CouponBiz;
import com.github.chenlijia1111.commonModule.common.enums.CouponTypeEnum;
import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.pojo.coupon.*;
import com.github.chenlijia1111.commonModule.common.requestVo.coupon.*;
import com.github.chenlijia1111.commonModule.entity.Coupon;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/29 0029 下午 1:34
 **/
@Service
public class WebCouponBiz {

    @Autowired
    private CouponBiz couponBiz;


    /**
     * 添加满数量折扣优惠券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:49 2019/11/29 0029
     **/
    public Result addCountDiscountCoupon(AddCountDiscountCouponParams params) {
        return couponBiz.addCountDiscountCoupon(params);
    }

    /**
     * 添加满数量减优惠券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:49 2019/11/29 0029
     **/
    public Result addCountSubCoupon(AddCountSubCouponParams params) {
        return couponBiz.addCountSubCoupon(params);
    }

    /**
     * 添加满价格折扣优惠券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:49 2019/11/29 0029
     **/
    public Result addPriceDiscountCoupon(AddPriceDiscountCouponParams params) {
        return couponBiz.addPriceDiscountCoupon(params);
    }

    /**
     * 添加满价格减优惠券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:50 2019/11/29 0029
     **/
    public Result addPriceSubCoupon(AddPriceSubCouponParams params) {
        return couponBiz.addPriceSubCoupon(params);
    }

    /**
     * 添加物流券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:50 2019/11/29 0029
     **/
    public Result addExpressCoupon(AddExpressCouponParams params) {
        return couponBiz.addExpressCoupon(params);
    }

    /**
     * 添加积分券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:50 2019/11/29 0029
     **/
    public Result addScoreCoupon(AddScoreCouponParams params) {
        return couponBiz.addScoreCoupon(params);
    }

}
