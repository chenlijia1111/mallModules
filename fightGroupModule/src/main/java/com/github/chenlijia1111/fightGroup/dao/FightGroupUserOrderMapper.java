package com.github.chenlijia1111.fightGroup.dao;

import com.github.chenlijia1111.fightGroup.entity.FightGroupUserOrder;
import tk.mybatis.mapper.common.Mapper;

/**
 * 拼团订单记录
 * @author chenLiJia
 * @since 2019-11-26 12:11:28
 * @version 1.0
 **/
public interface FightGroupUserOrderMapper extends Mapper<FightGroupUserOrder> {
    FightGroupUserOrder selectByPrimaryKey(String id);
}