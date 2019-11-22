package com.github.chenlijia1111.commonModule.common.responseVo.product;

import com.github.chenlijia1111.commonModule.entity.GoodSpec;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 商品规格返回对象
 *
 * @author 陈礼佳
 * @since 2019/11/3 10:24
 */
@ApiModel
@Setter
@Getter
public class GoodSpecVo extends GoodSpec {

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


    /**
     * 判断两个商品规格是否是相同
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodSpecVo)) return false;
        GoodSpecVo that = (GoodSpecVo) o;
        return Objects.equals(specName, that.specName) &&
                Objects.equals(specImageValue, that.specImageValue) &&
                Objects.equals(specValue, that.specValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specName, specImageValue, specValue);
    }
}
