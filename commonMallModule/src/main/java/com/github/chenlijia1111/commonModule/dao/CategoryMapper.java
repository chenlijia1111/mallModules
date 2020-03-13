package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.common.responseVo.category.CategoryVo;
import com.github.chenlijia1111.commonModule.entity.Category;
import com.github.chenlijia1111.utils.common.Result;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 商品分类
 * @author chenLiJia
 * @since 2020-03-12 15:35:19
 * @version 1.0
 **/
public interface CategoryMapper extends Mapper<Category> {


    /**
     * 递归查询这些类别所有的下级类别
     * @param idSet
     * @return
     */
    List<CategoryVo> listAllChildCategory(@Param("idSet") Set<Integer> idSet);


    /**
     * 批量修改状态
     * @param openStatus
     * @param idSet
     * @return
     */
    Result batchUpdateStatus(@Param("openStatus") Integer openStatus, @Param("idSet") Set<Integer> idSet);

    /**
     * 批量删除
     * @param idSet
     * @return
     */
    Result batchDelete(@Param("idSet") Set<Integer> idSet);

}