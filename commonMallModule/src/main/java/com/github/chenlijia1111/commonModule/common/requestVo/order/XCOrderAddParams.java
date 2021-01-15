package com.github.chenlijia1111.commonModule.common.requestVo.order;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * 喜餐定制订单参数
 *
 * @author Chen LiJia
 * @since 2021/1/14
 */
@Setter
@Getter
@ApiModel
public class XCOrderAddParams extends OrderAddParams {

    /**
     * 指定创建时间
     */
    private Date createTime;

    /**
     * 指定组订单编号
     */
    private String groupId;

    /**
     * 指定商家组订单编号
     */
    private Map<String, String> shopGroupNoMap;

}
