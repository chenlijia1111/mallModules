package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政区域 - 区县
 * @author chenLiJia
 * @since 2019-11-01 09:33:11
 * @version 1.0
 **/
@ApiModel("行政区域 - 区县")
@Table(name = "s_district_area")
public class Area {
    /**
     * 区县编号
     */
    @ApiModelProperty("区县编号")
    @PropertyCheck(name = "区县编号")
    @Id
    @Column(name = "code")
    private String code;

    /**
     * 区县名称
     */
    @ApiModelProperty("区县名称")
    @PropertyCheck(name = "区县名称")
    @Column(name = "name")
    private String name;

    /**
     * 状态：0有效，1无效
     */
    @ApiModelProperty("状态：0有效，1无效")
    @PropertyCheck(name = "状态：0有效，1无效")
    @Column(name = "status")
    private Integer status;

    /**
     * 所属市编号
     */
    @ApiModelProperty("所属市编号")
    @PropertyCheck(name = "所属市编号")
    @Column(name = "c_code")
    private String cCode;

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

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
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
