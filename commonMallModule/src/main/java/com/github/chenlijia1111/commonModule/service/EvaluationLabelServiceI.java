package com.github.chenlijia1111.commonModule.service;

import com.github.chenlijia1111.commonModule.common.responseVo.evaluation.LabelCountVo;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.entity.EvaluationLabel;

import java.util.List;

/**
 * 评价标签
 * @author chenLiJia
 * @since 2019-11-25 13:48:31
 **/
public interface EvaluationLabelServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 13:48:31
     **/
    Result add(EvaluationLabel params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 13:48:31
     **/
    Result update(EvaluationLabel params);

    /**
     * 批量添加评价标签
     * @since 下午 2:25 2019/11/25 0025
     * @param labelList 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    Result batchAdd(List<EvaluationLabel> labelList);

    /**
     * 统计评价标签数量
     * @param productId
     * @return
     */
    List<LabelCountVo> listLabelCountVo(String productId);

}
