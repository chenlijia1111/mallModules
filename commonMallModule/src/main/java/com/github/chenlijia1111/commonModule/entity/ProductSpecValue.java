package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 产品规格值
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-01 13:46:43
 **/
@ApiModel("产品规格值")
@Table(name = "s_product_spec_value")
@Setter
@Getter
@Accessors(chain = true)
public class ProductSpecValue {
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
     * 产品规格id
     */
    @ApiModelProperty("产品规格id")
    @PropertyCheck(name = "产品规格id")
    @Column(name = "product_spec_id")
    private Integer productSpecId;

    /**
     * 规格值-图片
     */
    @ApiModelProperty("规格值-图片")
    @PropertyCheck(name = "规格值-图片")
    @Column(name = "spec_value_image")
    private String specValueImage;

    /**
     * 规格值
     */
    @ApiModelProperty("规格值")
    @PropertyCheck(name = "规格值")
    @Column(name = "spec_value")
    private String specValue;


}
