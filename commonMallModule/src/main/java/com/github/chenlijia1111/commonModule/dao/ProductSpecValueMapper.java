package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.ProductSpec;
import com.github.chenlijia1111.commonModule.entity.ProductSpecValue;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 产品规格值
 * @author chenLiJia
 * @since 2019-11-01 13:46:43
 * @version 1.0
 **/
public interface ProductSpecValueMapper extends Mapper<ProductSpecValue> {

    /**
     * 批量添加
     * @since 下午 2:55 2019/11/1 0001
     * @param list 1
     * @return java.lang.Integer
     **/
    Integer batchAdd(@Param("list") List<ProductSpecValue> list);

    /**
     * 根据规格id集合删除规格值
     * @since 下午 4:00 2019/11/1 0001
     * @param specIdSet 规格id {@link ProductSpec#getId()}
     * @return java.lang.Integer
     **/
    Integer deleteBySpecIdSet(@Param("specIdSet") Set<Integer> specIdSet);

    /**
     * 通过规格id集合查询规格值
     * @since 上午 9:50 2019/11/5 0005
     * @param specIdSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ProductSpecValue>
     **/
    List<ProductSpecValue> listBySpecIdSet(@Param("specIdSet") Set<Integer> specIdSet);

}
