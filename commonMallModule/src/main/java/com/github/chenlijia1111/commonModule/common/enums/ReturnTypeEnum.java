package com.github.chenlijia1111.commonModule.common.enums;

/**
 * 退货类型
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/25 0025 上午 10:18
 **/
public enum ReturnTypeEnum {

    RETURN_MONEY(1), //退款
    RETURN_GOOD(2), //退货
    RETURN_GOOD_AND_MONEY(3), //退货退款
    ;

    ReturnTypeEnum(Integer returnType) {
        this.returnType = returnType;
    }

    private Integer returnType;

    public Integer getReturnType() {
        return returnType;
    }
}
