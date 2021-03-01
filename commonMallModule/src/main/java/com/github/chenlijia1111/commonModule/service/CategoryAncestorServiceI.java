package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.entity.CategoryAncestor;
import com.github.chenlijia1111.utils.common.Result;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 类别祖宗关系
 *
 * @author chenLiJia
 * @since 2020-07-17 10:33:07
 **/
public interface CategoryAncestorServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-07-17 10:33:07
     **/
    Result add(CategoryAncestor params);

    /**
     * 编辑
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-07-17 10:33:07
     **/
    Result update(CategoryAncestor params);

    /**
     * 按条件编辑
     *
     * @param entity
     * @param condition
     * @return
     */
    Result update(CategoryAncestor entity, Example condition);

    /**
     * 条件查询
     *
     * @param condition 1
     * @return
     * @since 2020-07-17 10:33:07
     **/
    List<CategoryAncestor> listByCondition(CategoryAncestor condition);

    /**
     * 条件查询
     *
     * @param condition 1
     * @return
     * @since 2020-07-17 10:33:07
     **/
    List<CategoryAncestor> listByCondition(Example condition);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    Result batchAdd(List<CategoryAncestor> list);

    /**
     * 递归更新当前类别的下级类别的祖宗关系
     *
     * @param categoryId
     */
    void recursiveUpdateAncestor(Integer categoryId);

    /**
     * 更新类别祖宗关系
     * 推荐使用
     * 不用递归
     *
     * @param categoryId
     * @param originParentId
     */
    void recursiveUpdateAncestor(Integer categoryId, Integer originParentId);

}
