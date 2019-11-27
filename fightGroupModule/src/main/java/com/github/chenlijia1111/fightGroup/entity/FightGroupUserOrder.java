package com.github.chenlijia1111.fightGroup.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import javax.persistence.*;

/**
 * 拼团订单记录
 * @author chenLiJia
 * @since 2019-11-26 12:11:28
 * @version 1.0
 **/
@ApiModel("拼团订单记录")
@Table(name = "s_fight_group_user_order")
@Setter
@Getter
@Accessors(chain = true)
public class FightGroupUserOrder {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 拼团-团id
     */
    @ApiModelProperty("拼团-团id")
    @PropertyCheck(name = "拼团-团id")
    @Column(name = "fight_group_id")
    private String fightGroupId;

    /**
     * 团员id
     */
    @ApiModelProperty("团员id")
    @PropertyCheck(name = "团员id")
    @Column(name = "group_user_id")
    private String groupUserId;

    /**
     * 拼团订单编号
     */
    @ApiModelProperty("拼团订单编号")
    @PropertyCheck(name = "拼团订单编号")
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;


}
