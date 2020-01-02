package com.github.chenlijia1111.spike.common.response.product;

import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 秒杀产品快照
 *
 * @author Chen LiJia
 * @since 2020/1/2
 */
@Setter
@Getter
public class SpikeAdminProductVo extends AdminProductVo {


    /**
     * 参与场次开始时间
     */
    @ApiModelProperty("参与场次开始时间")
    @PropertyCheck(name = "参与场次开始时间")
    private Date spikeStartTime;

    /**
     * 参与场次结束时间
     */
    @ApiModelProperty("参与场次结束时间")
    @PropertyCheck(name = "参与场次结束时间")
    private Date spikeEndTime;

    /**
     * 秒杀价格
     */
    @ApiModelProperty("秒杀价格")
    @PropertyCheck(name = "秒杀价格")
    private Double spikePrice;

}
