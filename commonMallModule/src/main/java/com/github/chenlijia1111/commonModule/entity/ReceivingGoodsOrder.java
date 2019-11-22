package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 收货单
 * @author chenLiJia
 * @since 2019-11-05 16:39:11
 * @version 1.0
 **/
@ApiModel("收货单")
@Table(name = "s_receiving_goods_order")
@Setter
@Getter
@Accessors(chain = true)
public class ReceivingGoodsOrder {
    /**
     * 收货单单号
     */
    @ApiModelProperty("收货单单号")
    @PropertyCheck(name = "收货单单号")
    @Id
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 客户标识
     */
    @ApiModelProperty("客户标识")
    @PropertyCheck(name = "客户标识")
    @Column(name = "custom")
    private String custom;

    /**
     * 商家标识
     */
    @ApiModelProperty("商家标识")
    @PropertyCheck(name = "商家标识")
    @Column(name = "shops")
    private String shops;

    /**
     * 状态 0初始状态 983042成功 983041 失败或者取消
     */
    @ApiModelProperty("状态 0初始状态 983042成功 983041 失败或者取消")
    @PropertyCheck(name = "状态 0初始状态 983042成功 983041 失败或者取消")
    @Column(name = "state")
    private Integer state;

    /**
     * 订单编号
     */
    @ApiModelProperty("订单编号")
    @PropertyCheck(name = "订单编号")
    @Column(name = "shopping_order")
    private String shoppingOrder;

    /**
     * 发货单编号
     */
    @ApiModelProperty("发货单编号")
    @PropertyCheck(name = "发货单编号")
    @Column(name = "immediate_payment_order")
    private String immediatePaymentOrder;

    /**
     * 收货时间
     */
    @ApiModelProperty("收货时间")
    @PropertyCheck(name = "收货时间")
    @Column(name = "receive_time")
    private Date receiveTime;

    /**
     * 收货人
     */
    @ApiModelProperty("收货人")
    @PropertyCheck(name = "收货人")
    @Column(name = "rec_user")
    private String recUser;

    /**
     * 前一个订单
     */
    @ApiModelProperty("前一个订单")
    @PropertyCheck(name = "前一个订单")
    @Column(name = "front_order")
    private String frontOrder;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;


}
