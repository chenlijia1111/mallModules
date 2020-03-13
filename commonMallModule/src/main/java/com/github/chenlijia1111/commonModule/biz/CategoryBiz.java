package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.commonModule.common.requestVo.category.AddCategoryParams;
import com.github.chenlijia1111.commonModule.common.requestVo.category.UpdateCategoryParams;
import com.github.chenlijia1111.commonModule.common.responseVo.category.CategoryVo;
import com.github.chenlijia1111.commonModule.entity.Category;
import com.github.chenlijia1111.commonModule.service.CategoryServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 商品分类
 *
 * @author chenLiJia
 * @since 2020-03-12 15:35:34
 **/
@Service
public class CategoryBiz {

    @Autowired
    private CategoryServiceI categoryService;//类别

    /**
     * 添加类别
     *
     * @param params
     * @return
     */
    public Result add(AddCategoryParams params) {

        //判断类别名称是否已存在
        List<Category> categories = categoryService.listByCondition(new Category().setCategoryName(params.getCategoryName()).setDeleteStatus(BooleanConstant.NO_INTEGER));
        if (Lists.isNotEmpty(categories)) {
            return Result.failure("类别名称已存在");
        }

        //开始添加
        Category category = new Category();
        BeanUtils.copyProperties(params, category);

        category.setDeleteStatus(BooleanConstant.NO_INTEGER).
                setCreateTime(new Date()).
                setUpdateTime(new Date());
        return categoryService.add(category);
    }

    /**
     * 编辑类别
     *
     * @param params
     * @return
     */
    public Result update(UpdateCategoryParams params) {

        //判断是否存在
        List<Category> categories = categoryService.listByCondition(new Category().setId(params.getId()).setDeleteStatus(BooleanConstant.NO_INTEGER));
        if (Lists.isEmpty(categories)) {
            return Result.failure("数据不存在");
        }
        Category originalCategory = categories.get(0);

        //更新类别
        Category category = new Category();
        BeanUtils.copyProperties(params, category);

        category.setUpdateTime(new Date());
        Result result = categoryService.update(category);
        if (result.getSuccess()) {
            //判断有没有更新启用状态
            if (!Objects.equals(originalCategory.getOpenStatus(), params.getOpenStatus())) {
                //改变所有下级的启用状态
                List<CategoryVo> childCategoryList = categoryService.listAllChildCategory(Sets.asSets(params.getId()));
                Set<Integer> childIdSet = categoryListToIdSet(childCategoryList);
                categoryService.batchUpdateStatus(params.getOpenStatus(), childIdSet);
            }
        }
        return result;
    }

    /**
     * 将类别集合转化为id集合
     *
     * @param categories
     * @return
     */
    private Set<Integer> categoryListToIdSet(List<CategoryVo> categories) {
        Set<Integer> idSet = new HashSet<>();
        if (Lists.isNotEmpty(categories)) {
            for (int i = 0; i < categories.size(); i++) {
                CategoryVo categoryVo = categories.get(i);
                idSet.add(categoryVo.getId());
                List<CategoryVo> childCategory = categoryVo.getChildCategory();
                if (Lists.isNotEmpty(childCategory)) {
                    idSet.addAll(categoryListToIdSet(childCategory));
                }
            }
        }
        return idSet;
    }


    /**
     * 启用类别
     * 如果启用的是父类别 那么对应的所有子类别全部都会启用
     *
     * @param id
     * @return
     */
    public Result openStatus(Integer id) {
        //判断是否存在
        List<Category> categories = categoryService.listByCondition(new Category().setId(id).setDeleteStatus(BooleanConstant.NO_INTEGER));
        if (Lists.isEmpty(categories)) {
            return Result.failure("数据不存在");
        }
        Category originalCategory = categories.get(0);
        originalCategory.setOpenStatus(BooleanConstant.YES_INTEGER);

        Result result = categoryService.update(originalCategory);
        if (result.getSuccess()) {
            //改变所有下级的启用状态
            List<CategoryVo> childCategoryList = categoryService.listAllChildCategory(Sets.asSets(id));
            Set<Integer> childIdSet = categoryListToIdSet(childCategoryList);
            categoryService.batchUpdateStatus(BooleanConstant.YES_INTEGER, childIdSet);
        }
        return result;
    }

