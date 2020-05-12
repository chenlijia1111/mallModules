package com.github.chenlijia1111.commonModule.common.requestVo.evaluation;

import com.github.chenlijia1111.commonModule.common.requestVo.PageAbleVo;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评价查询参数
 * @author Chen LiJia
 * @since 2020/5/12
 */
@ApiModel
@Setter
@Getter
public class QueryParams extends PageAbleVo {

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    private String productId;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String labelName;

    /**
     * 是否有图片 0否1是
     */
    @ApiModelProperty(value = "是否有图片 0否1是")
    private Integer imageStatus;



}
