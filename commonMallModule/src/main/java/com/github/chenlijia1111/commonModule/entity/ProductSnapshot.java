package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

/**
 * 产品快照表
 * @author chenLiJia
 * @since 2020-12-03 09:12:10
 * @version 1.0
 **/
@ApiModel("产品快照表")
@Table(name = "s_product_snapshot")
@Setter
@Getter
public class ProductSnapshot {
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
     * 产品id
     */
    @ApiModelProperty("产品id")
    @PropertyCheck(name = "产品id")
    @Column(name = "product_id")
    private String productId;

    /**
     * 产品类型
     * 1普通产品2拼团产品3秒杀产品
     * 其他类型可由调用者自定义
     * {@link com.github.chenlijia1111.commonModule.common.enums.ProductSnapshotTypeEnum}
     */
    @ApiModelProperty("产品类型")
    @PropertyCheck(name = "产品类型")
    @Column(name = "product_type")
    private Integer productType;

    /**
     * 产品详情
     */
    @ApiModelProperty("产品详情")
    @PropertyCheck(name = "产品详情")
    @Column(name = "product_json")
    private String productJson;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    public ProductSnapshot() {
    }

    public ProductSnapshot(String productId, Integer productType, String productJson, Date createTime) {
        this.productId = productId;
        this.productType = productType;
        this.productJson = productJson;
        this.createTime = createTime;
    }
}
