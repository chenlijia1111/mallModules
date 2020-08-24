package com.github.chenlijia1111.commonModule.common.responseVo.product;

import com.github.chenlijia1111.utils.list.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 客户端产品详情
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 12:08
 **/
@ApiModel
@Setter
@Getter
public class AppProductVo extends AdminProductVo {

    /**
     * 销量
     *
     * @since 上午 11:46 2019/11/5 0005
     **/
    @ApiModelProperty(value = "销量")
    private Integer salesVolume = 0;

    /**
     * 产品价格区间
     *
     * @since 下午 3:11 2019/11/18 0018
     **/
    @ApiModelProperty(value = "产品价格区间")
    private String priceRange;

    /**
     * 商品价格区间
     *
     * @since 下午 3:11 2019/11/18 0018
     **/
    @ApiModelProperty(value = "商品价格区间")
    private String MarketPriceRange;


    @Override
    public void setGoodVoList(List<GoodVo> goodVoList) {
        super.setGoodVoList(goodVoList);
        if (Lists.isNotEmpty(goodVoList)) {
            //查找产品价格区间
            //查询最小市场价以及最大市场价
            Optional<BigDecimal> minMarketPriceOption = goodVoList.stream().map(e -> e.getMarketPrice()).min(BigDecimal::compareTo);
            Optional<BigDecimal> maxMarketPriceOption = goodVoList.stream().map(e -> e.getMarketPrice()).max(BigDecimal::compareTo);
            if (minMarketPriceOption.isPresent() && maxMarketPriceOption.isPresent()) {
                BigDecimal minMarketPrice = minMarketPriceOption.get();
                BigDecimal maxMarketPrice = maxMarketPriceOption.get();
                this.setMarketPriceRange(Objects.equals(minMarketPrice, maxMarketPrice) ? (minMarketPrice + "") : (minMarketPrice + "-" + maxMarketPrice));
            }

            //查询最小售价以及最大售价
            Optional<BigDecimal> minPriceOption = goodVoList.stream().map(e -> e.getPrice()).min(BigDecimal::compareTo);
            Optional<BigDecimal> maxPriceOption = goodVoList.stream().map(e -> e.getPrice()).max(BigDecimal::compareTo);
            if (minPriceOption.isPresent() && maxPriceOption.isPresent()) {
                BigDecimal minPrice = minPriceOption.get();
                BigDecimal maxPrice = maxPriceOption.get();
                this.setPriceRange(Objects.equals(minPrice, maxPrice) ? (minPrice + "") : (minPrice + "-" + maxPrice));
            }

        }
    }
}
