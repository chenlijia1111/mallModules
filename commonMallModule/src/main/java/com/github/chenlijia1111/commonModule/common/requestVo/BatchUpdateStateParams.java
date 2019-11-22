package com.github.chenlijia1111.commonModule.common.requestVo;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.commonModule.common.checkFunction.StateCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 批量修改状态参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/9 0009 上午 11:51
 **/
@ApiModel
@Setter
@Getter
public class BatchUpdateStateParams {

    /**
     * id集合
     * @since 上午 11:51 2019/11/9 0009
     **/
    @ApiModelProperty(value = "id集合")
    private List<Integer> idList;

    /**
     * 状态 0关闭 1开启
     * @since 上午 11:51 2019/11/9 0009
     **/
    @ApiModelProperty(value = "状态 0关闭 1开启")
    @PropertyCheck(name = "状态", checkFunction = StateCheck.class)
    private Integer state;

}
