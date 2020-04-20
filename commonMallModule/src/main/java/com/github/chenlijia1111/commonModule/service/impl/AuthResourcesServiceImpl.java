package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.responseVo.auth.AuthVo;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.commonModule.entity.AuthResources;
import com.github.chenlijia1111.commonModule.dao.AuthResourcesMapper;
import com.github.chenlijia1111.commonModule.service.AuthResourcesServiceI;
import com.github.chenlijia1111.utils.list.Sets;
import com.github.chenlijia1111.utils.math.treeNode.TreeNodeUtil;
import com.github.chenlijia1111.utils.math.treeNode.TreeNodeVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 权限资源表
 * @author chenLiJia
 * @since 2020-04-20 09:56:37
 **/
@Service
public class AuthResourcesServiceImpl implements AuthResourcesServiceI {


    @Resource
    private AuthResourcesMapper authResourcesMapper; //角色权限资源


    /**
     * 添加
     *
     * @param params      添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-04-20 09:56:37
     **/
    @Override
    public Result add(AuthResources params){
    
        int i = authResourcesMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params      编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-04-20 09:56:37
     **/
    @Override
    public Result update(AuthResources params){
    
        int i = authResourcesMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询
     *
     * @param condition  
     * @return 
     * @author chenLiJia
     * @since 2020-04-20 09:56:37
     **/
    @Override
    public List<AuthResources> listByCondition(AuthResources condition){
    
        PropertyCheckUtil.transferObjectNotNull(condition, true);
        return authResourcesMapper.select(condition);
    }

    /**
     * 列出角色的所有权限 包含按钮
     * @param roleId
     * @return
     */
    @Override
    public List<TreeNodeVo> listRoleAllAuth(Integer roleId) {
        if(Objects.nonNull(roleId)){
            return listRoleAllAuth(Sets.asSets(roleId));
        }
        return new ArrayList<>();
    }

    /**
     * 根据角色集合查询这些角色的所有权限 包含按钮
     * @param roleIdSet
     * @return
     */
    @Override
    public List<TreeNodeVo> listRoleAllAuth(Set<Integer> roleIdSet) {
        if(Sets.isNotEmpty(roleIdSet)){
            List<AuthVo> authVos = authResourcesMapper.listRoleAllAuth(roleIdSet);
            List<TreeNodeVo> treeNodeVos = TreeNodeUtil.fillChildTreeNode(authVos);
            return treeNodeVos;
        }
        return new ArrayList<>();
    }

    /**
     * 列出角色的菜单权限 不包含按钮
     * @param roleId
     * @return
     */
    @Override
    public List<TreeNodeVo> listRolePageAuth(Integer roleId) {
        if(Objects.nonNull(roleId)){
            return listRolePageAuth(Sets.asSets(roleId));
        }
        return new ArrayList<>();
    }

    /**
     * 根据角色截查询这些角色的菜单权限 不包含按钮
     * @param roleIdSet
     * @return
     */
    @Override
    public List<TreeNodeVo> listRolePageAuth(Set<Integer> roleIdSet) {
        if(Sets.isNotEmpty(roleIdSet)){
            List<AuthVo> authVos = authResourcesMapper.listRolePageAuth(roleIdSet);
            List<TreeNodeVo> treeNodeVos = TreeNodeUtil.fillChildTreeNode(authVos);
            return treeNodeVos;
        }
        return new ArrayList<>();
    }


}
