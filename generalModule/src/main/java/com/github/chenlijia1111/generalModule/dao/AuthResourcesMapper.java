package com.github.chenlijia1111.generalModule.dao;

import com.github.chenlijia1111.generalModule.common.responseVo.auth.AuthVo;
import com.github.chenlijia1111.generalModule.entity.AuthResources;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 权限资源表
 * @author chenLiJia
 * @since 2020-04-20 09:56:17
 * @version 1.0
 **/
public interface AuthResourcesMapper extends Mapper<AuthResources> {


    /**
     * 根据角色集合查询这些角色的所有权限 包含按钮
     * @param roleIdSet
     * @return
     */
    List<AuthVo> listRoleAllAuth(@Param("roleIdSet") Set<Integer> roleIdSet);


    /**
     * 根据角色截查询这些角色的菜单权限 不包含按钮
     * @param roleIdSet
     * @return
     */
    List<AuthVo> listRolePageAuth(@Param("roleIdSet") Set<Integer> roleIdSet);

}
