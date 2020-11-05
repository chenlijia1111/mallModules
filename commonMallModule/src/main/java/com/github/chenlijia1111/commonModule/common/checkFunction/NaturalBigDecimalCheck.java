package com.github.chenlijia1111.commonModule.common.checkFunction;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 正数校验
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 5:03
 **/
public class NaturalBigDecimalCheck implements Predicate<BigDecimal> {


    @Override
    public boolean test(BigDecimal bigDecimal) {
        return Objects.nonNull(bigDecimal) && bigDecimal.compareTo(new BigDecimal("0.0")) >= 0;
    }
}