    /**
     * 批量启用
     * 如果启用的是父类别 那么对应的所有子类别全部都会启用
     *
     * @param idSet id集合
     * @return
     */
    public Result batchOpenStatus(Set<Integer> idSet) {

        if (Sets.isEmpty(idSet)) {
            return Result.failure("数据为空");
        }

        //进行批量修改
        Result result = categoryService.batchUpdateStatus(BooleanConstant.YES_INTEGER, idSet);
        if (result.getSuccess()) {
            //连带修改下级状态
            List<CategoryVo> childCategoryList = categoryService.listAllChildCategory(idSet);
            if (Lists.isNotEmpty(childCategoryList)) {
                Set<Integer> childIdSet = categoryListToIdSet(childCategoryList);
                categoryService.batchUpdateStatus(BooleanConstant.YES_INTEGER, childIdSet);
            }
        }

        return result;
    }

    /**
     * 禁用类别
     * 禁用之后 客户端应该就查询不到这个类别 以及这个类别的商品了(具体查询规则自由定制)
     * 如果禁用的是父类别 那么所有的子类别都会被禁用
     *
     * @param id
     * @return
     */
    public Result closeStatus(Integer id) {
        //判断是否存在
        List<Category> categories = categoryService.listByCondition(new Category().setId(id).setDeleteStatus(BooleanConstant.NO_INTEGER));
        if (Lists.isEmpty(categories)) {
            return Result.failure("数据不存在");
        }
        Category originalCategory = categories.get(0);
        originalCategory.setOpenStatus(BooleanConstant.NO_INTEGER);

        Result result = categoryService.update(originalCategory);
        if (result.getSuccess()) {
            //改变所有下级的启用状态
            List<CategoryVo> childCategoryList = categoryService.listAllChildCategory(Sets.asSets(id));
            Set<Integer> childIdSet = categoryListToIdSet(childCategoryList);
            categoryService.batchUpdateStatus(BooleanConstant.NO_INTEGER, childIdSet);
        }
        return result;
    }

    /**
     * 批量禁用类别
     * 如果禁用的是父类别 那么所有的子类别都会被禁用
     *
     * @param idSet
     * @return
     */
    public Result batchCloseStatus(Set<Integer> idSet) {
        if (Sets.isEmpty(idSet)) {
            return Result.failure("数据为空");
        }

        //进行批量修改
        Result result = categoryService.batchUpdateStatus(BooleanConstant.NO_INTEGER, idSet);
        if (result.getSuccess()) {
            //连带修改下级状态
            List<CategoryVo> childCategoryList = categoryService.listAllChildCategory(idSet);
            if (Lists.isNotEmpty(childCategoryList)) {
                Set<Integer> childIdSet = categoryListToIdSet(childCategoryList);
                categoryService.batchUpdateStatus(BooleanConstant.NO_INTEGER, childIdSet);
            }
        }

        return result;
    }

    /**
     * 删除类别
     * 如果删除的是父类别 那么所有的子类别都会被删除
     *
     * @param id
     * @return
     */
    public Result delete(Integer id) {
        //判断是否存在
        List<Category> categories = categoryService.listByCondition(new Category().setId(id).setDeleteStatus(BooleanConstant.NO_INTEGER));
        if (Lists.isEmpty(categories)) {
            return Result.failure("数据不存在");
        }
        Category originalCategory = categories.get(0);
        originalCategory.setDeleteStatus(BooleanConstant.YES_INTEGER);

        Result result = categoryService.update(originalCategory);
        if (result.getSuccess()) {
            //删除所有下级
            List<CategoryVo> childCategoryList = categoryService.listAllChildCategory(Sets.asSets(id));
            Set<Integer> childIdSet = categoryListToIdSet(childCategoryList);
            categoryService.batchDelete(childIdSet);
        }
        return result;
    }


    /**
     * 批量删除类别
     * 如果删除的是父类别 那么所有的子类别都会被删除
     *
     * @param idSet
     * @return
     */
    public Result batchDelete(Set<Integer> idSet) {
        if (Sets.isEmpty(idSet)) {
            return Result.failure("数据为空");
        }

        //进行批量修改
        Result result = categoryService.batchDelete(idSet);
        if (result.getSuccess()) {
            //连带删除下级
            List<CategoryVo> childCategoryList = categoryService.listAllChildCategory(idSet);
            if (Lists.isNotEmpty(childCategoryList)) {
                Set<Integer> childIdSet = categoryListToIdSet(childCategoryList);
                categoryService.batchDelete(childIdSet);
            }
        }

        return result;
    }


}
