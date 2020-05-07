package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.utils.common.Result;
import java.util.List;
import com.github.chenlijia1111.commonModule.entity.RoleAuth;
import tk.mybatis.mapper.entity.Example;

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
     **/
    Result update(RoleAuth params);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return
     **/
    List<RoleAuth> listByCondition(RoleAuth condition);

    /**
     * 条件查询
     * @param condition
     * @return
     */
    List<RoleAuth> listByCondition(Example condition);

    /**
     * 批量添加
     * @param roleAuthList
     * @return
     */
    Result batchAdd(List<RoleAuth> roleAuthList);

    /**
     * 按条件删除
     * @param condition
     * @return
     */
    Result deleteByExample(Example condition);


}
