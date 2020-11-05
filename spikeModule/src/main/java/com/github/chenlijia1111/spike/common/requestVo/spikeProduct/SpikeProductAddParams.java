package com.github.chenlijia1111.spike.common.requestVo.spikeProduct;

import com.github.chenlijia1111.commonModule.common.checkFunction.PositiveBigDecimalCheck;
import com.github.chenlijia1111.commonModule.common.checkFunction.PositiveNumberCheck;
import com.github.chenlijia1111.commonModule.common.checkFunction.PriceCheck;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品加入秒杀请求参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/25 0025 下午 5:03
 **/
@ApiModel
@Setter
@Getter
public class SpikeProductAddParams {

    /**
     * 产品id
     */
    @ApiModelProperty("产品id")
    @PropertyCheck(name = "产品id")
    private String productId;

    /**
     * 商品id,如果为空,表示整个产品都参与了秒杀,否则表示只有这一个商品参与
     */
    @ApiModelProperty("商品id,如果为空,表示整个产品都参与了秒杀,否则表示只有这一个商品参与")
    private String goodId;

    /**
     * 参与场次开始时间
     */
    @ApiModelProperty("参与场次开始时间")
    @PropertyCheck(name = "参与场次开始时间")
    private Date startTime;

    /**
     * 参与场次结束时间
     */
    @ApiModelProperty("参与场次结束时间")
    @PropertyCheck(name = "参与场次结束时间")
    private Date endTime;

    /**
     * 秒杀价格
     */
    @ApiModelProperty("秒杀价格")
    @PropertyCheck(name = "秒杀价格", checkFunction = PriceCheck.class)
    private Double spikePrice;

    /**
     * 参与秒杀的库存数量
     */
    @ApiModelProperty("参与秒杀的库存数量")
    @PropertyCheck(name = "参与秒杀的库存数量", checkFunction = PositiveBigDecimalCheck.class)
    private BigDecimal stockCount;

    /**
     * 排序值,用于秒杀列表排序
     */
    @ApiModelProperty("排序值,用于秒杀列表排序")
    private String orderNumber;
}
