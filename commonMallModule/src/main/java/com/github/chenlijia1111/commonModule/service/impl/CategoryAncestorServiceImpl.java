package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.responseVo.category.CategoryVo;
import com.github.chenlijia1111.commonModule.dao.CategoryAncestorMapper;
import com.github.chenlijia1111.commonModule.dao.CategoryMapper;
import com.github.chenlijia1111.commonModule.entity.Category;
import com.github.chenlijia1111.commonModule.entity.CategoryAncestor;
import com.github.chenlijia1111.commonModule.service.CategoryAncestorServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
     * 首先修改他自己的祖宗关系，把它自己的上级修改为新的上级
     * 然后再递归修改它的下级
     *
     * @param categoryId
     */
    @Override
    public void recursiveUpdateAncestor(Integer categoryId) {
        if (Objects.nonNull(categoryId)) {
            Date currentTime = new Date();
            Category currentCategory = categoryMapper.selectByPrimaryKey(categoryId);
            if (Objects.nonNull(currentCategory) && Objects.equals(BooleanConstant.NO_INTEGER, currentCategory.getDeleteStatus())) {
                // 更新它自己的祖宗关系
                // 先删除
                categoryAncestorMapper.delete(new CategoryAncestor().setCategoryId(categoryId));
                // 再添加
                if (Objects.nonNull(currentCategory.getParentId())) {
                    // 查询上级的所有祖宗关系，再一起添加
                    List<CategoryAncestor> categoryAncestorList = categoryAncestorMapper.select(new CategoryAncestor().setCategoryId(categoryId));
                    categoryAncestorList.stream().forEach(e -> e.setId(null).setCategoryId(categoryId).setCreateTime(currentTime));
                    categoryAncestorList.add(new CategoryAncestor().setCategoryId(categoryId).
                            setAncestorId(currentCategory.getParentId()).setCreateTime(currentTime));
                    categoryAncestorMapper.insertList(categoryAncestorList);
                }
            }
            // 再递归更新它的所有下级
            //查询这个类别的所有下级类别
            List<Category> categoryList = categoryMapper.select(new Category().setParentId(categoryId).setDeleteStatus(BooleanConstant.NO_INTEGER));
            if (Lists.isNotEmpty(categoryList)) {
                //更新这些类别的祖宗关系
                for (Category category : categoryList) {
                    recursiveUpdateAncestor(category.getId());
                }
            }
        }
    }

    /**
     * 更新类别祖宗关系
     * 推荐使用
     * 不用递归
     *
     * @param categoryId
     * @param originParentId
     */
    @Override
    public void recursiveUpdateAncestor(Integer categoryId, Integer originParentId) {
        // 演示
        // a -> b -> c -> d -> e
        // 原来的祖宗关系为
        // a -> b; a -> c; a -> d; a-> e;
        // b -> c; b -> d; b-> e;
        // c -> d; c-> e;
        // d-> e;
        // 切换为
        // a -> b -> c -> f -> g
        // 现在的祖宗关系为
        // a -> b; a -> c; a -> f; a-> g;
        // b -> c; b -> f; b-> g;
        // c -> f; c-> g;
        // d-> g;
        // 可以看到只需要更新跟当前类别上级有关的就可以了
        // 查询当前类别的所有上级
        // 查询当前类别的所有下级
        // 把下级(包含当前类别)相对于原上级的关联删掉 where categoryId in (所有下级id) and ancestorId in (原上级Id集合)
        // 新增下级(包含当前类别)相对于新上级的关联即可
        if (Objects.nonNull(categoryId)) {
            Date currentTime = new Date();
            Category currentCategory = categoryMapper.selectByPrimaryKey(categoryId);
            if (Objects.nonNull(currentCategory)) {
                // 所有的下级类别
                List<CategoryVo> allChildList = categoryMapper.listAllChildCategory(Sets.asSets(categoryId));
                // 所有下级类别id
                Set<Integer> allChildIdSet = allChildList.stream().map(e -> e.getId()).collect(Collectors.toSet());
                // 所有下级包含自己
                allChildIdSet.add(categoryId);
                // 查询原上级的所有上级
                Set<Integer> originAncestorIdSet = new HashSet<>();
                if (Objects.nonNull(originParentId)) {
                    // 原来所有上级
                    List<CategoryVo> originParentCategoryVoList = categoryMapper.listAllParentCategory(Sets.asSets(originParentId));
                    if (Lists.isNotEmpty(originParentCategoryVoList)) {
                        originAncestorIdSet = originParentCategoryVoList.stream().map(e -> e.getId()).collect(Collectors.toSet());
                    }
                }
                // 删除跟原上级有关联的关系
                if (Sets.isNotEmpty(allChildIdSet) && Sets.isNotEmpty(originAncestorIdSet)) {
                    categoryAncestorMapper.deleteByExample(Example.builder(CategoryAncestor.class).
                            where(Sqls.custom().andIn("categoryId", allChildIdSet).
                                    andIn("ancestorId", originAncestorIdSet)).build());
                }
                // 新增跟现上级有关联的关系
                // 当前的上级id集合
                Integer parentCategoryId = currentCategory.getParentId();
                Set<Integer> currentAncestorIdSet = new HashSet<>();
                if (Objects.nonNull(parentCategoryId)) {
                    // 原来所有上级
                    List<CategoryVo> originParentCategoryVoList = categoryMapper.listAllParentCategory(Sets.asSets(parentCategoryId));
                    if (Lists.isNotEmpty(originParentCategoryVoList)) {
                        currentAncestorIdSet = originParentCategoryVoList.stream().map(e -> e.getId()).collect(Collectors.toSet());
                    }
                }
                // 构建祖宗关系
                List<CategoryAncestor> categoryAncestorList = new ArrayList<>();
                if (Sets.isNotEmpty(currentAncestorIdSet) && Sets.isNotEmpty(allChildIdSet)) {
                    // 新增关系
                    for (Integer ancestorId : currentAncestorIdSet) {
                        for (Integer childId : allChildIdSet) {
                            CategoryAncestor categoryAncestor = new CategoryAncestor();
                            categoryAncestor.setCategoryId(childId);
                            categoryAncestor.setAncestorId(ancestorId);
                            categoryAncestor.setCreateTime(currentTime);
                            categoryAncestorList.add(categoryAncestor);
                        }
                    }
                }
                if (Lists.isNotEmpty(categoryAncestorList)) {
                    categoryAncestorMapper.insertList(categoryAncestorList);
                }
            }
        }
    }
}
