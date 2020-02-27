package com.github.chenlijia1111.commonModule.common.requestVo.evaluation;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;

/**
 * 添加评价参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/25 0025 下午 1:49
 **/
@ApiModel
@Setter
@Getter
public class AddEvaluationParams {

    /**
     * 订单编号
     */
    @ApiModelProperty("订单编号")
    @PropertyCheck(name = "订单编号")
    private String orderNo;


    /**
     * 图片
     */
    @ApiModelProperty("图片")
    private String images;


    /**
     * 评价内容
     */
    @ApiModelProperty("评价内容")
    private String comment;

    /**
     * 商品评分
     */
    @ApiModelProperty("商品评分")
    private Double productLevel;

    /**
     * 商家评分
     */
    @ApiModelProperty("商家评分")
    private Double shopLevel;

    /**
     * 服务评分
     */
    @ApiModelProperty("服务评分")
    private Double serviceLevel;

    /**
     * 物流评分
     */
    @ApiModelProperty("物流评分")
    private Double expressLevel;

    /**
     * 父评价(表示追评等其他含义)
     */
    @ApiModelProperty("父评价(表示追评等其他含义)")
    private String parentEvalua;

    /**
     * 评价标签
     * @since 下午 1:57 2019/11/25 0025
     **/
    @ApiModelProperty(value = "评价标签")
    private List<String> labels;

}
