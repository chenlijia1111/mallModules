package com.github.chenlijia1111.generalModule.service;

import com.github.chenlijia1111.generalModule.entity.SystemConfig;
import com.github.chenlijia1111.utils.common.Result;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 系统配置
 * @author chenLiJia
 * @since 2020-07-20 14:17:54
 **/
public interface SystemConfigServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-07-20 14:17:54
     **/
    Result add(SystemConfig params);

    /**
     * 编辑
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-07-20 14:17:54
     **/
    Result update(SystemConfig params);

   /**
     * 按条件编辑
     * @param entity
     * @param condition
     * @return
     */
    Result update(SystemConfig entity, Example condition);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return
     * @since 2020-07-20 14:17:54
     **/
    List<SystemConfig> listByCondition(SystemConfig condition);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return
     * @since 2020-07-20 14:17:54
     **/
    List<SystemConfig> listByCondition(Example condition);

    /**
     * 主键查询
     * @param key
     * @return
     */
    SystemConfig findByKey(String key);


}
