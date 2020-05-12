package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.common.responseVo.user.CommonSimpleUser;

import java.util.List;
import java.util.Set;

/**
 * 用户 服务层
 * 调用者需要自己实现,
 * 在通用模块里面，只定义相关的接口
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/21 0021 下午 2:02
 **/
public interface CommonModuleUserServiceI {

    /**
     * 获取当前登录用户id
     *
     * @return java.lang.String
     * @since 下午 2:05 2019/11/21 0021
     **/
    String currentUserId();

    /**
     * 查询简单用户信息
     * @param idSet
     * @return
     */
    List<CommonSimpleUser> listCommonSimpleUserByIdSet(Set<String> idSet);

}
