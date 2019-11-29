package com.github.chenlijia1111.fightGroup.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import javax.persistence.*;

/**
 * 商品参加拼团活动
 * @author chenLiJia
 * @since 2019-11-26 12:11:28
 * @version 1.0
 **/
@ApiModel("商品参加拼团活动")
@Table(name = "s_fight_group_product")
@Setter
@Getter
@Accessors(chain = true)
public class FightGroupProduct {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 产品id
     */
    @ApiModelProperty("产品id")
    @PropertyCheck(name = "产品id")
    @Column(name = "product_id")
    private String productId;

    /**
     * 商品id,如果为空表示整个产品都加入了拼团
     */
    @ApiModelProperty("商品id,如果为空表示整个产品都加入了拼团")
    @PropertyCheck(name = "商品id,如果为空表示整个产品都加入了拼团")
    @Column(name = "good_id")
    private String goodId;

    /**
     * 拼团开始时间
     */
    @ApiModelProperty("拼团开始时间")
    @PropertyCheck(name = "拼团开始时间")
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 拼团结束时间
     */
    @ApiModelProperty("拼团结束时间")
    @PropertyCheck(name = "拼团结束时间")
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 拼团价格
     */
    @ApiModelProperty("拼团价格")
    @PropertyCheck(name = "拼团价格")
    @Column(name = "fight_price")
    private Double fightPrice;

    /**
     * 拼团商品参与总库存
     */
    @ApiModelProperty("拼团商品参与总库存")
    @PropertyCheck(name = "拼团商品参与总库存")
    @Column(name = "total_stock_count")
    private Integer totalStockCount;

    /**
     * 拼团商品当前库存
     */
    @ApiModelProperty("拼团商品当前库存")
    @PropertyCheck(name = "拼团商品当前库存")
    @Column(name = "stock_count")
    private Integer stockCount;

    /**
     * 成团人数
     */
    @ApiModelProperty("成团人数")
    @PropertyCheck(name = "成团人数")
    @Column(name = "group_person_count")
    private Integer groupPersonCount;

    /**
     * 每人限购数量
     */
    @ApiModelProperty("每人限购数量")
    @PropertyCheck(name = "每人限购数量")
    @Column(name = "person_limit_count")
    private Integer personLimitCount;

    /**
     * 最大拼团时间(分钟),超过自动解散
     */
    @ApiModelProperty("最大拼团时间(秒),超过自动解散")
    @PropertyCheck(name = "最大拼团时间(秒),超过自动解散")
    @Column(name = "max_fight_time")
    private Integer maxFightTime;

    /**
     * 排序值
     */
    @ApiModelProperty("排序值")
    @PropertyCheck(name = "排序值")
    @Column(name = "sort_number")
    private Integer sortNumber;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 编辑时间
     */
    @ApiModelProperty("编辑时间")
    @PropertyCheck(name = "编辑时间")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新库存版本号,用于乐观锁
     */
    @ApiModelProperty("更新库存版本号,用于乐观锁")
    @PropertyCheck(name = "更新库存版本号,用于乐观锁")
    @Column(name = "update_version")
    private String updateVersion;

    /**
     * 是否删除 0否 1是
     */
    @ApiModelProperty("是否删除 0否 1是")
    @PropertyCheck(name = "是否删除 0否 1是")
    @Column(name = "delete_status")
    private Integer deleteStatus;


}
