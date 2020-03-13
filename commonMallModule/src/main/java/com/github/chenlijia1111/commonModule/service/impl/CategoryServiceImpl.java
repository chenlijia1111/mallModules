package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.responseVo.category.CategoryVo;
import com.github.chenlijia1111.commonModule.dao.CategoryMapper;
import com.github.chenlijia1111.commonModule.entity.Category;
import com.github.chenlijia1111.commonModule.service.CategoryServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品分类
 *
 * @author chenLiJia
 * @since 2020-03-12 15:35:34
 **/
@Service
public class CategoryServiceImpl implements CategoryServiceI {


    @Resource
    private CategoryMapper categoryMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-03-12 15:35:34
     **/
    @Override
    public Result add(Category params) {

        int i = categoryMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-03-12 15:35:34
     **/
    @Override
    public Result update(Category params) {

        int i = categoryMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @author chenLiJia
     * @since 2020-03-12 15:35:34
     **/
    @Override
    public List<Category> listByCondition(Category condition) {

        PropertyCheckUtil.transferObjectNotNull(condition, true);
        return categoryMapper.select(condition);
    }

    /**
     * 递归查询这些类别所有的下级类别
     *
     * @param idSet
     * @return
     */
    @Override
    public List<CategoryVo> listAllChildCategory(Set<Integer> idSet) {
        if (Sets.isNotEmpty(idSet)) {
            List<CategoryVo> categories = categoryMapper.listAllChildCategory(idSet);
            if (Lists.isNotEmpty(categories)) {
                //下级类别id集合
                Set<Integer> childIdSet = categories.stream().map(e -> e.getId()).collect(Collectors.toSet());
                //查询下级有没有下级
                List<CategoryVo> categoryList = listAllChildCategory(childIdSet);
                if (Lists.isNotEmpty(categoryList)) {
                    //处理下级列表
                    Map<Integer, List<CategoryVo>> parentChildList = categoryList.stream().collect(Collectors.groupingBy(Category::getParentId));
                    for (CategoryVo category : categories) {
                        List<CategoryVo> childCategoryList = parentChildList.get(category.getId());
                        category.setChildCategory(childCategoryList);
                    }
                }
            }
            return categories;
        }
        return new ArrayList<>();
    }

    /**
     * 批量修改状态
     * @param openStatus
     * @param idSet
     * @return
     */
    @Override
    public Result batchUpdateStatus(Integer openStatus, Set<Integer> idSet) {
        if(Objects.nonNull(openStatus) && Sets.isNotEmpty(idSet)){
            categoryMapper.batchUpdateStatus(openStatus, idSet);
        }
        return Result.success("操作成功");
    }

    /**
     * 批量删除
     * @param idSet
     * @return
     */
    @Override
    public Result batchDelete(Set<Integer> idSet) {
        if(Sets.isNotEmpty(idSet)){
            categoryMapper.batchDelete(idSet);
        }
        return Result.success("操作成功");
    }


}
