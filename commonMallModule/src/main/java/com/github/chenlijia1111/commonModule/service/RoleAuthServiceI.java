package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;
import java.util.List;
import com.github.chenlijia1111.commonModule.entity.RoleAuth;

/**
 * 角色权限关联
 * @author chenLiJia
 * @since 2020-04-20 09:56:37
 **/
public interface RoleAuthServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-04-20 09:56:37
     **/
    Result add(RoleAuth params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-04-20 09:56:37
     **/
    Result update(RoleAuth params);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return      * @author chenLiJia
     * @since 2020-04-20 09:56:37
     **/
    List<RoleAuth> listByCondition(RoleAuth condition);


}
