package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.common.responseVo.category.CategoryVo;
import com.github.chenlijia1111.utils.common.Result;
import java.util.List;
import java.util.Set;

import com.github.chenlijia1111.commonModule.entity.Category;

/**
 * 商品分类
 * @author chenLiJia
 * @since 2020-03-12 15:35:34
 **/
public interface CategoryServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-03-12 15:35:34
     **/
    Result add(Category params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-03-12 15:35:34
     **/
    Result update(Category params);

    /**
     * 条件查询
     *
     * @param condition      1
     * @return      * @author chenLiJia
     * @since 2020-03-12 15:35:34
     **/
    List<Category> listByCondition(Category condition);

    /**
     * 递归查询这些类别所有的下级类别
     * @param idSet
     * @return
     */
    List<CategoryVo> listAllChildCategory(Set<Integer> idSet);

    /**
     * 批量修改状态
     * @param openStatus
     * @param idSet
     * @return
     */
    Result batchUpdateStatus(Integer openStatus,Set<Integer> idSet);

    /**
     * 批量删除
     * @param idSet
     * @return
     */
    Result batchDelete(Set<Integer> idSet);


}
