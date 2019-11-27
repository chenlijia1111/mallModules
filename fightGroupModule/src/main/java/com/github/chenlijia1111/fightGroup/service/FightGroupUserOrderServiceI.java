package com.github.chenlijia1111.fightGroup.service;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.fightGroup.entity.FightGroupUserOrder;

/**
 * 拼团订单记录
 * @author chenLiJia
 * @since 2019-11-26 12:09:02
 **/
public interface FightGroupUserOrderServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    Result add(FightGroupUserOrder params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    Result update(FightGroupUserOrder params);

    /**
     * 通过订单编号查询拼团订单记录
     * 因为订单编号是唯一的,所以可以直接用订单编号进行查询
     * @since 上午 10:01 2019/11/27 0027
     * @param orderNo 订单编号
     * @return com.github.chenlijia1111.fightGroup.entity.FightGroupUserOrder
     **/
    FightGroupUserOrder findByOrderNo(String orderNo);
}
