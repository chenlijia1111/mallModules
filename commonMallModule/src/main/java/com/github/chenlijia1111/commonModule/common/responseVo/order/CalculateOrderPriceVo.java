package com.github.chenlijia1111.commonModule.common.responseVo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 试算价格返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/22 0022 下午 4:04
 **/
@ApiModel
@Setter
@Getter
public class CalculateOrderPriceVo {

    /**
     * 总应付金额
     *
     * @since 下午 4:05 2019/11/22 0022
     **/
    @ApiModelProperty(value = "总应付金额")
    private BigDecimal payAble;

    /**
     * 费用详情信息
     */
    @ApiModelProperty(value = "费用详情信息")
    private Map<String, BigDecimal> feeMap;

}
