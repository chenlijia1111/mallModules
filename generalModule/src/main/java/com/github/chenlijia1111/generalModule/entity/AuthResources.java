package com.github.chenlijia1111.generalModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * 权限资源表
 * @author chenLiJia
 * @since 2020-04-20 09:56:17
 * @version 1.0
 **/
@ApiModel("权限资源表")
@Table(name = "s_auth_resources")
@Setter
@Getter
@Accessors(chain = true)
public class AuthResources {
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
     * 父权限资源id
     */
    @ApiModelProperty("父权限资源id")
    @PropertyCheck(name = "父权限资源id")
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    @PropertyCheck(name = "权限名称")
    @Column(name = "auth_name")
    private String authName;

    /**
     * 页面路径
     */
    @ApiModelProperty("页面路径")
    @PropertyCheck(name = "页面路径")
    @Column(name = "page_url")
    private String pageUrl;

    /**
     * 页面图标
     */
    @ApiModelProperty("页面图标")
    @PropertyCheck(name = "页面图标")
    @Column(name = "page_icon")
    private String pageIcon = "";

    /**
     * 请求路劲
     */
    @ApiModelProperty("请求路劲")
    @PropertyCheck(name = "请求路劲")
    @Column(name = "request_url")
    private String requestUrl;

    /**
     * 是否是按钮 0否1是
     */
    @ApiModelProperty("是否是按钮 0否1是")
    @PropertyCheck(name = "是否是按钮 0否1是")
    @Column(name = "button_status")
    private Integer buttonStatus;

    /**
     * 是否是页面 0否1是
     */
    @ApiModelProperty("是否是页面 0否1是")
    @PropertyCheck(name = "是否是页面 0否1是")
    @Column(name = "page_status")
    private Integer pageStatus;

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
     * 是否删除 0否1是
     */
    @ApiModelProperty("是否删除 0否1是")
    @PropertyCheck(name = "是否删除 0否1是")
    @Column(name = "delete_status")
    private Integer deleteStatus;

    /**
     * 权限类型 适配需要多个后台的项目，用于表示不同的平台的权限资源
     * 看是否需要，不需要可以不用管
     */
    @ApiModelProperty("权限类型 适配需要多个后台的项目，用于表示不同的平台的权限资源")
    @PropertyCheck(name = "权限类型")
    @Column(name = "auth_type")
    private Integer authType;

}
