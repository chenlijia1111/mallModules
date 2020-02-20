package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.commonModule.common.enums.CouponTypeEnum;
import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.pojo.coupon.*;
import com.github.chenlijia1111.commonModule.common.requestVo.coupon.*;
import com.github.chenlijia1111.commonModule.entity.Coupon;
import com.github.chenlijia1111.commonModule.service.CouponServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 优惠券
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/29 0029 上午 10:20
 **/
@Service
public class CouponBiz {

    @Autowired
    private CouponServiceI couponService;//优惠券

    /**
     * 添加满数量折扣优惠券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:49 2019/11/29 0029
     **/
    public Result addCountDiscountCoupon(AddCountDiscountCouponParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        Date currentTime = new Date();
        String couponId = String.valueOf(IDGenerateFactory.COUPON_ID_UTIL.nextId());
        //开始处理
        Coupon coupon = new Coupon().
                setId(couponId).
                setCouponName(params.getCouponName()).
                setCouponCount(params.getCouponTotalCount()).
                setCouponTotalCount(params.getCouponTotalCount()).
                setCouponType(CouponTypeEnum.CountDiscountCoupon.getType()).
                setCreateTime(currentTime).setUpdateTime(currentTime).
                setCreateUserType(params.getCreateUserType()).
                setCreateUserId(params.getCreateUserId()).
                setDeleteStatus(BooleanConstant.NO_INTEGER);

        //构建满数量折扣优惠券
        CountDiscountCoupon countDiscountCoupon = new CountDiscountCoupon().
                setId(couponId).
                setConditionCount(params.getConditionCount()).
                setDiscount(params.getDiscount());

        String s = JSONUtil.objToStr(countDiscountCoupon);
        coupon.setCouponJson(s);
        //返回券 json
        Result add = couponService.add(coupon);
        if (add.getSuccess()) {
            add.setData(s);
        }
        return add;
    }

    /**
     * 添加满数量减优惠券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:49 2019/11/29 0029
     **/
    public Result addCountSubCoupon(AddCountSubCouponParams params) {
        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        Date currentTime = new Date();
        String couponId = String.valueOf(IDGenerateFactory.COUPON_ID_UTIL.nextId());
        //开始处理
        Coupon coupon = new Coupon().
                setId(couponId).
                setCouponName(params.getCouponName()).
                setCouponCount(params.getCouponTotalCount()).
                setCouponTotalCount(params.getCouponTotalCount()).
                setCouponType(CouponTypeEnum.CountSubCoupon.getType()).
                setCreateTime(currentTime).setUpdateTime(currentTime).
                setCreateUserType(params.getCreateUserType()).
                setCreateUserId(params.getCreateUserId()).
                setDeleteStatus(BooleanConstant.NO_INTEGER);

        //构建满数量减优惠券
        CountSubCoupon countSubCoupon = new CountSubCoupon().
                setId(couponId).
                setConditionCount(params.getConditionCount()).
                setSubMoney(params.getSubMoney());

        String s = JSONUtil.objToStr(countSubCoupon);
        coupon.setCouponJson(s);
        //返回券 json
        Result add = couponService.add(coupon);
        if (add.getSuccess()) {
            add.setData(s);
        }
        return add;
    }

    /**
     * 添加满价格折扣优惠券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:49 2019/11/29 0029
     **/
    public Result addPriceDiscountCoupon(AddPriceDiscountCouponParams params) {
        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        Date currentTime = new Date();
        String couponId = String.valueOf(IDGenerateFactory.COUPON_ID_UTIL.nextId());
        //开始处理
        Coupon coupon = new Coupon().
                setId(couponId).
                setCouponName(params.getCouponName()).
                setCouponCount(params.getCouponTotalCount()).
                setCouponTotalCount(params.getCouponTotalCount()).
                setCouponType(CouponTypeEnum.PriceDiscountCoupon.getType()).
                setCreateTime(currentTime).setUpdateTime(currentTime).
                setCreateUserType(params.getCreateUserType()).
                setCreateUserId(params.getCreateUserId()).
                setDeleteStatus(BooleanConstant.NO_INTEGER);

        //构建满价格折扣优惠券
        PriceDiscountCoupon priceDiscountCoupon = new PriceDiscountCoupon().
                setId(couponId).
                setConditionMoney(params.getConditionMoney()).
                setDiscount(params.getDiscount());

        String s = JSONUtil.objToStr(priceDiscountCoupon);
        coupon.setCouponJson(s);
        //返回券 json
        Result add = couponService.add(coupon);
        if (add.getSuccess()) {
            add.setData(s);
        }
        return add;
    }

