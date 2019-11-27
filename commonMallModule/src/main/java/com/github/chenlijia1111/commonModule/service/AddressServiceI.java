package com.github.chenlijia1111.commonModule.service;


import com.github.chenlijia1111.commonModule.entity.Area;
import com.github.chenlijia1111.commonModule.entity.City;
import com.github.chenlijia1111.commonModule.entity.Province;

import java.util.List;

/**
 * 地址
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 上午 9:37
 **/
public interface AddressServiceI {

    /**
     * 条件查询省
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.Province>
     * @since 上午 9:45 2019/11/1 0001
     **/
    List<Province> listByCondition(Province condition);

    /**
     * 条件查询市
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.City>
     * @since 上午 9:47 2019/11/1 0001
     **/
    List<City> listByCondition(City condition);

    /**
     * 条件查询区
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.Area>
     * @since 上午 9:47 2019/11/1 0001
     **/
    List<Area> listByCondition(Area condition);

}
