package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.dao.EvaluationLabelMapper;
import com.github.chenlijia1111.commonModule.entity.EvaluationLabel;
import com.github.chenlijia1111.commonModule.service.EvaluationLabelServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评价标签
 *
 * @author chenLiJia
 * @since 2019-11-25 13:48:31
 **/
@Service
public class EvaluationLabelServiceImpl implements EvaluationLabelServiceI {


    @Resource
    private EvaluationLabelMapper evaluationLabelMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 13:48:31
     **/
    public Result add(EvaluationLabel params) {

        int i = evaluationLabelMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-25 13:48:31
     **/
    public Result update(EvaluationLabel params) {

        int i = evaluationLabelMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


    /**
     * 批量添加评价标签
     *
     * @param labelList 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:25 2019/11/25 0025
     **/
    @Override
    public Result batchAdd(List<EvaluationLabel> labelList) {
        if (Lists.isNotEmpty(labelList)) {
            evaluationLabelMapper.batchAdd(labelList);
        }
        return Result.success("操作成功");
    }


}
