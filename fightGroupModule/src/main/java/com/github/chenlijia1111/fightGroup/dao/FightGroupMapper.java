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
    FightGroup selectByPrimaryKey(String id);
}