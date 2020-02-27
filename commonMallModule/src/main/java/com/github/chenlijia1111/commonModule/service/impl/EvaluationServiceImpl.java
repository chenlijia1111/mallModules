package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.dao.EvaluationMapper;
import com.github.chenlijia1111.commonModule.entity.Evaluation;
import com.github.chenlijia1111.commonModule.service.EvaluationServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评价表
 *
 * @author chenLiJia
 * @since 2019-11-25 13:45:34
 **/
@Service
public class EvaluationServiceImpl implements EvaluationServiceI {


    @Resource
    private EvaluationMapper evaluationMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 13:45:34
     **/
    public Result add(Evaluation params) {

        int i = evaluationMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 13:45:34
     **/
    public Result update(Evaluation params) {

        int i = evaluationMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 根据条件统计数量
     *
     * @param condition 1
     * @return java.lang.Integer
     * @since 下午 2:03 2019/11/25 0025
     **/
    @Override
    public Integer countByCondition(Evaluation condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return evaluationMapper.selectCount(condition);
    }

    /**
     * 判断评价是否存在
     *
     * @param id 评价id
     * @return com.github.chenlijia1111.commonModule.entity.Evaluation
     * @since 下午 2:14 2019/11/25 0025
     **/
    @Override
    public Evaluation findById(String id) {
        if (StringUtils.isNotEmpty(id)) {
            return evaluationMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    /**
     * 根据条件查询列表
     * @param condition
     * @return
     */
    @Override
    public List<Evaluation> listByCondition(Evaluation condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return evaluationMapper.select(condition);
    }


}
