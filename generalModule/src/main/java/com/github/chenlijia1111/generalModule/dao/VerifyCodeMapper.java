package com.github.chenlijia1111.generalModule.dao;

import com.github.chenlijia1111.generalModule.entity.VerifyCode;
import tk.mybatis.mapper.common.Mapper;

/**
 * 系统验证码
 * @author chenLiJia
 * @since 2020-02-22 09:04:10
 * @version 1.0
 **/
public interface VerifyCodeMapper extends Mapper<VerifyCode> {
    VerifyCode selectByPrimaryKey(Integer id);


    /**
     * 清除过期的验证码
     */
    Integer clearExpireCode();

}
