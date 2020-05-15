package com.github.chenlijia1111.commonModule.common.responseVo.order;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 自动评价订单延时队列对象
 * @author Chen LiJia
 * @since 2020/5/15
 */
@Setter
@Getter
public class DelayNotEvaluateOrder {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 收货时间
     */
    private Date receiveTime;


}
