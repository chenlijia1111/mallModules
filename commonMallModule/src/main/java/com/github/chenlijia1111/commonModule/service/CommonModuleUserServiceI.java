package com.github.chenlijia1111.commonModule.service;

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

}
