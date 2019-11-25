package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.dao.CouponMapper;
import com.github.chenlijia1111.commonModule.entity.Coupon;
import com.github.chenlijia1111.commonModule.service.CouponServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 优惠券
 *
 * @author chenLiJia
 * @since 2019-11-21 15:29:01
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "CouponServiceI")
public class CouponServiceImpl implements CouponServiceI {


    @Resource
    private CouponMapper couponMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-21 15:29:01
     **/
    public Result add(Coupon params) {

        int i = couponMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-21 15:29:01
     **/
    public Result update(Coupon params) {

        int i = couponMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 主键查询优惠券
     *
     * @param id 1
     * @return com.github.chenlijia1111.commonModule.entity.Coupon
     * @since 下午 4:47 2019/11/21 0021
     **/
    @Override
    public Coupon findById(String id) {
        if (StringUtils.isNotEmpty(id)) {
            return couponMapper.selectByPrimaryKey(id);
        }
        return null;
    }


    /**
     * 通过优惠券id集合查询优惠券集合
     *
     * @param idSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.Coupon>
     * @since 上午 11:19 2019/11/22 0022
     **/
    @Override
    public List<Coupon> listByIdSet(Set<String> idSet) {
        if (Sets.isNotEmpty(idSet)) {
            return couponMapper.listByIdSet(idSet);
        }
        return Lists.newArrayList();
    }


}
