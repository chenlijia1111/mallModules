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
import tk.mybatis.mapper.entity.Example;

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
     * 这里不将子类整理好，如有需要，可以用树节点工具进行整理返回前端
     * @see com.github.chenlijia1111.utils.math.treeNode.TreeNodeUtil
     *
     * @param idSet
     * @return
     */
    @Override
    public List<CategoryVo> listAllChildCategory(Set<Integer> idSet) {
        if (Sets.isNotEmpty(idSet)) {
            List<CategoryVo> categories = categoryMapper.listAllChildCategory(idSet);
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

    /**
     * 修改
     * @param category set 内容
     * @param condition where 条件
     * @return
     */
    @Override
    public Result updateByCondition(Category category, Example condition) {
        if(Objects.nonNull(category) && Objects.nonNull(condition)){
            int i = categoryMapper.updateByExample(category, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }
}
