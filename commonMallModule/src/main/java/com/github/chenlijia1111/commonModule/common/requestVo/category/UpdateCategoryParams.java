package com.github.chenlijia1111.commonModule.common.requestVo.category;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编辑类别参数
 *
 * @author Chen LiJia
 * @since 2020/3/12
 */
@ApiModel
@Setter
@Getter
public class UpdateCategoryParams extends AddCategoryParams{

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    private Integer id;

}
