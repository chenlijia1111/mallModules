package com.github.chenlijia1111.commonModule.common.requestVo.order;

import com.github.chenlijia1111.commonModule.common.checkFunction.PositiveBigDecimalCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 单个订单添加参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 4:47
 **/
@ApiModel
@Setter
@Getter
public class SingleOrderAddParams {

    /**
     * 商品id
     *
     * @since 下午 4:48 2019/11/5 0005
     **/
    @ApiModelProperty(value = "商品id")
    @PropertyCheck(name = "商品id")
    private String goodId;

    /**
     * 商品数量
     * 2020-10-27 修改 支持小数
     *
     * @since 下午 4:48 2019/11/5 0005
     **/
    @ApiModelProperty(value = "商品数量")
    @PropertyCheck(name = "商品数量", checkFunction = PositiveBigDecimalCheck.class)
    private BigDecimal count;

    /**
     * 单个订单补充参数
     * 适用于，添加订单的时候想给订单添加额外标记，但是又不想添加字段
     * 该字段为字符串形式，调用者可自定义保存内容
     */
    @ApiModelProperty(value = "单个订单补充参数")
    private String singleOrderAppend;

    /**
     * 指定价格
     */
    @ApiModelProperty(value = "指定价格")
    private BigDecimal goodPrice;


}
