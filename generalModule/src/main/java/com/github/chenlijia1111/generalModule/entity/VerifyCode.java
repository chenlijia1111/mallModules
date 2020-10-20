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
 * 系统验证码
 * @author chenLiJia
 * @since 2020-02-22 09:04:10
 * @version 1.0
 **/
@ApiModel("系统验证码")
@Table(name = "s_verify_code")
@Setter
@Getter
@Accessors(chain = true)
public class VerifyCode {
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
     * 调用方自定义  验证码类型
     */
    @ApiModelProperty("调用方自定义  验证码类型")
    @PropertyCheck(name = "调用方自定义  验证码类型")
    @Column(name = "code_type")
    private Integer codeType;

    /**
     * 验证码key 一般是登陆账户
     */
    @ApiModelProperty("验证码key 一般是登陆账户")
    @PropertyCheck(name = "验证码key 一般是登陆账户")
    @Column(name = "code_key")
    private String codeKey;

    /**
     * 验证码内容
     */
    @ApiModelProperty("验证码内容")
    @PropertyCheck(name = "验证码内容")
    @Column(name = "code_value")
    private String codeValue;

    /**
     * 验证码创建时间
     */
    @ApiModelProperty("验证码创建时间")
    @PropertyCheck(name = "验证码创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 验证码有效时间
     */
    @ApiModelProperty("验证码有效时间")
    @PropertyCheck(name = "验证码有效时间")
    @Column(name = "limit_time")
    private Date limitTime;


}
