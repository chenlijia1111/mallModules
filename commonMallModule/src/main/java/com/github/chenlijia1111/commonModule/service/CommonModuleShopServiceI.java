package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.common.responseVo.shop.CommonMallSimpleShopVo;

import java.util.List;
import java.util.Set;

/**
 * 商家
 * 通用模块通的商家服务只定义接口
 * 具体实现需要调用者自己去实现
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/21 0021 下午 2:02
 **/
public interface CommonModuleShopServiceI {

    /**
     * 获取当前商家id
     *
     * @return java.lang.String
     * @since 下午 2:07 2019/11/21 0021
     **/
    String currentShopId();


    /**
     * 根据店铺Id集合查询店铺名称 以及logo
     * @param shopIdSet
     * @return
     */
    List<CommonMallSimpleShopVo> listByShopIdSet(Set<String> shopIdSet);

}
