package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.RoleAuth;
import tk.mybatis.mapper.common.Mapper;

/**
 * 角色权限关联
 * @author chenLiJia
 * @since 2020-04-20 09:56:17
 * @version 1.0
 **/
public interface RoleAuthMapper extends Mapper<RoleAuth> {
    RoleAuth selectByPrimaryKey(Integer id);
}