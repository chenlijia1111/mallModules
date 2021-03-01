package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.CategoryAncestor;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 类别祖宗关系
 * @author chenLiJia
 * @since 2020-07-17 10:32:48
 * @version 1.0
 **/
public interface CategoryAncestorMapper extends Mapper<CategoryAncestor>, InsertListMapper<CategoryAncestor> {

    /**
     * 删除跟自己有关联的类别
     * @param categoryId
     */
    void deleteRelation(@Param("categoryId") Integer categoryId);

}
