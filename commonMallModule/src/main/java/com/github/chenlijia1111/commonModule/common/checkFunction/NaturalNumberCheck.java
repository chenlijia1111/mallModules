package com.github.chenlijia1111.commonModule.common.checkFunction;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * 自然数校验
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 5:03
 **/
public class NaturalNumberCheck implements Predicate<Integer> {


    @Override
    public boolean test(Integer integer) {
        return Objects.nonNull(integer) && integer >= 0;
    }
}
