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
 * 拼团-团
 * @author chenLiJia
 * @since 2019-11-26 12:11:28
 * @version 1.0
 **/
@ApiModel("拼团-团")
@Table(name = "s_fight_group")
@Setter
@Getter
@Accessors(chain = true)
public class FightGroup {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 产品参与拼团表id
     */
    @ApiModelProperty("产品参与拼团表id")
    @PropertyCheck(name = "产品参与拼团表id")
    @Column(name = "fight_group_product_id")
    private String fightGroupProductId;

    /**
     * 拼团发起者
     */
    @ApiModelProperty("拼团发起者")
    @PropertyCheck(name = "拼团发起者")
    @Column(name = "create_user")
    private String createUser;

    /**
     * 拼团发起时间
     */
    @ApiModelProperty("拼团发起时间")
    @PropertyCheck(name = "拼团发起时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 成团人数
     * 成团人数必须同于过来,不可以修改
     * 即成团当时是多少人数可以成团就是多少人数人团
     * 不允许出现用户成团是成团人数是5人,当拼到4个人的时候后台又突然把成团人数修改为2人，
     * 这是拼团的状态就出现了逻辑问题，会给用户产生误导以及迷惑
     */
    @ApiModelProperty("成团人数")
    @PropertyCheck(name = "成团人数")
    @Column(name = "group_person_count")
    private Integer groupPersonCount;

    /**
     * 当前团员人数
     */
    @ApiModelProperty("当前团员人数")
    @PropertyCheck(name = "当前团员人数")
    @Column(name = "current_group_count")
    private Integer currentGroupCount;

    /**
     * 拼团状态 0拼团中 1拼团成功 2拼团失败
     */
    @ApiModelProperty("拼团状态 0拼团中 1拼团成功 2拼团失败")
    @PropertyCheck(name = "拼团状态 0拼团中 1拼团成功 2拼团失败")
    @Column(name = "fight_status")
    private Integer fightStatus;


}
