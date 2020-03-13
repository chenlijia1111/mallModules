package com.github.chenlijia1111.commonModule.common.requestVo.category;

import com.github.chenlijia1111.commonModule.common.checkFunction.StateCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 添加类别参数
 *
 * @author Chen LiJia
 * @since 2020/3/12
 */
@ApiModel
@Setter
@Getter
public class AddCategoryParams {

    /**
     * 类别名称
     */
    @ApiModelProperty("类别名称")
    @PropertyCheck(name = "类别名称")
    private String categoryName;

    /**
     * 类别图标
     */
    @ApiModelProperty("类别图标")
    private String categoryImage;

    /**
     * 排序值
     */
    @ApiModelProperty("排序值")
    private Integer sortNum;

    /**
     * 启用状态 0否1是
     */
    @ApiModelProperty("启用状态 0否1是")
    @PropertyCheck(name = "启用状态 0否1是", checkFunction = StateCheck.class)
    private Integer openStatus;

    /**
     * 父级分类id
     */
    @ApiModelProperty("父级分类id")
    private Integer parentId;

}
