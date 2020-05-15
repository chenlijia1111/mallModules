package com.github.chenlijia1111.commonModule.common.requestVo.clientAddress;

import com.github.chenlijia1111.commonModule.common.checkFunction.StateCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * 新增收货地址参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 2:00
 **/
@ApiModel
@Setter
@Getter
public class AddParams {


    /**
     * 收货人姓名
     */
    @ApiModelProperty("收货人姓名")
    @PropertyCheck(name = "收货人姓名")
    private String receiverName;

    /**
     * 性别  1男 2女 3未知
     */
    @ApiModelProperty("性别  1男 2女 3未知")
    private Integer receiveSex;


    /**
     * 收货人手机号
     */
    @ApiModelProperty("收货人手机号")
    @PropertyCheck(name = "收货人手机号")
    private String receiverTelephone;

    /**
     * 省 关联
     */
    @ApiModelProperty("省")
    @PropertyCheck(name = "省")
    private String province;

    /**
     * 市 关联
     */
    @ApiModelProperty("市")
    @PropertyCheck(name = "市")
    private String city;

    /**
     * 区 关联
     */
    @ApiModelProperty("区")
    @PropertyCheck(name = "区")
    private String area;

    /**
     * 前端小程序自带地址插件code 用于回显
     */
    @ApiModelProperty("前端小程序自带地址插件code 用于回显")
    private String addressCode;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    @PropertyCheck(name = "详细地址")
    private String detailAddress;

    /**
     * 是否常用地址 0否 1是
     */
    @ApiModelProperty("是否常用地址 0否 1是")
    @PropertyCheck(name = "是否常用地址", checkFunction = StateCheck.class)
    private Integer commonAddress;

}
