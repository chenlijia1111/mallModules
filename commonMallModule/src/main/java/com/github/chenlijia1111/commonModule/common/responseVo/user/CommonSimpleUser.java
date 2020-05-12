package com.github.chenlijia1111.commonModule.common.responseVo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 简单用户信息
 * @author Chen LiJia
 * @since 2020/5/12
 */
@ApiModel
@Setter
@Getter
public class CommonSimpleUser {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String headImage;

}
