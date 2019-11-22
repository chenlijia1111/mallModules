package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 优惠券
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-21 15:28:47
 **/
@ApiModel("优惠券")
@Table(name = "s_coupon")
@Setter
@Getter
public class Coupon {
    /**
     * 优惠券id
     */
    @ApiModelProperty("优惠券id")
    @PropertyCheck(name = "优惠券id")
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 优惠券名称
     *
     * @since 下午 4:43 2019/11/21 0021
     **/
    @ApiModelProperty
    @PropertyCheck(name = "优惠券名称")
    @Column(name = "coupon_name")
    private String couponName;

    /**
     * 优惠券剩余数量
     *
     * @since 下午 4:43 2019/11/21 0021
     **/
    @ApiModelProperty
    @PropertyCheck(name = "优惠券剩余数量")
    @Column(name = "coupon_count")
    private Integer couponCount;

    /**
     * 优惠券总数
     *
     * @since 下午 4:43 2019/11/21 0021
     **/
    @ApiModelProperty
    @PropertyCheck(name = "优惠券总数")
    @Column(name = "coupon_total_count")
    private Integer couponTotalCount;

    /**
     * 优惠券类型 1满价格减优惠券 2满价格折扣优惠券 3满数量减优惠券 4满数量折扣优惠券 5积分券 6物流券 其他待扩展
     * @see com.github.chenlijia1111.commonModule.common.enums.CouponTypeEnum
     */
    @ApiModelProperty("优惠券类型 1满价格减优惠券 2满价格折扣优惠券 3满数量减优惠券 4满数量折扣优惠券 5积分券 6物流券 其他待扩展")
    @PropertyCheck(name = "优惠券类型 1满价格减优惠券 2满价格折扣优惠券 3满数量减优惠券 4满数量折扣优惠券 5积分券 6物流券 其他待扩展")
    @Column(name = "coupon_type")
    private Integer couponType;

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
     * 1系统添加的优惠券 2后台用户添加的 3商家添加的
     */
    @ApiModelProperty("1系统添加的优惠券 2后台用户添加的 3商家添加的")
    @PropertyCheck(name = "1系统添加的优惠券 2后台用户添加的 3商家添加的")
    @Column(name = "create_user_type")
    private Integer createUserType;

    /**
     * 添加用户id
     */
    @ApiModelProperty("添加用户id")
    @PropertyCheck(name = "添加用户id")
    @Column(name = "create_user_id")
    private String createUserId;

    /**
     * 是否删除 0未删除 1删除
     */
    @ApiModelProperty("是否删除 0未删除 1删除")
    @PropertyCheck(name = "是否删除 0未删除 1删除")
    @Column(name = "delete_status")
    private String deleteStatus;

    /**
     * 优惠券json
     */
    @ApiModelProperty("优惠券json")
    @PropertyCheck(name = "优惠券json")
    @Column(name = "coupon_json")
    private String couponJson;


}
