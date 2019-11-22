package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.ClientAddress;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户地址信息表
 * @author chenLiJia
 * @since 2019-11-05 13:52:16
 * @version 1.0
 **/
public interface ClientAddressMapper extends Mapper<ClientAddress> {


    /**
     * 将某个用户的收货地址全部改为非默认地址
     *
     * @param clientId 1
     * @return java.lang.Integer
     * @author chenlijia
     * @since 下午 1:00 2019/8/15 0015
     **/
    Integer updateClientCommonAddressByClient(@Param("clientId") Integer clientId);
}
