package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.dao.VerifyCodeMapper;
import com.github.chenlijia1111.commonModule.entity.VerifyCode;
import com.github.chenlijia1111.commonModule.service.VerifyCodeServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统验证码
 *
 * @author chenLiJia
 * @since 2020-02-22 09:04:18
 **/
@Service
public class VerifyCodeServiceImpl implements VerifyCodeServiceI {


    @Resource
    private VerifyCodeMapper verifyCodeMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-02-22 09:04:18
     **/
    @Override
    public Result add(VerifyCode params) {
        //判断是否存在,如果存在,就变更
        VerifyCode verifyCodeCondition = new VerifyCode().
                setCodeKey(params.getCodeKey()).
                setCodeType(params.getCodeType());
        List<VerifyCode> verifyCodeList = verifyCodeMapper.select(verifyCodeCondition);
        if (Lists.isNotEmpty(verifyCodeList)) {
            //变更
            params.setId(verifyCodeList.get(0).getId());
            params.setCreateTime(new Date());
            return update(params);
        }
        int i = verifyCodeMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-02-22 09:04:18
     **/
    @Override
    public Result update(VerifyCode params) {

        int i = verifyCodeMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @author chenLiJia
     * @since 2020-02-22 09:04:18
     **/
    @Override
    public List<VerifyCode> listByCondition(VerifyCode condition) {

        PropertyCheckUtil.transferObjectNotNull(condition, true);
        return verifyCodeMapper.select(condition);
    }

    /**
     * 校验验证码
     *
     * @param codeType  验证码类型
     * @param codeKey   验证码key
     * @param codeValue 验证码内容
     * @return
     */
    @Override
    public Result checkExpire(Integer codeType, String codeKey, String codeValue) {
        if (StringUtils.isEmpty(codeKey)) {
            return Result.failure("验证码key为空");
        }

        if (Objects.isNull(codeType)) {
            return Result.failure("验证码类型为空");
        }

        if (StringUtils.isEmpty(codeValue)) {
            return Result.failure("验证码内容为空");
        }

        //查询是否存在
        VerifyCode verifyCodeCondition = new VerifyCode().setCodeType(codeType).setCodeKey(codeKey);
        List<VerifyCode> verifyCodeList = verifyCodeMapper.select(verifyCodeCondition);
        if (Lists.isEmpty(verifyCodeList)) {
            return Result.failure("验证码不存在");
        }

        Optional<VerifyCode> max = verifyCodeList.stream().max(Comparator.comparing(VerifyCode::getCreateTime));
        if (max.isPresent()) {
            VerifyCode verifyCode = max.get();
            //判断是否过期
            Date limitTime = verifyCode.getLimitTime();
            if (limitTime.getTime() <= System.currentTimeMillis()) {
                return Result.failure("验证码已过期");
            }

            //判断验证码是否正确
            if (!Objects.equals(verifyCode.getCodeValue(), codeValue)) {
                return Result.failure("验证码错误");
            }

            //验证通过
            return Result.success("验证码验证通过");
        }

        return Result.failure("验证码验证失败");
    }

    /**
     * 清除过期的验证码
     */
    @Override
    public void clearExpireCode() {
        verifyCodeMapper.clearExpireCode();
    }


}
