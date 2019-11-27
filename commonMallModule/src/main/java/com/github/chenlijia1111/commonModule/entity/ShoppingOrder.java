package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.commonModule.common.pojo.coupon.AbstractCoupon;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.utils.list.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * 订单
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-05 16:39:11
 **/
@ApiModel("订单")
@Table(name = "s_shopping_order")
@Setter
@Getter
@Accessors(chain = true)
public class ShoppingOrder {
    /**
     * 订单编号
     */
    @ApiModelProperty("订单编号")
    @PropertyCheck(name = "订单编号")
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
     * 商品id
     */
    @ApiModelProperty("商品id")
    @PropertyCheck(name = "商品id")
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 商品数量
     */
    @ApiModelProperty("商品数量")
    @PropertyCheck(name = "商品数量")
    @Column(name = "count")
    private Integer count;

    /**
     * 状态 0初始状态 983042成功 983041 失败或者取消
     */
    @ApiModelProperty("状态 0初始状态 983042成功 983041 失败或者取消")
    @PropertyCheck(name = "状态 0初始状态 983042成功 983041 失败或者取消")
    @Column(name = "state")
    private Integer state;

    /**
     * 商品金额
     */
    @ApiModelProperty("商品支付时单价")
    @PropertyCheck(name = "商品支付时单价")
    @Column(name = "good_price")
    private Double goodPrice;

    /**
     * 商品金额
     */
    @ApiModelProperty("商品金额")
    @PropertyCheck(name = "商品金额")
    @Column(name = "product_amount_total")
    private Double productAmountTotal;

    /**
     * 支付渠道
     */
    @ApiModelProperty("支付渠道")
    @PropertyCheck(name = "支付渠道")
    @Column(name = "pay_channel")
    private String payChannel;

    /**
     * 商户支付订单号
     */
    @ApiModelProperty("商户支付订单号")
    @PropertyCheck(name = "商户支付订单号")
    @Column(name = "pay_record")
    private String payRecord;

    /**
     * 第三方支付流水号 在回调成功之后生成
     */
    @ApiModelProperty("第三方支付流水号 在回调成功之后生成")
    @PropertyCheck(name = "第三方支付流水号 在回调成功之后生成")
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 订单金额
     */
    @ApiModelProperty("订单金额")
    @PropertyCheck(name = "订单金额")
    @Column(name = "order_amount_total")
    private Double orderAmountTotal;

    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    @PropertyCheck(name = "支付时间")
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 组订单id
     */
    @ApiModelProperty("组订单id")
    @PropertyCheck(name = "组订单id")
    @Column(name = "group_id")
    private String groupId;

    /**
     * 应付金额
     */
    @ApiModelProperty("应付金额")
    @PropertyCheck(name = "应付金额")
    @Column(name = "payable")
    private Double payable;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @PropertyCheck(name = "备注")
    @Column(name = "remarks")
    private String remarks;

    /**
     * 商家备注
     */
    @ApiModelProperty("商家备注")
    @PropertyCheck(name = "商家备注")
    @Column(name = "shop_remarks")
    private String shopRemarks;


    /**
     * 订单快照
     *
     * @see com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo
     */
    @ApiModelProperty("订单快照")
    @PropertyCheck(name = "订单快照")
    @Column(name = "details_json")
    private String detailsJson;

    /**
     * 使用的优惠券json数组
     * 订单结算优惠券结算的顺序  满减券->积分券->物流券
     *
     * @see com.github.chenlijia1111.commonModule.common.pojo.coupon.CountDiscountCoupon
     * @see com.github.chenlijia1111.commonModule.common.pojo.coupon.CountSubCoupon
     * @see com.github.chenlijia1111.commonModule.common.pojo.coupon.ExpressCoupon
     * @see com.github.chenlijia1111.commonModule.common.pojo.coupon.PriceDiscountCoupon
     * @see com.github.chenlijia1111.commonModule.common.pojo.coupon.PriceSubCoupon
     * @see com.github.chenlijia1111.commonModule.common.pojo.coupon.ScoreCoupon
     */
    @ApiModelProperty("使用的优惠券json数组")
    @PropertyCheck(name = "使用的优惠券json数组")
    @Column(name = "order_coupon")
    private String orderCoupon;

    /**
     * 订单使用的券
     *
     * @see #orderCoupon
     * @since 下午 1:43 2019/11/22 0022
     **/
    private List<AbstractCoupon> couponList;

    /**
     * 订单的发货单
     *
     * @since 上午 10:58 2019/11/22 0022
     **/
    private ImmediatePaymentOrder immediatePaymentOrder;

    /**
     * 订单对应的商品详情
     *
     * @since 上午 10:28 2019/11/26 0026
     **/
    private GoodVo goodsVO;

    public List<AbstractCoupon> getCouponList() {
        return couponList == null ? Lists.newArrayList() : couponList;
    }
}
