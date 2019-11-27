package com.github.chenlijia1111.fightGroup.service.impl;

import com.github.chenlijia1111.fightGroup.dao.FightGroupMapper;
import com.github.chenlijia1111.fightGroup.entity.FightGroup;
import com.github.chenlijia1111.fightGroup.service.FightGroupServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 拼团-团
 *
 * @author chenLiJia
 * @since 2019-11-26 12:09:02
 **/
@Service
public class FightGroupServiceImpl implements FightGroupServiceI {


    @Resource
    private FightGroupMapper fightGroupMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    public Result add(FightGroup params) {

        int i = fightGroupMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    public Result update(FightGroup params) {

        int i = fightGroupMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 主键查询拼团团信息
     *
     * @param id 1
     * @return com.github.chenlijia1111.fightGroup.entity.FightGroup
     * @since 下午 3:40 2019/11/26 0026
     **/
    @Override
    public FightGroup findById(String id) {
        if (StringUtils.isNotEmpty(id)) {
            return fightGroupMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    /**
     * 定时取消那些达到指定时间还没有成功拼团的团
     * @since 上午 9:38 2019/11/27 0027
     * @return 返回取消的团的数量
     **/
    @Override
    public Integer scheduleCancelFightGroup() {
        return fightGroupMapper.scheduleCancelFightGroup();
    }


}
