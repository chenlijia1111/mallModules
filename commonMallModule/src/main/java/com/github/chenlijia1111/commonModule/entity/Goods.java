package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.commonModule.common.checkFunction.PriceCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * 商品表
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-01 13:46:43
 **/
@ApiModel("商品表")
@Table(name = "s_goods")
@Setter
@Getter
@Accessors(chain = true)
public class Goods {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 产品ID
     */
    @ApiModelProperty("产品ID")
    @PropertyCheck(name = "产品ID")
    @Column(name = "product_id")
    private String productId;

    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    @PropertyCheck(name = "商品编号")
    @Column(name = "good_no")
    private String goodNo;

    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    @PropertyCheck(name = "库存数量")
    @Column(name = "stock_count")
    private Integer stockCount;

    /**
     * 售价
     */
    @ApiModelProperty("售价")
    @PropertyCheck(name = "售价", checkFunction = PriceCheck.class)
    @Column(name = "price")
    private Double price;

    /**
     * 会员价
     */
    @ApiModelProperty("会员价")
    @PropertyCheck(name = "会员价", checkFunction = PriceCheck.class)
    @Column(name = "vip_price")
    private Double vipPrice;

    /**
     * 市场价
     */
    @ApiModelProperty("市场价")
    @PropertyCheck(name = "市场价", checkFunction = PriceCheck.class)
    @Column(name = "market_price")
    private Double marketPrice;

    /**
     * 商品图片
     */
    @ApiModelProperty("商品图片")
    @PropertyCheck(name = "商品图片")
    @Column(name = "good_image")
    private String goodImage;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 变更时间
     */
    @ApiModelProperty("变更时间")
    @PropertyCheck(name = "变更时间")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除 0未删除 1已删除
     */
    @ApiModelProperty("是否删除 0未删除 1已删除")
    @PropertyCheck(name = "是否删除 0未删除 1已删除")
    @Column(name = "delete_status")
    private Integer deleteStatus;

    /**
     * 规格名称
     * 用于在创建商品的时候就直接生成规格名称，查询的时候就可以直接使用
     * 避免另外查询
     */
    @ApiModelProperty("规格名称")
    @PropertyCheck(name = "规格名称")
    @Column(name = "default_sku_name")
    private String defaultSkuName;


    public Goods setStockCount(Integer stockCount) {
        this.stockCount = (Objects.isNull(stockCount) || stockCount < 0) ? 0 : stockCount;
        return this;
    }
}
