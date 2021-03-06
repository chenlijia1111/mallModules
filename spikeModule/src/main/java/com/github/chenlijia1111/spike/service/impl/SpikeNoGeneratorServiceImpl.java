package com.github.chenlijia1111.spike.service.impl;

import com.github.chenlijia1111.commonModule.service.IdGeneratorServiceI;
import com.github.chenlijia1111.commonModule.service.impl.ShoppingIdGeneratorServiceImpl;
import com.github.chenlijia1111.utils.core.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 秒杀订单编号生成策略
 * 约定
 * 已天为单位
 * 订单类型代号 1代表组订单 2代表订单编号 3代表发货单 4代表收货单 5代表退货单 6秒杀 7拼团
 * <p>
 * 6 + 年月日 + 6位流水
 *
 * @author Chen LiJia
 * @since 2019/12/30
 */
public class SpikeNoGeneratorServiceImpl implements IdGeneratorServiceI {

    //上次获取的天
    private static LocalDate lastDay = LocalDate.now();

    @Override
    public String createOrderNo() {

        //当前天
        LocalDate nowDate = LocalDate.now();
        //判断跟上次是否是同一天
        if (!Objects.equals(nowDate, lastDay)) {
            //重新初始化
            ShoppingIdGeneratorServiceImpl.currentNumber = new AtomicInteger(0);
        }

        lastDay = nowDate;
        String groupId = StringUtils.completeIntToFixedLengthStr("6" + nowDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")), 6, ShoppingIdGeneratorServiceImpl.currentNumber.addAndGet(1), '0');
        return groupId;
    }

}
