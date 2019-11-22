package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.entity.Coupon;

import java.util.List;
import java.util.Set;

/**
 * 优惠券
 * @author chenLiJia
 * @since 2019-11-21 15:29:01
 **/
public interface CouponServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-21 15:29:01
     **/
    Result add(Coupon params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-21 15:29:01
     **/
    Result update(Coupon params);

    /**
     * 主键查询优惠券
     * @since 下午 4:47 2019/11/21 0021
     * @param id 1
     * @return com.github.chenlijia1111.commonModule.entity.Coupon
     **/
    Coupon findById(String id);

    /**
     * 通过优惠券id集合查询优惠券集合
     * @since 上午 11:19 2019/11/22 0022
     * @param idSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.Coupon>
     **/
    List<Coupon> listByIdSet(Set<String> idSet);
}
