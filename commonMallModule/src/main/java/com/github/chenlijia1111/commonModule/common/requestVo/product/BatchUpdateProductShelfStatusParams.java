package com.github.chenlijia1111.commonModule.common.requestVo.product;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.commonModule.common.checkFunction.StateCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 批量修改产品上架状态参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/9 0009 上午 11:51
 **/
@ApiModel
@Setter
@Getter
public class BatchUpdateProductShelfStatusParams {

    /**
     * 产品id集合
     * @since 上午 11:51 2019/11/9 0009
     **/
    @ApiModelProperty(value = "产品id集合")
    private List<String> idList;

    /**
     * 状态 0下架 1上架
     * @since 上午 11:51 2019/11/9 0009
     **/
    @ApiModelProperty(value = "状态 0下架 1上架")
    @PropertyCheck(name = "状态", checkFunction = StateCheck.class)
    private Integer state;

}
