package com.github.chenlijia1111.commonModule.common.responseVo.category;

import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.math.treeNode.TreeNodeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类别返回对象
 *
 * @author Chen LiJia
 * @since 2020/3/12
 */
@ApiModel
@Setter
@Getter
public class CategoryVo extends TreeNodeVo {

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Integer id;

    /**
     * 类别名称
     */
    @ApiModelProperty("类别名称")
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
    private Integer openStatus;

    /**
     * 删除状态 0否1是
     */
    @ApiModelProperty("删除状态 0否1是")
    private Integer deleteStatus;

    /**
     * 父级分类id
     */
    @ApiModelProperty("父级分类id")
    private Integer parentId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 编辑时间
     */
    @ApiModelProperty("编辑时间")
    private Date updateTime;

    /**
     * 下级分类
     */
    @ApiModelProperty(value = "下级分类")
    private List<CategoryVo> childCategory;

    @Override
    public void setChildTreeNodeList(List<TreeNodeVo> childTreeNodeList) {
        super.setChildTreeNodeList(childTreeNodeList);
        if (Lists.isNotEmpty(childTreeNodeList)) {
            childCategory = childTreeNodeList.stream().map(e -> (CategoryVo) e).collect(Collectors.toList());
        }
    }
}
