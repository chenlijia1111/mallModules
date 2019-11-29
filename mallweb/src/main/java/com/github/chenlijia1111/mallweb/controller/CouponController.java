package com.github.chenlijia1111.mallweb.controller;

import com.github.chenlijia1111.commonModule.common.requestVo.coupon.*;
import com.github.chenlijia1111.mallweb.biz.WebCouponBiz;
import com.github.chenlijia1111.utils.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优惠券
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/29 0029 下午 1:34
 **/
@RestController
@RequestMapping(value = "app/coupon")
@Api(tags = "优惠券")
public class CouponController {

    @Autowired
    private WebCouponBiz couponBiz;

    /**
     * 添加满数量折扣优惠券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:49 2019/11/29 0029
     **/
    @PostMapping(value = "addCountDiscountCoupon")
    @ApiOperation(value = "添加满数量折扣优惠券")
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
    @PostMapping(value = "addCountSubCoupon")
    @ApiOperation(value = "添加满数量减优惠券")
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
    @PostMapping(value = "addPriceDiscountCoupon")
    @ApiOperation(value = "添加满价格折扣优惠券")
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
    @PostMapping(value = "addPriceSubCoupon")
    @ApiOperation(value = "添加满价格减优惠券")
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
    @PostMapping(value = "addExpressCoupon")
    @ApiOperation(value = "添加物流券")
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
    @PostMapping(value = "addScoreCoupon")
    @ApiOperation(value = "添加积分券")
    public Result addScoreCoupon(AddScoreCouponParams params) {
        return couponBiz.addScoreCoupon(params);
    }


}
