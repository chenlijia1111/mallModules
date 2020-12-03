package com.github.chenlijia1111.commonModule.common.enums;

/**
 * 产品快照类型
 * @author Chen LiJia
 * @since 2020/12/3
 */
public enum  ProductSnapshotTypeEnum {

    COMMON(1), // 普通产品
    FIGHT_GROUP(2), // 拼团产品
    SPIKE(3), // 秒杀产品
    ;

    Integer type;

    ProductSnapshotTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
