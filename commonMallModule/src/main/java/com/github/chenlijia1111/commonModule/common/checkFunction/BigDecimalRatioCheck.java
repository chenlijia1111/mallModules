package com.github.chenlijia1111.commonModule.common.checkFunction;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 比率校验
 * 0-1
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/8 0008 上午 11:56
 **/
public class BigDecimalRatioCheck implements Predicate<BigDecimal> {

    @Override
    public boolean test(BigDecimal b) {
        return Objects.nonNull(b) && b.compareTo(new BigDecimal(0)) >= 0 && b.compareTo(new BigDecimal(1)) <= 1;
    }
}
