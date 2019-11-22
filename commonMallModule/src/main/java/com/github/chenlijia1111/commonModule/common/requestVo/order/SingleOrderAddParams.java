package com.github.chenlijia1111.commonModule.common.requestVo.order;

import com.github.chenlijia1111.commonModule.common.checkFunction.PositiveNumberCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
     *
     * @since 下午 4:48 2019/11/5 0005
     **/
    @ApiModelProperty(value = "商品数量")
    @PropertyCheck(name = "商品数量", checkFunction = PositiveNumberCheck.class)
    private Integer count;

}
