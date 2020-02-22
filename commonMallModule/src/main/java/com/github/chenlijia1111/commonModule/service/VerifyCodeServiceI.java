package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.entity.VerifyCode;
import com.github.chenlijia1111.utils.common.Result;

import java.util.List;

/**
 * 系统验证码
 *
 * @author chenLiJia
 * @since 2020-02-22 09:04:18
 **/
public interface VerifyCodeServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-02-22 09:04:18
     **/
    Result add(VerifyCode params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-02-22 09:04:18
     **/
    Result update(VerifyCode params);

    /**
     * 条件查询
     *
     * @param condition 1
     * @return * @author chenLiJia
     * @since 2020-02-22 09:04:18
     **/
    List<VerifyCode> listByCondition(VerifyCode condition);

    /**
     * 校验验证码
     *
     * @param codeType  验证码类型
     * @param codeKey   验证码key
     * @param codeValue 验证码内容
     * @return
     */
    Result checkExpire(Integer codeType, String codeKey, String codeValue);


}
