package com.github.chenlijia1111.fightGroup.common.requestVo.fightGroupOrder;

import com.github.chenlijia1111.commonModule.common.checkFunction.PositiveNumberCheck;
import com.github.chenlijia1111.commonModule.common.requestVo.order.CouponWithGoodIds;
import com.github.chenlijia1111.fightGroup.entity.FightGroup;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.utils.core.enums.PropertyCheckType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 添加拼团订单参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/26 0026 下午 2:46
 **/
@ApiModel
@Setter
@Getter
public class FightGroupOrderAddParams {

    /**
     * 商品参与拼团记录id
     *
     * @since 下午 3:13 2019/11/25 0025
     **/
    @ApiModelProperty(value = "商品参与拼团记录id")
    @PropertyCheck(name = "商品参与拼团记录id")
    private String fightGroupProductId;

    /**
     * 拼团-团id
     * 表示当前是加入其它人创建好的拼团
     * {@link FightGroup#getId()}
     * 如果为空,则表示
     * @since 下午 3:08 2019/11/26 0026
     **/
    @ApiModelProperty(value = "拼团-团id")
    private String fightGroupId;

    /**
     * 商品id
     *
     * @since 下午 4:48 2019/11/5 0005
     **/
    @ApiModelProperty(value = "商品id")
    @PropertyCheck(name = "商品id")
    private String goodId;

    /**
     * 商品数量
     *
     * @since 下午 4:48 2019/11/5 0005
     **/
    @ApiModelProperty(value = "商品数量")
    @PropertyCheck(name = "商品数量", checkFunction = PositiveNumberCheck.class)
    private BigDecimal count;

    /**
     * 收货人姓名
     *
     * @since 下午 4:51 2019/11/5 0005
     **/
    @ApiModelProperty(value = "收货人姓名")
    @PropertyCheck(name = "收货人姓名")
    private String receiverName;

    /**
     * 收货人电话
     *
     * @since 下午 4:51 2019/11/5 0005
     **/
    @ApiModelProperty(value = "收货人电话")
    @PropertyCheck(name = "收货人电话", checkType = PropertyCheckType.MOBILE_PHONE)
    private String receiverTelephone;

    /**
     * 收货人地址-省份
     *
     * @since 下午 4:02 2019/11/12 0012
     **/
    @ApiModelProperty(value = "收货人地址省")
    @PropertyCheck(name = "收货人地址省")
    private String recProvince;

    /**
     * 收货人地址-市
     *
     * @since 下午 4:02 2019/11/12 0012
     **/
    @ApiModelProperty(value = "收货人地址市")
    @PropertyCheck(name = "收货人地址市")
    private String recCity;

    /**
     * 收货人地址区
     *
     * @since 下午 4:02 2019/11/12 0012
     **/
    @ApiModelProperty(value = "收货人地址区")
    @PropertyCheck(name = "收货人地址区")
    private String recArea;

    /**
     * 收货人地址
     */
    @ApiModelProperty("收货人详细地址")
    @PropertyCheck(name = "收货人详细地址")
    private String recAddr;

    /**
     * 备注
     *
     * @since 下午 4:52 2019/11/5 0005
     **/
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 优惠券id集合以及优惠券在当前订单中对应的作用的商品id
     *
     * @since 上午 11:22 2019/11/22 0022
     **/
    @ApiModelProperty(value = "优惠券id集合以及优惠券在当前订单中对应的作用的商品id")
    private List<CouponWithGoodIds> couponWithGoodIdsList;


}
