package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * 类别祖宗关系
 * @author chenLiJia
 * @since 2020-07-17 10:32:48
 * @version 1.0
 **/
@ApiModel("类别祖宗关系")
@Table(name = "s_category_ancestor")
@Setter
@Getter
@Accessors(chain = true)
public class CategoryAncestor {
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
     * 类别Id
     */
    @ApiModelProperty("类别Id")
    @PropertyCheck(name = "类别Id")
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 祖宗id
     */
    @ApiModelProperty("祖宗id")
    @PropertyCheck(name = "祖宗id")
    @Column(name = "ancestor_id")
    private Integer ancestorId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

}