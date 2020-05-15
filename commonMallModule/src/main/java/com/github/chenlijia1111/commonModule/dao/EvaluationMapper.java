package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.common.requestVo.evaluation.QueryParams;
import com.github.chenlijia1111.commonModule.common.responseVo.evaluate.EvaluateListVo;
import com.github.chenlijia1111.commonModule.entity.Evaluation;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 评价表
 * @author chenLiJia
 * @since 2019-11-25 13:54:57
 * @version 1.0
 **/
public interface EvaluationMapper extends Mapper<Evaluation> {


    /**
     * 根据订单编号集合查询评价列表
     * @param orderNoSet
     * @return
     */
    List<Evaluation> listByOrderNoSet(@Param("orderNoSet") Set<String> orderNoSet);


    /**
     * 查询评价列表
     * @param params
     * @return
     */
    List<EvaluateListVo> listPage(QueryParams params);

}