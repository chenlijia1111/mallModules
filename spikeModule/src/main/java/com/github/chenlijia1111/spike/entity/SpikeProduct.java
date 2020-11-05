package com.github.chenlijia1111.spike.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * 产品参与秒杀表
 * @author chenLiJia
 * @since 2019-11-25 15:06:02
 * @version 1.0
 **/
@ApiModel("产品参与秒杀表")
@Table(name = "s_spike_product")
@Setter
@Getter
@Accessors(chain = true)
public class SpikeProduct {
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
     * 商品id,如果为空,表示整个产品都参与了秒杀,否则表示只有这一个商品参与
     */
    @ApiModelProperty("商品id,如果为空,表示整个产品都参与了秒杀,否则表示只有这一个商品参与")
    @PropertyCheck(name = "商品id")
    @Column(name = "good_id")
    private String goodId;

    /**
     * 参与场次开始时间
     */
    @ApiModelProperty("参与场次开始时间")
    @PropertyCheck(name = "参与场次开始时间")
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 参与场次结束时间
     */
    @ApiModelProperty("参与场次结束时间")
    @PropertyCheck(name = "参与场次结束时间")
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 秒杀价格
     */
    @ApiModelProperty("秒杀价格")
    @PropertyCheck(name = "秒杀价格")
    @Column(name = "spike_price")
    private Double spikePrice;

    /**
     * 参与秒杀的库存数量
     */
    @ApiModelProperty("参与秒杀的库存数量")
    @PropertyCheck(name = "参与秒杀的库存数量")
    @Column(name = "total_stock_count")
    private BigDecimal totalStockCount;

    /**
     * 当前库存数量
     */
    @ApiModelProperty("当前库存数量")
    @PropertyCheck(name = "当前库存数量")
    @Column(name = "stock_count")
    private BigDecimal stockCount;

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
     * 减库存版本号,用于乐观锁
     */
    @ApiModelProperty("减库存版本号,用于乐观锁")
    @PropertyCheck(name = "减库存版本号,用于乐观锁")
    @Column(name = "update_version")
    private String updateVersion;


    /**
     * 排序值,用于秒杀列表排序
     */
    @ApiModelProperty("排序值,用于秒杀列表排序")
    @PropertyCheck(name = "排序值")
    @Column(name = "order_number")
    private String orderNumber;


    /**
     * 是否删除 0否 1是
     */
    @ApiModelProperty("是否删除 0否 1是")
    @PropertyCheck(name = "是否删除 0否 1是")
    @Column(name = "delete_status")
    private Integer deleteStatus;


}
