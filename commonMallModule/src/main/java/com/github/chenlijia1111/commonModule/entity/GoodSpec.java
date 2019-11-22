package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 商品规格
 * @author chenLiJia
 * @since 2019-11-01 13:46:43
 * @version 1.0
 **/
@ApiModel("商品规格")
@Table(name = "s_good_spec")
@Setter
@Getter
@Accessors(chain = true)
public class GoodSpec {
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
     * 规格值 关联s_product_spec_value
     */
    @ApiModelProperty("规格值 关联s_product_spec_value")
    @PropertyCheck(name = "规格值 关联s_product_spec_value")
    @Column(name = "spec_value_id")
    private Integer specValueId;


}
