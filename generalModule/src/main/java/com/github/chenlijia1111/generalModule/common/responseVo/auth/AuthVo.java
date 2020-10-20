package com.github.chenlijia1111.generalModule.common.responseVo.auth;

import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.math.treeNode.TreeNodeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 返回用户权限封装对象
 * @author Chen LiJia
 * @since 2020/4/20
 */
@ApiModel
@Setter
@Getter
public class AuthVo extends TreeNodeVo {

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Integer id;

    /**
     * 父权限资源id
     */
    @ApiModelProperty("父权限资源id")
    private Integer parentId;

    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    private String authName;

    /**
     * 页面路径
     */
    @ApiModelProperty("页面路径")
    private String pageUrl;

    /**
     * 页面图标
     */
    @ApiModelProperty("页面图标")
    private String pageIcon = "";

    /**
     * 请求路劲
     */
    @ApiModelProperty("请求路劲")
    private String requestUrl;

    /**
     * 是否是按钮 0否1是
     */
    @ApiModelProperty("是否是按钮 0否1是")
    private Integer buttonStatus;

    /**
     * 是否是页面 0否1是
     */
    @ApiModelProperty("是否是页面 0否1是")
    private Integer pageStatus;

    /**
     * 下级权限资源列表
     * 本来是没有必要定义这个字段的，父类已经有一个这个类似的字段了，
     * 但是呢，有些前端页面选择的json是固定的字段值的
     */
    @ApiModelProperty(value = "下级权限资源列表")
    private List<AuthVo> children;


    public void setId(Integer id) {
        this.id = id;
        //赋值父类树id，用户组装数据
        if(Objects.nonNull(id)){
            super.setTreeId(id.toString());
        }
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
        //赋值父类树父id，用户组装数据
        if(Objects.nonNull(parentId)){
            super.setTreeParentId(parentId.toString());
        }
    }

    @Override
    public void setChildTreeNodeList(List<TreeNodeVo> childTreeNodeList) {
        super.setChildTreeNodeList(childTreeNodeList);
        if(Lists.isNotEmpty(childTreeNodeList)){
            //向下强转
            this.children = childTreeNodeList.stream().map(e->(AuthVo)e).collect(Collectors.toList());
        }
    }
}
