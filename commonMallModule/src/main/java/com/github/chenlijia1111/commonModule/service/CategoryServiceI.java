package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.common.responseVo.category.CategoryVo;
import com.github.chenlijia1111.utils.common.Result;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.chenlijia1111.commonModule.entity.Category;
import tk.mybatis.mapper.entity.Example;

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
     * 利用祖宗关系
     * 查询这些类别所有的下级类别
     * @param idSet
     * @return
     */
    List<CategoryVo> listAllChildCategory(Set<Integer> idSet);

    /**
     * 查询所有的上级类别，包含自己
     * @param idSet
     * @return
     */
    List<CategoryVo> listAllParentCategory(Set<Integer> idSet);

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

    /**
     * 修改
     * @param category set 内容
     * @param condition where 条件
     * @return
     */
    Result updateByCondition(Category category,Example condition);

    /**
     * 主键查询
     * @param id
     * @return
     */
    Category findById(Integer id);


    /**
     * 条件查询
     * @param condition
     * @return
     */
    List<Category> listByCondition(Example condition);

    /**
     * 拼接类别名称  上级/下级
     * @param categoryList
     * @return
     */
    Map<Integer, String> spliceCategoryName(List<CategoryVo> categoryList);

}
