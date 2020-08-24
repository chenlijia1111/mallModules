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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 退货单
 * @author chenLiJia
 * @since 2019-11-05 16:39:11
 * @version 1.0
 **/
@ApiModel("退货单")
@Table(name = "s_return_goods_order")
@Setter
@Getter
@Accessors(chain = true)
public class ReturnGoodsOrder {
    /**
     * 退货单单号
     */
    @ApiModelProperty("退货单单号")
    @PropertyCheck(name = "退货单单号")
    @Id
    @Column(name = "re_order_no")
    private String reOrderNo;

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
     * 退货时间
     */
    @ApiModelProperty("退货时间")
    @PropertyCheck(name = "退货时间")
    @Column(name = "re_create_time")
    private Date reCreateTime;

    /**
     * 退货原因
     */
    @ApiModelProperty("退货原因")
    @PropertyCheck(name = "退货原因")
    @Column(name = "re_explain")
    private String reExplain;

    /**
     * 退款凭证图片
     */
    @ApiModelProperty("退款凭证图片")
    @PropertyCheck(name = "退款凭证图片")
    @Column(name = "re_images")
    private String reImages;

    /**
     * 退货金额
     */
    @ApiModelProperty("退货金额")
    @PropertyCheck(name = "退货金额")
    @Column(name = "re_fund")
    private BigDecimal reFund;

    /**
     * 商家处理状态 0初始状态 983042成功 983041 失败或者取消
     */
    @ApiModelProperty("商家处理状态 0初始状态 983042成功 983041 失败或者取消")
    @PropertyCheck(name = "商家处理状态 0初始状态 983042成功 983041 失败或者取消")
    @Column(name = "check_status")
    private Integer checkStatus;

    /**
     * 处理时间
     */
    @ApiModelProperty("处理时间")
    @PropertyCheck(name = "处理时间")
    @Column(name = "re_handle_time")
    private Date reHandleTime;

    /**
     * 处理结果,原因
     */
    @ApiModelProperty("处理结果,原因")
    @PropertyCheck(name = "处理结果,原因")
    @Column(name = "re_reason")
    private String reReason;

    /**
     * 订单编号
     */
    @ApiModelProperty("订单编号")
    @PropertyCheck(name = "订单编号")
    @Column(name = "shopping_order")
    private String shoppingOrder;

    /**
     * 前一个订单
     */
    @ApiModelProperty("前一个订单")
    @PropertyCheck(name = "前一个订单")
    @Column(name = "front_order")
    private String frontOrder;

    /**
     * 退款状态 0初始状态 983042成功 983041 失败或者取消
     */
    @ApiModelProperty("退款状态 0初始状态 983042成功 983041 失败或者取消")
    @PropertyCheck(name = "退款状态 0初始状态 983042成功 983041 失败或者取消")
    @Column(name = "re_fund_status")
    private Integer reFundStatus;

    /**
     * 退货状态 0初始状态 983042成功 983041 失败或者取消
     */
    @ApiModelProperty("退货状态 0初始状态 983042成功 983041 失败或者取消")
    @PropertyCheck(name = "退货状态 0初始状态 983042成功 983041 失败或者取消")
    @Column(name = "state")
    private Integer state;

    /**
     * 退款单号
     */
    @ApiModelProperty("退款单号")
    @PropertyCheck(name = "退款单号")
    @Column(name = "return_pay_no")
    private String returnPayNo;

    /**
     * 退货类型 1退款 2退货 3退款退货
     */
    @ApiModelProperty("退货类型 1退款 2退货 3退款退货")
    @PropertyCheck(name = "退货类型 1退款 2退货 3退款退货")
    @Column(name = "re_type")
    private Integer reType;

    /**
     * 退款时间
     */
    @ApiModelProperty("退款时间")
    @PropertyCheck(name = "退款时间")
    @Column(name = "return_pay_time")
    private Date returnPayTime;


    /**
     * 是否删除 0否1是
     */
    @ApiModelProperty("是否删除 0否1是")
    @PropertyCheck(name = "是否删除 0否1是")
    @Column(name = "delete_status")
    private Integer deleteStatus;

}
