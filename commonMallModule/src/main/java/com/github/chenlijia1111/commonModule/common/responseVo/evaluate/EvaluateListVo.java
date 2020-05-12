package com.github.chenlijia1111.commonModule.common.responseVo.evaluate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 评价列表对象
 * @author Chen LiJia
 * @since 2020/5/12
 */
@ApiModel
@Setter
@Getter
public class EvaluateListVo {

    /**
     * 评价id
     */
    @ApiModelProperty("评价id")
    private String id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String clientId;

    /**
     * 商家id
     */
    @ApiModelProperty("商家id")
    private String shopId;

    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    private String orderNo;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private String goodId;

    /**
     * 产品id
     */
    @ApiModelProperty("产品id")
    private String productId;

    /**
     * 评价内容
     */
    @ApiModelProperty("评价内容")
    private String comment;

    /**
     * 图片内容
     */
    @ApiModelProperty("图片内容")
    private String images;

    /**
     * 商品评分
     */
    @ApiModelProperty("商品评分")
    private Double productLevel;

    /**
     * 商家评分
     */
    @ApiModelProperty("商家评分")
    private Double shopLevel;

    /**
     * 服务评分
     */
    @ApiModelProperty("服务评分")
    private Double serviceLevel;

    /**
     * 物流评分
     */
    @ApiModelProperty("物流评分")
    private Double expressLevel;

    /**
     * 父评价(表示追评等其他含义)
     */
    @ApiModelProperty("父评价(表示追评等其他含义)")
    private String parentEvalua;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String userNickName;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String userHeadImage;

}
