package com.github.chenlijia1111.mallweb.service.impl;

import com.github.chenlijia1111.commonModule.service.CommonModuleShopServiceI;
import org.springframework.stereotype.Service;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/29 0029 上午 10:00
 **/
@Service
public class CommonModuleShopServiceImpl implements CommonModuleShopServiceI {

    /**
     * 获取当前商家id
     *
     * @return java.lang.String
     * @since 下午 2:07 2019/11/21 0021
     **/
    @Override
    public String currentShopId() {
        return "1";
    }
}
