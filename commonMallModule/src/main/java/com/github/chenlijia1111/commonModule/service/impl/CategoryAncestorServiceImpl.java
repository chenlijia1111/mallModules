package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.dao.CategoryAncestorMapper;
import com.github.chenlijia1111.commonModule.dao.CategoryMapper;
import com.github.chenlijia1111.commonModule.entity.Category;
import com.github.chenlijia1111.commonModule.entity.CategoryAncestor;
import com.github.chenlijia1111.commonModule.service.CategoryAncestorServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 类别祖宗关系
 *
 * @author chenLiJia
 * @since 2020-07-17 10:33:07
 **/
@Service
public class CategoryAncestorServiceImpl implements CategoryAncestorServiceI {


    @Resource
    private CategoryAncestorMapper categoryAncestorMapper;
    @Resource
    private CategoryMapper categoryMapper;//类别


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-07-17 10:33:07
     **/
    @Override
    public Result add(CategoryAncestor params) {

        int i = categoryAncestorMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-07-17 10:33:07
     **/
    @Override
    public Result update(CategoryAncestor params) {

        int i = categoryAncestorMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 按条件编辑
     *
     * @param entity
     * @param condition
     * @return
     */
    @Override
    public Result update(CategoryAncestor entity, Example condition) {
        if (Objects.nonNull(entity) && Objects.nonNull(condition)) {
            int i = categoryAncestorMapper.updateByExampleSelective(entity, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @since 2020-07-17 10:33:07
     **/
    @Override
    public List<CategoryAncestor> listByCondition(CategoryAncestor condition) {

        PropertyCheckUtil.transferObjectNotNull(condition, true);
        return categoryAncestorMapper.select(condition);
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @since 2020-07-17 10:33:07
     **/
    @Override
    public List<CategoryAncestor> listByCondition(Example condition) {

        if (null != condition) {
            List<CategoryAncestor> list = categoryAncestorMapper.selectByExample(condition);
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    @Override
    public Result batchAdd(List<CategoryAncestor> list) {
        if (Lists.isNotEmpty(list)) {
            int i = categoryAncestorMapper.insertList(list);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }

    /**
     * 递归更新当前类别的下级类别的祖宗关系
     *
     * @param categoryId
     */
    @Override
    public void recursiveUpdateAncestor(Integer categoryId) {
        if (Objects.nonNull(categoryId)) {
            //查询这个类别的所有下级类别
            List<Category> categoryList = categoryMapper.select(new Category().setParentId(categoryId).setDeleteStatus(BooleanConstant.NO_INTEGER));
            if (Lists.isNotEmpty(categoryList)) {
                //查询当前类别的祖宗关系
                List<CategoryAncestor> categoryAncestorList = categoryAncestorMapper.select(new CategoryAncestor().setCategoryId(categoryId));
                //更新这些类别的祖宗关系
                Date currentTime = new Date();
                for (Category category : categoryList) {
                    //先删除这些类别的祖宗关系
                    categoryAncestorMapper.delete(new CategoryAncestor().setCategoryId(category.getId()));
                    //再更新这些类别的祖宗关系
                    categoryAncestorList.stream().forEach(e -> e.setCategoryId(category.getId()).setCreateTime(currentTime).setId(null));
                    //添加当前类别为祖宗
                    categoryAncestorList.add(new CategoryAncestor().setCategoryId(category.getId()).setAncestorId(categoryId).setCreateTime(currentTime));
                    categoryAncestorMapper.insertList(categoryAncestorList);

                    //进行递归
                    recursiveUpdateAncestor(category.getId());
                }
            }
        }
    }
}