    /**
     * 添加满价格减优惠券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:50 2019/11/29 0029
     **/
    public Result addPriceSubCoupon(AddPriceSubCouponParams params) {
        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        Date currentTime = new Date();
        String couponId = String.valueOf(IDGenerateFactory.COUPON_ID_UTIL.nextId());
        //开始处理
        Coupon coupon = new Coupon().
                setId(couponId).
                setCouponName(params.getCouponName()).
                setCouponCount(params.getCouponTotalCount()).
                setCouponTotalCount(params.getCouponTotalCount()).
                setCouponType(CouponTypeEnum.PriceSubCoupon.getType()).
                setCreateTime(currentTime).setUpdateTime(currentTime).
                setCreateUserType(params.getCreateUserType()).
                setCreateUserId(params.getCreateUserId()).
                setDeleteStatus(BooleanConstant.NO_INTEGER);

        //构建满价格减优惠券
        PriceSubCoupon priceSubCoupon = new PriceSubCoupon().
                setId(couponId).
                setConditionMoney(params.getConditionMoney()).
                setSubMoney(params.getSubMoney());

        String s = JSONUtil.objToStr(priceSubCoupon);
        coupon.setCouponJson(s);
        //返回券 json
        Result add = couponService.add(coupon);
        if (add.getSuccess()) {
            add.setData(s);
        }
        return add;
    }

    /**
     * 添加物流券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:50 2019/11/29 0029
     **/
    public Result addExpressCoupon(AddExpressCouponParams params) {
        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        Date currentTime = new Date();
        String couponId = String.valueOf(IDGenerateFactory.COUPON_ID_UTIL.nextId());
        //开始处理
        Coupon coupon = new Coupon().
                setId(couponId).
                setCouponName(params.getCouponName()).
                setCouponCount(params.getCouponTotalCount()).
                setCouponTotalCount(params.getCouponTotalCount()).
                setCouponType(CouponTypeEnum.ExpressCoupon.getType()).
                setCreateTime(currentTime).setUpdateTime(currentTime).
                setCreateUserType(params.getCreateUserType()).
                setCreateUserId(params.getCreateUserId()).
                setDeleteStatus(BooleanConstant.NO_INTEGER);

        //构建满价格减优惠券
        ExpressCoupon expressCoupon = new ExpressCoupon().
                setId(couponId).
                setExpressMoney(params.getExpressMoney()).
                setConditionMoney(params.getConditionMoney()).
                setConditionCount(params.getConditionCount()).
                setSubMoney(params.getSubMoney()).
                setDiscount(params.getDiscount());

        String s = JSONUtil.objToStr(expressCoupon);
        coupon.setCouponJson(s);

        //返回券 json
        Result add = couponService.add(coupon);
        if (add.getSuccess()) {
            add.setData(s);
        }
        return add;
    }

    /**
     * 添加积分券
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:50 2019/11/29 0029
     **/
    public Result addScoreCoupon(AddScoreCouponParams params) {
        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        Date currentTime = new Date();
        String couponId = String.valueOf(IDGenerateFactory.COUPON_ID_UTIL.nextId());
        //开始处理
        Coupon coupon = new Coupon().
                setId(couponId).
                setCouponName(params.getCouponName()).
                setCouponCount(params.getCouponTotalCount()).
                setCouponTotalCount(params.getCouponTotalCount()).
                setCouponType(CouponTypeEnum.ScoreCoupon.getType()).
                setCreateTime(currentTime).setUpdateTime(currentTime).
                setCreateUserType(params.getCreateUserType()).
                setCreateUserId(params.getCreateUserId()).
                setDeleteStatus(BooleanConstant.NO_INTEGER);

        //构建满价格减优惠券
        ScoreCoupon scoreCoupon = new ScoreCoupon().
                setId(couponId).
                setRatio(params.getRatio()).
                setMaxScore(params.getMaxScore()).
                setMaxMoneyRatio(params.getMaxMoneyRatio());

        String s = JSONUtil.objToStr(scoreCoupon);
        coupon.setCouponJson(s);
        //返回券 json
        Result add = couponService.add(coupon);
        if (add.getSuccess()) {
            add.setData(s);
        }
        return add;
    }

}
