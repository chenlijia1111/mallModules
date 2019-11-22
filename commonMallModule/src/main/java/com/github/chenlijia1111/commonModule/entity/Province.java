package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政区域 - 省份
 * @author chenLiJia
 * @since 2019-11-01 09:33:11
 * @version 1.0
 **/
@ApiModel("行政区域 - 省份")
@Table(name = "s_district_province")
public class Province {
    /**
     * 
     */
    @ApiModelProperty("code")
    @PropertyCheck(name = "code")
    @Id
    @Column(name = "code")
    private String code;

    /**
     * 省份名称
     */
    @ApiModelProperty("省份名称")
    @PropertyCheck(name = "省份名称")
    @Column(name = "name")
    private String name;

    /**
     * 状态：0无效，1有效
     */
    @ApiModelProperty("状态：0无效，1有效")
    @PropertyCheck(name = "状态：0无效，1有效")
    @Column(name = "status")
    private Integer status;

    /**
     * 排序号
     */
    @ApiModelProperty("排序号")
    @PropertyCheck(name = "排序号")
    @Column(name = "rank")
    private Integer rank;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @PropertyCheck(name = "备注")
    @Column(name = "remark")
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
