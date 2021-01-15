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
     * shopId -> shopGroupNo
     */
    private Map<String, String> shopGroupNoMap;

    /**
     * 是否不需要校验商品
     * 如果为是的话
     * 就只校验数据是否存在即可，不校验上架啊，deleteStatus 等字段
     * 用于喜餐修改订单信息，相当于重新下单了，但是不需要校验商品
     */
    private Integer notCheckProduct;


}
