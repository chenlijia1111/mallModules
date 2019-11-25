package com.github.chenlijia1111.commonModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 评价标签
 * @author chenLiJia
 * @since 2019-11-25 13:48:21
 * @version 1.0
 **/
@ApiModel("评价标签")
@Table(name = "s_evaluation_label")
@Setter
@Getter
@Accessors(chain = true)
public class EvaluationLabel {
    /**
     * id
     */
    @ApiModelProperty("id")
    @PropertyCheck(name = "id")
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 评价id
     */
    @ApiModelProperty("评价id")
    @PropertyCheck(name = "评价id")
    @Column(name = "evalua_id")
    private String evaluaId;

    /**
     * 标签名称
     */
    @ApiModelProperty("标签名称")
    @PropertyCheck(name = "标签名称")
    @Column(name = "label_name")
    private String labelName;

}
