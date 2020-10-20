package com.github.chenlijia1111.generalModule.dao;

import com.github.chenlijia1111.generalModule.entity.RoleAuth;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 角色权限关联
 * @author chenLiJia
 * @since 2020-04-20 09:56:17
 * @version 1.0
 **/
public interface RoleAuthMapper extends Mapper<RoleAuth>, InsertListMapper<RoleAuth> {
}
