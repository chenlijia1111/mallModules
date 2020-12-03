package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.ProductSnapshot;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 产品快照表
 * @author chenLiJia
 * @since 2020-12-03 09:12:10
 * @version 1.0
 **/
public interface ProductSnapshotMapper extends Mapper<ProductSnapshot> {

    /**
     * 查询最近的产品快照信息
     * @param productIdSet 产品id集合
     * @return
     */
    List<ProductSnapshot> listByProductIdSet(@Param("productIdSet") Set<String> productIdSet);

}
