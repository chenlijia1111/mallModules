package com.github.chenlijia1111.commonModule.common.responseVo.order;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单状态合集封装
 * 正常数据，一条订单肯定只有一个发货数据一个收货数据
 * 评价数据不一定有，所以用 left join
 *
 * @author Chen LiJia
 * @since 2020/9/22
 */
@Setter
@Getter
public class OrderStatusFieldVo {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商家组订单编号
     */
    private String shopGroupNo;

    /**
     * 组订单编号
     */
    private String groupNo;

    /**
     * 订单状态
     * 0 初始化 983042 订单成功 983041 订单取消
     * 有些系统可能并不需要支付，所有可能订单成功了，但是支付状态是否的
     * 比如积分抵扣的，就不需要支付
     * 比如有些记账订单的，也是不需要支付的
     * 具体逻辑具体处理，有需要特殊处理的可以通过
     * {@link com.github.chenlijia1111.commonModule.service.IFindOrderStateHook} 进行操作
     */
    private Integer orderState;

    /**
     * 支付状态
     * 0否1是
     */
    private Integer payStatus;

    /**
     * 完成状态
     * 0否1是
     */
    private Integer completeStatus;

    /**
     * 是否已发货
     * 0否1是
     */
    private Integer sendStatus;

    /**
     * 是否已收货
     * 0否1是
     */
    private Integer receiveStatus;

    /**
     * 是否已评价
     * 0否1是
     */
    private Integer evaluateStatus;

}
