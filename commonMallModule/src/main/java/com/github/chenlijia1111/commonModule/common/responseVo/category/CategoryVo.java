package com.github.chenlijia1111.commonModule.common.responseVo.category;

import com.github.chenlijia1111.commonModule.entity.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 类别返回对象
 * @author Chen LiJia
 * @since 2020/3/12
 */
@ApiModel
@Setter
@Getter
public class CategoryVo extends Category {

    /**
     * 下级分类
     */
    @ApiModelProperty(value = "下级分类")
    private List<CategoryVo> childCategory;

}
