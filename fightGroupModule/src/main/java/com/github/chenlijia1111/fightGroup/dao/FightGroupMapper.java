package com.github.chenlijia1111.fightGroup.dao;

import com.github.chenlijia1111.fightGroup.entity.FightGroup;
import tk.mybatis.mapper.common.Mapper;

/**
 * 拼团-团
 * @author chenLiJia
 * @since 2019-11-26 12:11:28
 * @version 1.0
 **/
public interface FightGroupMapper extends Mapper<FightGroup> {

    /**
     * 定时取消那些达到指定时间还没有成功拼团的团
     * @since 上午 9:38 2019/11/27 0027
     * @return 返回取消的团的数量
     **/
    Integer scheduleCancelFightGroup();
}
