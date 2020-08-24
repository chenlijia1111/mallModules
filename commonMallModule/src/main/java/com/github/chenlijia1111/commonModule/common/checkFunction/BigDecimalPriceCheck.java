package com.github.chenlijia1111.commonModule.common.checkFunction;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 价格校验
 * 必须大于0
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 2:05
 **/
public class BigDecimalPriceCheck implements Predicate<BigDecimal> {


    @Override
    public boolean test(BigDecimal b) {
        return Objects.nonNull(b) && b.compareTo(new BigDecimal(0)) >= 0;
    }
}
