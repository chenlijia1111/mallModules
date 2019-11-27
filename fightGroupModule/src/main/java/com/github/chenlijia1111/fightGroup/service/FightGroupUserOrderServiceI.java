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


}
