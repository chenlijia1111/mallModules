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
 * 用户地址信息表
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-27 10:39:55
 **/
@ApiModel("用户地址信息表")
@Table(name = "s_client_address")
@Setter
@Getter
@Accessors(chain = true)
public class ClientAddress {
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
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Column(name = "client_id")
    private String clientId;

    /**
     * 收货人姓名
     */
    @ApiModelProperty("收货人姓名")
    @PropertyCheck(name = "收货人姓名")
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 性别  1男 2女 3未知
     */
    @ApiModelProperty("性别  1男 2女 3未知")
    @PropertyCheck(name = "性别  1男 2女 3未知")
    @Column(name = "receive_sex")
    private Integer receiveSex;

    /**
     * 收货人手机号
     */
    @ApiModelProperty("收货人手机号")
    @PropertyCheck(name = "收货人手机号")
    @Column(name = "receiver_telephone")
    private String receiverTelephone;

    /**
     * 省 关联
     */
    @ApiModelProperty("省 关联")
    @PropertyCheck(name = "省 关联")
    @Column(name = "province")
    private String province;

    /**
     * 市 关联
     */
    @ApiModelProperty("市 关联")
    @PropertyCheck(name = "市 关联")
    @Column(name = "city")
    private String city;

    /**
     * 区 关联
     */
    @ApiModelProperty("区 关联")
    @PropertyCheck(name = "区 关联")
    @Column(name = "area")
    private String area;

    /**
     * 前端小程序自带地址插件code 用于回显
     */
    @ApiModelProperty("前端小程序自带地址插件code 用于回显")
    @PropertyCheck(name = "前端小程序自带地址插件code 用于回显")
    @Column(name = "address_code")
    private String addressCode;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    @PropertyCheck(name = "详细地址")
    @Column(name = "detail_address")
    private String detailAddress;

    /**
     * 是否常用地址 0否 1是
     */
    @ApiModelProperty("是否常用地址 0否 1是")
    @PropertyCheck(name = "是否常用地址 0否 1是")
    @Column(name = "common_address")
    private Integer commonAddress;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @PropertyCheck(name = "更新时间")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除 0未删除 1已删除
     */
    @ApiModelProperty("是否删除 0未删除 1已删除")
    @PropertyCheck(name = "是否删除 0未删除 1已删除")
    @Column(name = "delete_status")
    private Integer deleteStatus;


}
