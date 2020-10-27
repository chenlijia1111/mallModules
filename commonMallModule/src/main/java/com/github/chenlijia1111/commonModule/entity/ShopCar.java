package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2020-02-19 17:37:03
 **/
@ApiModel("购物车")
@Table(name = "s_shop_car")
public class ShopCar {
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
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Column(name = "client_id")
    private String clientId;

    /**
     * 产品id
     */
    @ApiModelProperty("产品id")
    @PropertyCheck(name = "产品id")
    @Column(name = "product_id")
    private String productId;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    @PropertyCheck(name = "商品id")
    @Column(name = "good_id")
    private String goodId;

    /**
     * 产品数量
     * 2020-10-27 修改，支持小数，适配特殊情景，如买菜之类
     */
    @ApiModelProperty("产品数量")
    @PropertyCheck(name = "产品数量")
    @Column(name = "good_count")
    private BigDecimal goodCount;

    /**
     * 修改时间 以此字段倒序排序
     */
    @ApiModelProperty("修改时间 以此字段倒序排序")
    @PropertyCheck(name = "修改时间 以此字段倒序排序")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 产品json信息
     */
    @ApiModelProperty("产品json信息")
    @PropertyCheck(name = "产品json信息")
    @Column(name = "product_json")
    private String productJson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public BigDecimal getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(BigDecimal goodCount) {
        this.goodCount = goodCount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getProductJson() {
        return productJson;
    }

    public void setProductJson(String productJson) {
        this.productJson = productJson;
    }
}
