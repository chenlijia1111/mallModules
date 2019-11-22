package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 产品的规格选项
 * @author chenLiJia
 * @since 2019-11-01 13:46:43
 * @version 1.0
 **/
@ApiModel("产品的规格选项")
@Table(name = "s_product_spec")
@Setter
@Getter
@Accessors(chain = true)
public class ProductSpec {
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
     * 规格名称
     */
    @ApiModelProperty("规格名称")
    @PropertyCheck(name = "规格名称")
    @Column(name = "spec_name")
    private String specName;

}
