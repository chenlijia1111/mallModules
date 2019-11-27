package com.github.chenlijia1111.fightGroup.service;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.fightGroup.entity.FightGroup;

/**
 * 拼团-团
 * @author chenLiJia
 * @since 2019-11-26 12:09:02
 **/
public interface FightGroupServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    Result add(FightGroup params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    Result update(FightGroup params);

    /**
     * 主键查询拼团团信息
     * @since 下午 3:40 2019/11/26 0026
     * @param id 1
     * @return com.github.chenlijia1111.fightGroup.entity.FightGroup
     **/
    FightGroup findById(String id);


}
