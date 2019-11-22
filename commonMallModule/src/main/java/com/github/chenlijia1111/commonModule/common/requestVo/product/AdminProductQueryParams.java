package com.github.chenlijia1111.commonModule.common.requestVo.product;

import com.github.chenlijia1111.commonModule.common.requestVo.PageAbleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 后台产品列表查询参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 2:17
 **/
@ApiModel
@Setter
@Getter
public class AdminProductQueryParams extends PageAbleVo {

    /**
     * 产品编号
     * @since 下午 2:17 2019/11/1 0001
     **/
    @ApiModelProperty(value = "产品编号")
    private String productNo;

    /**
     * 产品名称
     * @since 下午 2:18 2019/11/1 0001
     **/
    @ApiModelProperty(value = "产品名称")
    private String productName;

}
