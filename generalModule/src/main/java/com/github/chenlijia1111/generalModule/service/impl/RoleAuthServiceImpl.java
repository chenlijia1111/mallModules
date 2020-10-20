package com.github.chenlijia1111.generalModule.service.impl;

import com.github.chenlijia1111.generalModule.dao.RoleAuthMapper;
import com.github.chenlijia1111.generalModule.entity.RoleAuth;
import com.github.chenlijia1111.generalModule.service.RoleAuthServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 角色权限关联
 * @author chenLiJia
 * @since 2020-04-20 09:56:37
 **/
@Service
public class RoleAuthServiceImpl implements RoleAuthServiceI {


    @Resource
    private RoleAuthMapper roleAuthMapper;


    /**
     * 添加
     *
     * @param params      添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-04-20 09:56:37
     **/
    @Override
    public Result add(RoleAuth params){

        int i = roleAuthMapper.insertSelective(params);
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
    public Result update(RoleAuth params){

        int i = roleAuthMapper.updateByPrimaryKeySelective(params);
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
    public List<RoleAuth> listByCondition(RoleAuth condition){

        PropertyCheckUtil.transferObjectNotNull(condition, true);
        return roleAuthMapper.select(condition);
    }

    /**
     * 条件查询
     * @param condition
     * @return
     */
    @Override
    public List<RoleAuth> listByCondition(Example condition) {
        if(Objects.nonNull(condition)){
            return roleAuthMapper.selectByExample(condition);
        }
        return new ArrayList<>();
    }

    /**
     * 批量添加
     * @param roleAuthList
     * @return
     */
    @Override
    public Result batchAdd(List<RoleAuth> roleAuthList) {
        if(Lists.isNotEmpty(roleAuthList)){
            int i = roleAuthMapper.insertList(roleAuthList);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }

    /**
     * 按条件删除
     * @param condition
     * @return
     */
    @Override
    public Result deleteByExample(Example condition) {
        if(Objects.nonNull(condition)){
            int i = roleAuthMapper.deleteByExample(condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }


}
