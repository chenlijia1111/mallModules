package com.github.chenlijia1111.commonModule.common.enums;

/**
 * 订单类型枚举
 * 如果有其他的订单类型,可以自定义枚举类,
 * 之所以要用枚举类来保存状态只是为了防止魔法变量
 * 即直接在业务代码里定义,这样会导致后期修改不方便
 *
 * @author Chen LiJia
 * @since 2020/1/2
 */
public enum OrderTypeEnum {


    ORDINARY_ORDER(1), //普通订单
    SPIKE_ORDER(2), //秒杀订单
    FIGHT_GROUP_ORDER(3), //拼团订单
    ;

    private Integer type;


    OrderTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
