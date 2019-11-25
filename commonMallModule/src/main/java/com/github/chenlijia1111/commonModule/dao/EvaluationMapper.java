package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.Evaluation;
import tk.mybatis.mapper.common.Mapper;

/**
 * 评价表
 * @author chenLiJia
 * @since 2019-11-25 13:54:57
 * @version 1.0
 **/
public interface EvaluationMapper extends Mapper<Evaluation> {
    Evaluation selectByPrimaryKey(String id);
}