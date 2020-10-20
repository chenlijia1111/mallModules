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
 * 角色权限关联
 * @author chenLiJia
 * @since 2020-04-20 09:56:17
 * @version 1.0
 **/
@ApiModel("角色权限关联")
@Table(name = "s_role_auth")
@Setter
@Getter
@Accessors(chain = true)
public class RoleAuth {
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
     * 角色id
     */
    @ApiModelProperty("角色id")
    @PropertyCheck(name = "角色id")
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 权限id
     */
    @ApiModelProperty("权限id")
    @PropertyCheck(name = "权限id")
    @Column(name = "auth_id")
    private Integer authId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

}
