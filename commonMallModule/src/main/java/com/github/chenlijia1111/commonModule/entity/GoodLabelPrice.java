package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

/**
 * 商品标签价
 * @author chenLiJia
 * @since 2020-12-03 15:38:17
 * @version 1.0
 **/
@ApiModel("商品标签价")
@Table(name = "s_good_label_price")
@Setter
@Getter
public class GoodLabelPrice {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    @PropertyCheck(name = "商品id")
    @Column(name = "good_id")
    private String goodId;

    /**
     * 标签名称
     */
    @ApiModelProperty("标签名称")
    @PropertyCheck(name = "标签名称")
    @Column(name = "label_name")
    private String labelName;

    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    @PropertyCheck(name = "商品价格")
    @Column(name = "good_price")
    private Double goodPrice;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    public GoodLabelPrice() {
    }

    public GoodLabelPrice(String goodId, String labelName, Double goodPrice, Date createTime) {
        this.goodId = goodId;
        this.labelName = labelName;
        this.goodPrice = goodPrice;
        this.createTime = createTime;
    }
}
