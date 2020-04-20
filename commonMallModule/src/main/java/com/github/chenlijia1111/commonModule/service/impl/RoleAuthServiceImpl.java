package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.commonModule.entity.RoleAuth;
import com.github.chenlijia1111.commonModule.dao.RoleAuthMapper;
import com.github.chenlijia1111.commonModule.service.RoleAuthServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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


}
