package com.github.chenlijia1111.commonModule.common.responseVo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * 商品比较器
 * 用于比较两个商品是否是一个商品
 *
 * @author 陈礼佳
 * @since 2019/11/3 11:06
 */
@ApiModel
@Setter
@Getter
@Accessors(chain = true)
public class GoodSpecCompareVo {

    /**
     * 商品id
     *
     * @since 下午 3:05 2019/11/1 0001
     **/
    @ApiModelProperty(value = "商品id")
    private String goodId;

    /**
     * 规格名称
     *
     * @since 下午 3:05 2019/11/1 0001
     **/
    @ApiModelProperty(value = "规格名称")
    private String specName;


    /**
     * 图片规格值
     *
     * @since 下午 3:09 2019/11/1 0001
     **/
    @ApiModelProperty(value = "图片规格值")
    private String specImageValue;


    /**
     * 规格值
     *
     * @since 下午 3:05 2019/11/1 0001
     **/
    @ApiModelProperty(value = "规格值")
    private String specValue;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodSpecCompareVo)) return false;
        GoodSpecCompareVo that = (GoodSpecCompareVo) o;
        return Objects.equals(specName, that.specName) &&
                Objects.equals(specImageValue, that.specImageValue) &&
                Objects.equals(specValue, that.specValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specName, specImageValue, specValue);
    }
}
