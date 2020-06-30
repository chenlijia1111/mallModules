package com.github.chenlijia1111.commonModule.common.responseVo.evaluation;

import lombok.Getter;
import lombok.Setter;

/**
 * 标签数量统计对象
 * @author Chen LiJia
 * @since 2020/6/29
 */
@Setter
@Getter
public class LabelCountVo {

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 标签数量
     */
    private Integer count;

}
