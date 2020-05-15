package com.github.chenlijia1111.commonModule.common.responseVo.order;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 自动收货订单延时队列对象
 * @author Chen LiJia
 * @since 2020/5/15
 */
@Setter
@Getter
public class DelayNotReceiveOrder {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 签收时间
     */
    private Date signTime;


}
