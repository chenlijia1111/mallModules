package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.Coupon;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 优惠券
 * @author chenLiJia
 * @since 2019-11-21 15:28:47
 * @version 1.0
 **/
public interface CouponMapper extends Mapper<Coupon> {


    /**
     * 通过优惠券id集合查询优惠券集合
     * @since 上午 11:19 2019/11/22 0022
     * @param idSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.Coupon>
     **/
    List<Coupon> listByIdSet(@Param("idSet") Set<String> idSet);

    /**
     * 批量领取优惠券 优惠券数量-1
     * @param idSet
     * @return
     */
    Integer batchReceiveCoupon(@Param("idSet") Set<String> idSet);

}
