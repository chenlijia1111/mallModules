package com.github.chenlijia1111.fightGroup.common.requestVo.fightGroupProduct;

import com.github.chenlijia1111.commonModule.common.checkFunction.PositiveNumberCheck;
import com.github.chenlijia1111.commonModule.common.checkFunction.PriceCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 商品参与拼团参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/26 0026 下午 2:02
 **/
@Setter
@Getter
@ApiModel
public class FightGroupProductAddParams {

    /**
     * 产品id
     */
    @ApiModelProperty("产品id")
    @PropertyCheck(name = "产品id")
    private String productId;

    /**
     * 商品id,如果为空表示整个产品都加入了拼团
     */
    @ApiModelProperty("商品id,如果为空表示整个产品都加入了拼团")
    private String goodId;

    /**
     * 拼团开始时间
     */
    @ApiModelProperty("拼团开始时间")
    @PropertyCheck(name = "拼团开始时间")
    private Date startTime;

    /**
     * 拼团结束时间
     */
    @ApiModelProperty("拼团结束时间")
    @PropertyCheck(name = "拼团结束时间")
    private Date endTime;

    /**
     * 拼团价格
     */
    @ApiModelProperty("拼团价格")
    @PropertyCheck(name = "拼团价格", checkFunction = PriceCheck.class)
    private Double fightPrice;

    /**
     * 拼团商品参与总库存
     */
    @ApiModelProperty("拼团商品参与总库存")
    @PropertyCheck(name = "拼团商品参与总库存", checkFunction = PositiveNumberCheck.class)
    private Integer totalStockCount;

    /**
     * 成团人数
     */
    @ApiModelProperty("成团人数")
    @PropertyCheck(name = "成团人数", checkFunction = PositiveNumberCheck.class)
    private Integer groupPersonCount;

    /**
     * 每人限购数量
     */
    @ApiModelProperty("每人限购数量")
    private Integer personLimitCount;

    /**
     * 最大拼团时间(分钟),超过自动解散
     */
    @ApiModelProperty("最大拼团时间(分钟),超过自动解散")
    @PropertyCheck(name = "最大拼团时间(分钟),超过自动解散", checkFunction = PositiveNumberCheck.class)
    private Integer maxFightTime;

    /**
     * 排序值
     */
    @ApiModelProperty("排序值")
    private Integer sortNumber;

}
