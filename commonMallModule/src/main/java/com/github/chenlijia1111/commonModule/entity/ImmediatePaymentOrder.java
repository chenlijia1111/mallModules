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
 * 发货单
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-05 16:39:11
 **/
@ApiModel("发货单")
@Table(name = "s_immediate_payment_order")
@Setter
@Getter
@Accessors(chain = true)
public class ImmediatePaymentOrder {
    /**
     * 发货单单号
     */
    @ApiModelProperty("发货单单号")
    @PropertyCheck(name = "发货单单号")
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
     * 收货人
     */
    @ApiModelProperty("收货人")
    @PropertyCheck(name = "收货人")
    @Column(name = "rec_user")
    private String recUser;

    /**
     * 发货人
     */
    @ApiModelProperty("发货人")
    @PropertyCheck(name = "发货人")
    @Column(name = "star_user")
    private String starUser;

    /**
     * 收货人电话
     */
    @ApiModelProperty("收货人电话")
    @PropertyCheck(name = "收货人电话")
    @Column(name = "rec_tel")
    private String recTel;

    /**
     * 发货人电话
     */
    @ApiModelProperty("发货人电话")
    @PropertyCheck(name = "发货人电话")
    @Column(name = "star_tel")
    private String starTel;



    /**
     * 发货人地址
     */
    @ApiModelProperty("发货人地址")
    @PropertyCheck(name = "发货人地址")
    @Column(name = "star_addr")
    private String starAddr;

    /**
     * 快递单号
     */
    @ApiModelProperty("快递单号")
    @PropertyCheck(name = "快递单号")
    @Column(name = "express_no")
    private String expressNo;

    /**
     * 快递公司
     */
    @ApiModelProperty("快递公司")
    @PropertyCheck(name = "快递公司")
    @Column(name = "express_com")
    private String expressCom;

    /**
     * 快递名称
     */
    @ApiModelProperty("快递名称")
    @PropertyCheck(name = "快递名称")
    @Column(name = "express_name")
    private String expressName;

    /**
     * 前一个订单
     */
    @ApiModelProperty("前一个订单")
    @PropertyCheck(name = "前一个订单")
    @Column(name = "front_order")
    private String frontOrder;

    /**
     * 购物单单号
     */
    @ApiModelProperty("购物单单号")
    @PropertyCheck(name = "购物单单号")
    @Column(name = "shopping_order")
    private String shoppingOrder;

    /**
     * 发货时间
     */
    @ApiModelProperty("发货时间")
    @PropertyCheck(name = "发货时间")
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 收货人地址-省份
     *
     * @since 下午 4:02 2019/11/12 0012
     **/
    @ApiModelProperty(value = "收货人地址省")
    @PropertyCheck(name = "收货人地址省")
    @Column(name = "rec_province")
    private String recProvince;

    /**
     * 收货人地址-市
     *
     * @since 下午 4:02 2019/11/12 0012
     **/
    @ApiModelProperty(value = "收货人地址市")
    @PropertyCheck(name = "收货人地址市")
    @Column(name = "rec_city")
    private String recCity;

    /**
     * 收货人地址区
     *
     * @since 下午 4:02 2019/11/12 0012
     **/
    @ApiModelProperty(value = "收货人地址区")
    @PropertyCheck(name = "收货人地址区")
    @Column(name = "rec_area")
    private String recArea;

    /**
     * 收货人地址
     */
    @ApiModelProperty("收货人详细地址")
    @PropertyCheck(name = "收货人详细地址")
    @Column(name = "rec_addr")
    private String recAddr;

    /**
     * 物流签收状态 0否1是
     */
    @ApiModelProperty("物流签收状态 0否1是")
    @PropertyCheck(name = "物流签收状态 0否1是")
    @Column(name = "express_sign_status")
    private Integer expressSignStatus;

    /**
     * 物流签收时间 通过物流api查询
     */
    @ApiModelProperty("物流签收时间 通过物流api查询")
    @PropertyCheck(name = "物流签收时间")
    @Column(name = "express_sign_time")
    private Integer expressSignTime;

    /**
     * 发货单对应的收货单
     *
     * @since 上午 10:59 2019/11/22 0022
     **/
    private ReceivingGoodsOrder receivingGoodsOrder;

}
