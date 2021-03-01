package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.responseVo.category.CategoryVo;
import com.github.chenlijia1111.commonModule.dao.CategoryMapper;
import com.github.chenlijia1111.commonModule.entity.Category;
import com.github.chenlijia1111.commonModule.service.CategoryServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

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
     * 编辑
     * 字段为 null 会更新
     * 允许 parentId 为 null
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-03-12 15:35:34
     **/
    @Override
    public Result updateWithNull(Category params) {
        int i = categoryMapper.updateByPrimaryKey(params);
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
     * 这里不将子类整理好，如有需要，可以用树节点工具进行整理返回前端
     * <p>
     * 传递的数据如果为空 就查询所有分类
     *
     * @param idSet
     * @return
     * @see com.github.chenlijia1111.utils.math.treeNode.TreeNodeUtil
     */
    @Override
    public List<CategoryVo> listAllChildCategory(Set<Integer> idSet) {
        List<CategoryVo> categories = categoryMapper.listAllChildCategory(idSet);
        return categories;
    }

    /**
     * 查询所有的上级类别，包含自己
     *
     * @param idSet
     * @return
     */
    @Override
    public List<CategoryVo> listAllParentCategory(Set<Integer> idSet) {
        if (Sets.isEmpty(idSet)) {
            return new ArrayList<>();
        }
        return categoryMapper.listAllParentCategory(idSet);
    }

    /**
     * 批量修改状态
     *
     * @param openStatus
     * @param idSet
     * @return
     */
    @Override
    public Result batchUpdateStatus(Integer openStatus, Set<Integer> idSet) {
        if (Objects.nonNull(openStatus) && Sets.isNotEmpty(idSet)) {
            categoryMapper.batchUpdateStatus(openStatus, idSet);
        }
        return Result.success("操作成功");
    }

    /**
     * 批量删除
     *
     * @param idSet
     * @return
     */
    @Override
    public Result batchDelete(Set<Integer> idSet) {
        if (Sets.isNotEmpty(idSet)) {
            categoryMapper.batchDelete(idSet);
        }
        return Result.success("操作成功");
    }

    /**
     * 修改
     *
     * @param category  set 内容
     * @param condition where 条件
     * @return
     */
    @Override
    public Result updateByCondition(Category category, Example condition) {
        if (Objects.nonNull(category) && Objects.nonNull(condition)) {
            int i = categoryMapper.updateByExample(category, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    @Override
    public Category findById(Integer id) {
        if (Objects.nonNull(id)) {
            return categoryMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     */
    @Override
    public List<Category> listByCondition(Example condition) {
        if (Objects.nonNull(condition)) {
            return categoryMapper.selectByExample(condition);
        }
        return new ArrayList<>();
    }

    /**
     * 拼接类别名称  上级/下级
     *
     * @param categoryList
     * @return
     */
    @Override
    public Map<Integer, String> spliceCategoryName(List<CategoryVo> categoryList) {
        //转为 map 映射
        Map<Integer, String> map = new HashMap<>();
        if (Lists.isNotEmpty(categoryList)) {
            for (CategoryVo vo : categoryList) {
                map.put(vo.getId(), vo.getCategoryName());
            }

            //进行处理
            for (CategoryVo vo : categoryList) {
                //判断有没有父类，有父类就把父类的名称拼接在前面
                Integer parentId = vo.getParentId();
                if (Objects.nonNull(parentId)) {
                    String parentCategoryName = map.get(parentId);
                    if (StringUtils.isNotEmpty(parentCategoryName)) {
                        //修改类别名称为拼接
                        map.put(vo.getId(), parentCategoryName + "/" + vo.getCategoryName());
                    }
                }
            }
        }
        return map;
    }
}
