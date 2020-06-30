package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.common.responseVo.evaluation.LabelCountVo;
import com.github.chenlijia1111.commonModule.entity.EvaluationLabel;
import com.github.chenlijia1111.utils.common.Result;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 评价标签
 * @author chenLiJia
 * @since 2019-11-25 13:48:21
 * @version 1.0
 **/
public interface EvaluationLabelMapper extends Mapper<EvaluationLabel> {

    /**
     * 批量添加评价标签
     * @since 下午 2:25 2019/11/25 0025
     * @param labelList 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    Integer batchAdd(@Param("labelList") List<EvaluationLabel> labelList);

    /**
     * 统计评价标签数量
     * @param productId
     * @return
     */
    List<LabelCountVo> listLabelCountVo(@Param("productId") String productId);

}
