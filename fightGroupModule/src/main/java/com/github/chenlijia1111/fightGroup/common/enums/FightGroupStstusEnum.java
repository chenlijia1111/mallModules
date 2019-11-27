package com.github.chenlijia1111.fightGroup.common.enums;

/**
 * 拼团-团状态
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/26 0026 下午 3:49
 **/
public enum FightGroupStstusEnum {

    FIGHTTING(0), //拼团中
    FIGHT_SUCCESS(1), //拼团成功
    FIGHT_FAUILE(2), //拼团失败
    ;


    FightGroupStstusEnum(Integer status) {
        this.status = status;
    }

    private Integer status;

    public Integer getStatus() {
        return status;
    }
}
