package com.github.chenlijia1111.spike.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 秒杀下单记录
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-25 15:06:02
 **/
@ApiModel("秒杀下单记录")
@Table(name = "s_spike_order_recode")
@Setter
@Getter
@Accessors(chain = true)
public class SpikeOrderRecode {
    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    @PropertyCheck(name = "订单id")
    @Id
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 商品参加秒杀记录id
     */
    @ApiModelProperty("商品参加秒杀记录id")
    @PropertyCheck(name = "商品参加秒杀记录id")
    @Column(name = "spike_id")
    private String spikeId;

}
