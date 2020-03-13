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
 * 商品分类
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2020-03-12 15:35:19
 **/
@ApiModel("商品分类")
@Table(name = "s_category")
@Setter
@Getter
@Accessors(chain = true)
public class Category {
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
     * 类别名称
     */
    @ApiModelProperty("类别名称")
    @PropertyCheck(name = "类别名称")
    @Column(name = "category_name")
    private String categoryName;

    /**
     * 类别图标
     */
    @ApiModelProperty("类别图标")
    @PropertyCheck(name = "类别图标")
    @Column(name = "category_image")
    private String categoryImage;

    /**
     * 排序值
     */
    @ApiModelProperty("排序值")
    @PropertyCheck(name = "排序值")
    @Column(name = "sort_num")
    private Integer sortNum;

    /**
     * 启用状态 0否1是
     */
    @ApiModelProperty("启用状态 0否1是")
    @PropertyCheck(name = "启用状态 0否1是")
    @Column(name = "open_status")
    private Integer openStatus;

    /**
     * 删除状态 0否1是
     */
    @ApiModelProperty("删除状态 0否1是")
    @PropertyCheck(name = "删除状态 0否1是")
    @Column(name = "delete_status")
    private Integer deleteStatus;

    /**
     * 父级分类id
     */
    @ApiModelProperty("父级分类id")
    @PropertyCheck(name = "父级分类id")
    @Column(name = "parent_id")
    private Integer parentId;

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


}