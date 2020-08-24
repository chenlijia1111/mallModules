package com.github.chenlijia1111.fightGroup.common.response.product;

import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 拼团商品快照
 *
 * @author Chen LiJia
 * @since 2020/1/2
 */
@Setter
@Getter
public class FightGroupAdminProductVo extends AdminProductVo {

    /**
     * 拼团开始时间
     */
    @ApiModelProperty("拼团开始时间")
    @PropertyCheck(name = "拼团开始时间")
    private Date fightStartTime;

    /**
     * 拼团结束时间
     */
    @ApiModelProperty("拼团结束时间")
    @PropertyCheck(name = "拼团结束时间")
    private Date fightEndTime;

    /**
     * 拼团价格
     */
    @ApiModelProperty("拼团价格")
    @PropertyCheck(name = "拼团价格")
    private BigDecimal fightPrice;

    /**
     * 成团人数
     */
    @ApiModelProperty("成团人数")
    @PropertyCheck(name = "成团人数")
    private Integer groupPersonCount;

    /**
     * 每人限购数量
     */
    @ApiModelProperty("每人限购数量")
    @PropertyCheck(name = "每人限购数量")
    private Integer fightGroupPersonLimitCount;

    /**
     * 最大拼团时间(分钟),超过自动解散
     */
    @ApiModelProperty("最大拼团时间(秒),超过自动解散")
    @PropertyCheck(name = "最大拼团时间(秒),超过自动解散")
    private Integer maxFightTime;

}
