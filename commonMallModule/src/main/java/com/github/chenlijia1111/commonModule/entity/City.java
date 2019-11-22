package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政区域 - 市
 * @author chenLiJia
 * @since 2019-11-01 09:33:11
 * @version 1.0
 **/
@ApiModel("行政区域 - 市")
@Table(name = "s_district_city")
public class City {
    /**
     * 主键，市编号
     */
    @ApiModelProperty("主键，市编号")
    @PropertyCheck(name = "主键，市编号")
    @Id
    @Column(name = "code")
    private String code;

    /**
     * 市名称
     */
    @ApiModelProperty("市名称")
    @PropertyCheck(name = "市名称")
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
     * 省份编号
     */
    @ApiModelProperty("省份编号")
    @PropertyCheck(name = "省份编号")
    @Column(name = "p_code")
    private String pCode;

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

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
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
