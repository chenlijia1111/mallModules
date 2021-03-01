package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.commonModule.common.requestVo.category.AddCategoryParams;
import com.github.chenlijia1111.commonModule.common.requestVo.category.UpdateCategoryParams;
import com.github.chenlijia1111.commonModule.common.responseVo.category.CategoryVo;
import com.github.chenlijia1111.commonModule.entity.Category;
import com.github.chenlijia1111.commonModule.entity.CategoryAncestor;
import com.github.chenlijia1111.commonModule.service.CategoryAncestorServiceI;
import com.github.chenlijia1111.commonModule.service.CategoryServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商品分类
 * <p>
 * TODO 优化上下级，新增祖宗类别进行管理，使用树节点
 *
 * @author chenLiJia
 * @since 2020-03-12 15:35:34
 **/
@Service
public class CategoryBiz {

    @Autowired
    private CategoryServiceI categoryService;//类别
    @Autowired
    private CategoryAncestorServiceI categoryAncestorService;//类别祖宗

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

        Date currentTime = new Date();

        category.setDeleteStatus(BooleanConstant.NO_INTEGER).
                setCreateTime(currentTime).
                setUpdateTime(currentTime);

        Result result = categoryService.add(category);
        if (result.getSuccess()) {
            //如果有上级，需要把上级的祖宗关系复制过来
            Integer parentId = params.getParentId();
            if (Objects.nonNull(parentId)) {
                //查询上级的祖宗关系
                List<CategoryAncestor> categoryAncestorList = categoryAncestorService.listByCondition(new CategoryAncestor().setCategoryId(parentId));
                //修改信息
                categoryAncestorList.stream().forEach(e -> e.setCategoryId(category.getId()).setCreateTime(currentTime));
                //添加当前父类别
                CategoryAncestor categoryAncestor = new CategoryAncestor().
                        setCategoryId(category.getId()).
                        setAncestorId(parentId).
                        setCreateTime(currentTime).
                        setId(null);
                categoryAncestorList.add(categoryAncestor);
                //批量添加祖宗关系
                categoryAncestorService.batchAdd(categoryAncestorList);
            }
        }
        return result;
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
        Integer originParentId = originalCategory.getParentId();

        //更新类别
        Category category = originalCategory;
        BeanUtils.copyProperties(params, category);

        category.setUpdateTime(new Date());
        Result result = categoryService.updateWithNull(category);
        if (result.getSuccess()) {
            //判断有没有更新上级，如果更新了上级，那么需要将所有下级的祖宗关系进行修改
            if (!Objects.equals(originParentId, params.getParentId()) ||
                    !Objects.equals(originalCategory.getOpenStatus(), params.getOpenStatus())) {
                categoryAncestorService.recursiveUpdateAncestor(params.getId(), originParentId);
            }

            //判断有没有更新启用状态
            if (!Objects.equals(originalCategory.getOpenStatus(), params.getOpenStatus())) {
                //改变所有下级的启用状态
                List<CategoryVo> childCategoryList = categoryService.listAllChildCategory(Sets.asSets(params.getId()));
                Set<Integer> childIdSet = childCategoryList.stream().map(e -> e.getId()).collect(Collectors.toSet());
                if (Sets.isNotEmpty(childIdSet)) {
                    //set内容
                    Category setCondition = new Category().setOpenStatus(params.getOpenStatus());
                    //where内容
                    Example whereCondition = Example.builder(Category.class).where(Sqls.custom().andIn("id", childIdSet)).build();
                    categoryService.updateByCondition(setCondition, whereCondition);
                }
            }

        }
        return result;
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
            Set<Integer> childIdSet = childCategoryList.stream().map(e -> e.getId()).collect(Collectors.toSet());
            if (Sets.isNotEmpty(childIdSet)) {
                //set内容
                Category setCondition = new Category().setOpenStatus(BooleanConstant.YES_INTEGER);
                //where内容
                Example whereCondition = Example.builder(Category.class).where(Sqls.custom().andIn("id", childIdSet)).build();
                categoryService.updateByCondition(setCondition, whereCondition);
            }
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
            //改变所有下级的启用状态
            List<CategoryVo> childCategoryList = categoryService.listAllChildCategory(idSet);
            Set<Integer> childIdSet = childCategoryList.stream().map(e -> e.getId()).collect(Collectors.toSet());
            if (Sets.isNotEmpty(childIdSet)) {
                //set内容
                Category setCondition = new Category().setOpenStatus(BooleanConstant.YES_INTEGER);
                //where内容
                Example whereCondition = Example.builder(Category.class).where(Sqls.custom().andIn("id", childIdSet)).build();
                categoryService.updateByCondition(setCondition, whereCondition);
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
            Set<Integer> childIdSet = childCategoryList.stream().map(e -> e.getId()).collect(Collectors.toSet());
            if (Sets.isNotEmpty(childIdSet)) {
                //set内容
                Category setCondition = new Category().setOpenStatus(BooleanConstant.NO_INTEGER);
                //where内容
                Example whereCondition = Example.builder(Category.class).where(Sqls.custom().andIn("id", childIdSet)).build();
                categoryService.updateByCondition(setCondition, whereCondition);
            }
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
            //改变所有下级的启用状态
            List<CategoryVo> childCategoryList = categoryService.listAllChildCategory(idSet);
            Set<Integer> childIdSet = childCategoryList.stream().map(e -> e.getId()).collect(Collectors.toSet());
            if (Sets.isNotEmpty(childIdSet)) {
                //set内容
                Category setCondition = new Category().setOpenStatus(BooleanConstant.NO_INTEGER);
                //where内容
                Example whereCondition = Example.builder(Category.class).where(Sqls.custom().andIn("id", childIdSet)).build();
                categoryService.updateByCondition(setCondition, whereCondition);
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
            Set<Integer> childIdSet = childCategoryList.stream().map(e -> e.getId()).collect(Collectors.toSet());
            if (Sets.isNotEmpty(childIdSet)) {
                //set内容
                Category setCondition = new Category().setDeleteStatus(BooleanConstant.YES_INTEGER);
                //where内容
                Example whereCondition = Example.builder(Category.class).where(Sqls.custom().andIn("id", childIdSet)).build();
                categoryService.updateByCondition(setCondition, whereCondition);
            }
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
            //删除所有下级
            List<CategoryVo> childCategoryList = categoryService.listAllChildCategory(idSet);
            Set<Integer> childIdSet = childCategoryList.stream().map(e -> e.getId()).collect(Collectors.toSet());
            if (Sets.isNotEmpty(childIdSet)) {
                //set内容
                Category setCondition = new Category().setDeleteStatus(BooleanConstant.YES_INTEGER);
                //where内容
                Example whereCondition = Example.builder(Category.class).where(Sqls.custom().andIn("id", childIdSet)).build();
                categoryService.updateByCondition(setCondition, whereCondition);
            }
        }

        return result;
    }


}
