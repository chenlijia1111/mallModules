package com.github.chenlijia1111.mallweb.service.impl;

import com.github.chenlijia1111.commonModule.service.CommonModuleUserServiceI;
import org.springframework.stereotype.Service;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/29 0029 上午 9:55
 **/
@Service
public class CommonModuleUserServiceImpl implements CommonModuleUserServiceI {


    /**
     * 获取当前登录用户id
     *
     * @return java.lang.String
     * @since 下午 2:05 2019/11/21 0021
     **/
    @Override
    public String currentUserId() {
        return "1";
    }
}
