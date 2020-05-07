package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.entity.AuthResources;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.math.treeNode.TreeNodeVo;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;

/**
 * 权限资源表
 * @author chenLiJia
 * @since 2020-04-20 09:56:37
 **/
public interface AuthResourcesServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-04-20 09:56:37
     **/
    Result add(AuthResources params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-04-20 09:56:37
     **/
    Result update(AuthResources params);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return      * @author chenLiJia
     * @since 2020-04-20 09:56:37
     **/
    List<AuthResources> listByCondition(AuthResources condition);


    /**
     * 列出角色的所有权限 包含按钮
     * 可做参考
     * @param roleId
     * @return
     */
    List<TreeNodeVo> listRoleAllAuth(Integer roleId);

    /**
     * 根据角色集合查询这些角色的所有权限 包含按钮
     * 可做参考
     * @param roleIdSet
     * @return
     */
    List<TreeNodeVo> listRoleAllAuth(Set<Integer> roleIdSet);

    /**
     * 列出角色的菜单权限 不包含按钮
     * 可做参考
     * @param roleId
     * @return
     */
    List<TreeNodeVo> listRolePageAuth(Integer roleId);

    /**
     * 根据角色截查询这些角色的菜单权限 不包含按钮
     * 可做参考
     * @param roleIdSet
     * @return
     */
    List<TreeNodeVo> listRolePageAuth(Set<Integer> roleIdSet);


    /**
     * 根据角色查询角色所有的权限资源id
     * 用于角色详情回显
     * @param roleId
     * @return
     */
    List<Integer> listRoleAuthId(Integer roleId);

    /**
     * 条件查询
     * @param condition
     * @return
     */
    List<AuthResources> listByCondition(Example condition);


}
