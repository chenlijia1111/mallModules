package com.github.chenlijia1111.commonModule.common.requestVo.returnOrder;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 商家处理退货单参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/25 0025 上午 11:55
 **/
@ApiModel
@Setter
@Getter
public class ShopHandleParams {

    /**
     * 退货单单号
     * @since 上午 11:56 2019/11/25 0025
     **/
    @ApiModelProperty(value = "退货单单号")
    @PropertyCheck(name = "退货单单号")
    private String returnOrderNo;

    /**
     * 处理结果(拒绝原因)
     * @since 上午 11:56 2019/11/25 0025
     **/
    @ApiModelProperty(value = "处理结果(拒绝原因)")
    private String handleResult;

}
