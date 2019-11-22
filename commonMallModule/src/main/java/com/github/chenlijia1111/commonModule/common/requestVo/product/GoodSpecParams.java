package com.github.chenlijia1111.commonModule.common.requestVo.product;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodSpecCompareVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 3:05
 **/
@ApiModel
@Setter
@Getter
public class GoodSpecParams {

    /**
     * 规格名称
     *
     * @since 下午 3:05 2019/11/1 0001
     **/
    @ApiModelProperty(value = "规格名称")
    @PropertyCheck(name = "规格名称")
    private String specName;


    /**
     * 图片规格值
     *
     * @since 下午 3:09 2019/11/1 0001
     **/
    @ApiModelProperty(value = "图片规格值")
    @PropertyCheck(name = "图片规格值")
    private String specImageValue;


    /**
     * 规格值
     *
     * @since 下午 3:05 2019/11/1 0001
     **/
    @ApiModelProperty(value = "规格值")
    @PropertyCheck(name = "规格值")
    private String specValue;


    /**
     * 转换成规格比较器
     *
     * @return
     */
    public GoodSpecCompareVo toGoodSpecCompareVo() {
        return new GoodSpecCompareVo().setSpecName(this.getSpecName()).
                setSpecImageValue(this.getSpecImageValue()).
                setSpecValue(this.getSpecValue());
    }

}
