package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.SystemConfig;
import tk.mybatis.mapper.common.Mapper;

/**
 * 系统配置
 * @author chenLiJia
 * @since 2020-07-20 14:17:44
 * @version 1.0
 **/
public interface SystemConfigMapper extends Mapper<SystemConfig> {
    SystemConfig selectByPrimaryKey(String systemKey);
}