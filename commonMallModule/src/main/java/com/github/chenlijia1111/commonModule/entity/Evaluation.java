package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import javax.persistence.*;

/**
 * 评价表
 * @author chenLiJia
 * @since 2019-11-25 13:54:57
 * @version 1.0
 **/
@ApiModel("评价表")
@Table(name = "s_evaluation")
@Setter
@Getter
@Accessors(chain = true)
public class Evaluation {
    /**
     * 评价id
     */
    @ApiModelProperty("评价id")
    @PropertyCheck(name = "评价id")
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Column(name = "client_id")
    private String clientId;

    /**
     * 商家id
     */
    @ApiModelProperty("商家id")
    @PropertyCheck(name = "商家id")
    @Column(name = "shop_id")
    private String shopId;

    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    @PropertyCheck(name = "订单id")
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    @PropertyCheck(name = "商品id")
    @Column(name = "good_id")
    private String goodId;

    /**
     * 产品id
     */
    @ApiModelProperty("产品id")
    @PropertyCheck(name = "产品id")
    @Column(name = "product_id")
    private String productId;

    /**
     * 评价内容
     */
    @ApiModelProperty("评价内容")
    @PropertyCheck(name = "评价内容")
    @Column(name = "comment")
    private String comment;

    /**
     * 图片内容
     */
    @ApiModelProperty("图片内容")
    @PropertyCheck(name = "图片内容")
    @Column(name = "images")
    private String images;

    /**
     * 商家评分
     */
    @ApiModelProperty("商家评分")
    @PropertyCheck(name = "商家评分")
    @Column(name = "shop_level")
    private Double shopLevel;

    /**
     * 服务评分
     */
    @ApiModelProperty("服务评分")
    @PropertyCheck(name = "服务评分")
    @Column(name = "service_level")
    private Double serviceLevel;

    /**
     * 物流评分
     */
    @ApiModelProperty("物流评分")
    @PropertyCheck(name = "物流评分")
    @Column(name = "express_level")
    private Double expressLevel;

    /**
     * 父评价(表示追评等其他含义)
     */
    @ApiModelProperty("父评价(表示追评等其他含义)")
    @PropertyCheck(name = "父评价(表示追评等其他含义)")
    @Column(name = "parent_evalua")
    private String parentEvalua;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否删除 0否 1是
     */
    @ApiModelProperty("是否删除 0否 1是")
    @PropertyCheck(name = "是否删除 0否 1是")
    @Column(name = "delete_status")
    private Integer deleteStatus;


}
