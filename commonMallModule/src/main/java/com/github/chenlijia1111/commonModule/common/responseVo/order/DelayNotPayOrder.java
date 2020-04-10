package com.github.chenlijia1111.commonModule.common.responseVo.order;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Chen LiJia
 * @since 2020/4/8
 */
@Setter
@Getter
public class DelayNotPayOrder {

    /**
     * 订单组id
     */
    private String groupId;

    /**
     * 订单创建时间
     */
    private Date createTime;


}
