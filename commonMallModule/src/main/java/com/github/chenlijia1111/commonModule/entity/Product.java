package com.github.chenlijia1111.commonModule.entity;

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

/**
 * 产品表
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-01 13:46:43
 **/
@ApiModel("产品表")
@Table(name = "s_product")
@Setter
@Getter
@Accessors(chain = true)
public class Product {
    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    @PropertyCheck(name = "商品id")
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @PropertyCheck(name = "名称")
    @Column(name = "name")
    private String name;

    /**
     * 商家
     */
    @ApiModelProperty("商家")
    @PropertyCheck(name = "商家")
    @Column(name = "shops")
    private String shops;

    /**
     * 产品编号
     */
    @ApiModelProperty("产品编号")
    @PropertyCheck(name = "产品编号")
    @Column(name = "product_no")
    private String productNo;

    /**
     * 品牌
     */
    @ApiModelProperty("品牌")
    @PropertyCheck(name = "品牌")
    @Column(name = "brand")
    private String brand;

    /**
     * 内容地址
     */
    @ApiModelProperty("内容地址")
    @PropertyCheck(name = "内容地址")
    @Column(name = "content_url")
    private String contentUrl;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @PropertyCheck(name = "描述")
    @Column(name = "description")
    private String description;

    /**
     * 小图
     */
    @ApiModelProperty("小图")
    @PropertyCheck(name = "小图")
    @Column(name = "small_pic")
    private String smallPic;

    /**
     * 类别Id
     */
    @ApiModelProperty("类别Id")
    @PropertyCheck(name = "类别Id")
    @Column(name = "category_id")
    private String categoryId;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    @PropertyCheck(name = "标题")
    @Column(name = "title")
    private String title;

    /**
     * 视频
     */
    @ApiModelProperty("视频")
    @PropertyCheck(name = "视频")
    @Column(name = "video")
    private String video;

    /**
     * 单位
     */
    @ApiModelProperty("单位")
    @PropertyCheck(name = "单位")
    @Column(name = "unit")
    private String unit;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @PropertyCheck(name = "更新时间")
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
     * 是否上架 0未上架 1上架
     */
    @ApiModelProperty("是否上架 0未上架 1上架")
    @PropertyCheck(name = "是否上架 0未上架 1上架")
    @Column(name = "shelf_status")
    private Integer shelfStatus;

    /**
     * 内容 富文本内容
     */
    @ApiModelProperty("内容 富文本内容")
    @PropertyCheck(name = "内容 富文本内容")
    @Column(name = "content")
    private String content;

    /**
     * 排序值
     * 越小越靠前
     *
     * @since 上午 9:23 2019/11/4 0004
     **/
    @ApiModelProperty(value = "排序值")
    @PropertyCheck(name = "排序值")
    @Column(name = "sort_number")
    private Integer sortNumber;
}
