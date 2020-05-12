package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.common.requestVo.evaluation.QueryParams;
import com.github.chenlijia1111.commonModule.common.responseVo.evaluate.EvaluateListVo;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.entity.Evaluation;

import java.util.List;

/**
 * 评价表
 * @author chenLiJia
 * @since 2019-11-25 13:45:34
 **/
public interface EvaluationServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 13:45:34
     **/
    Result add(Evaluation params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 13:45:34
     **/
    Result update(Evaluation params);

    /**
     * 根据条件统计数量
     * @since 下午 2:03 2019/11/25 0025
     * @param condition 1
     * @return java.lang.Integer
     **/
    Integer countByCondition(Evaluation condition);

    /**
     * 判断评价是否存在
     * @since 下午 2:14 2019/11/25 0025
     * @param id 评价id
     * @return com.github.chenlijia1111.commonModule.entity.Evaluation
     **/
    Evaluation findById(String id);

    /**
     * 根据条件查询列表
     * @param condition
     * @return
     */
    List<Evaluation> listByCondition(Evaluation condition);

    /**
     * 查询评价列表
     * @param params
     * @return
     */
    List<EvaluateListVo> listPage(QueryParams params);
}
