package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 商家组订单
 * 用于聚合订单，加快查询订单时间
 * 由于商家组订单下有很多子订单，所以发货，收货，评价等状态都不是一起的
 * 所以需要由客户端自己控制，这里只是加了一个表，方便客户端进行操作而已
 * 在添加完订单之后会自动构建这个表的数据，后面的都不会在进行处理
 * 由调用者自己处理相对应的逻辑
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2021-01-20 09:37:18
 **/
@ApiModel("商家组订单")
@Table(name = "s_shop_group_order")
@Setter
@Getter
public class ShopGroupOrder {

    /**
     * 商家组订单编号
     */
    @ApiModelProperty("商家组订单编号")
    @PropertyCheck(name = "商家组订单编号")
    @Id
    @Column(name = "shop_group_id")
    private String shopGroupId;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Column(name = "custom")
    private String custom;

    /**
     * 商家id
     */
    @ApiModelProperty("商家id")
    @PropertyCheck(name = "商家id")
    @Column(name = "shops")
    private String shops;

    /**
     * 是否支付
     */
    @ApiModelProperty("是否支付")
    @PropertyCheck(name = "是否支付")
    @Column(name = "pay_status")
    private Integer payStatus;

    /**
     * 是否发货
     */
    @ApiModelProperty("是否发货")
    @PropertyCheck(name = "是否发货")
    @Column(name = "send_status")
    private Integer sendStatus;

    /**
     * 是否收货
     */
    @ApiModelProperty("是否收货")
    @PropertyCheck(name = "是否收货")
    @Column(name = "receive_status")
    private Integer receiveStatus;

    /**
     * 是否评价
     */
    @ApiModelProperty("是否评价")
    @PropertyCheck(name = "是否评价")
    @Column(name = "evaluate_status")
    private Integer evaluateStatus;

    /**
     * 是否完成
     */
    @ApiModelProperty("是否完成")
    @PropertyCheck(name = "是否完成")
    @Column(name = "complete_status")
    private Integer completeStatus;

    /**
     * 是否取消
     */
    @ApiModelProperty("是否取消")
    @PropertyCheck(name = "是否取消")
    @Column(name = "cancel_status")
    private Integer cancelStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否删除
     */
    @ApiModelProperty("是否删除")
    @PropertyCheck(name = "是否删除")
    @Column(name = "delete_status")
    private Integer deleteStatus;

    /**
     * 扩展列1
     */
    @ApiModelProperty("扩展列1")
    @PropertyCheck(name = "扩展列1")
    @Column(name = "extend_column1")
    private Integer extendColumn1;

    /**
     * 扩展列2
     */
    @ApiModelProperty("扩展列2")
    @PropertyCheck(name = "扩展列2")
    @Column(name = "extend_column2")
    private Integer extendColumn2;

    /**
     * 扩展列3
     */
    @ApiModelProperty("扩展列3")
    @PropertyCheck(name = "扩展列3")
    @Column(name = "extend_column3")
    private Integer extendColumn3;

    /**
     * 扩展列4
     */
    @ApiModelProperty("扩展列4")
    @PropertyCheck(name = "扩展列4")
    @Column(name = "extend_column4")
    private Integer extendColumn4;

    /**
     * 扩展列5
     */
    @ApiModelProperty("扩展列5")
    @PropertyCheck(name = "扩展列5")
    @Column(name = "extend_column5")
    private Integer extendColumn5;

    /**
     * 构建初始对象
     *
     * @param shopGroupId 商家组订单id
     * @param custom      客户端id
     * @param shops       商家id
     * @param createTime  创建时间
     * @return
     */
    public static ShopGroupOrder generateInitShopGroupOrderVo(String shopGroupId, String custom, String shops, Date createTime) {
        ShopGroupOrder shopGroupOrder = new ShopGroupOrder();
        shopGroupOrder.setShopGroupId(shopGroupId);
        shopGroupOrder.setCustom(custom);
        shopGroupOrder.setShops(shops);
        shopGroupOrder.setPayStatus(BooleanConstant.NO_INTEGER);
        shopGroupOrder.setSendStatus(BooleanConstant.NO_INTEGER);
        shopGroupOrder.setReceiveStatus(BooleanConstant.NO_INTEGER);
        shopGroupOrder.setEvaluateStatus(BooleanConstant.NO_INTEGER);
        shopGroupOrder.setCompleteStatus(BooleanConstant.NO_INTEGER);
        shopGroupOrder.setCancelStatus(BooleanConstant.NO_INTEGER);
        shopGroupOrder.setCreateTime(createTime);
        shopGroupOrder.setDeleteStatus(BooleanConstant.NO_INTEGER);
        return shopGroupOrder;
    }

}
