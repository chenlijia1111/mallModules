package com.github.chenlijia1111.commonModule.common.responseVo.shopCar;

import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.commonModule.entity.ShopCar;
import com.github.chenlijia1111.utils.list.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/8/17 0017 上午 11:18
 **/
@Setter
@Getter
@ApiModel
public class ClientShopCarVo extends ShopCar {

    /**
     * 商家id
     *
     * @author chenlijia
     * @since 下午 2:13 2019/8/17 0017
     **/
    @ApiModelProperty(value = "商家id")
    private String shopId;

    /**
     * 产品名称
     *
     * @author chenlijia
     * @since 上午 11:22 2019/8/17 0017
     **/
    @ApiModelProperty(value = "productName")
    private String productName;

    /**
     * 商品价格
     *
     * @author chenlijia
     * @since 上午 11:22 2019/8/17 0017
     **/
    @ApiModelProperty(value = "商品价格")
    private Double goodsPrice;

    /**
     * 商品规格名称
     * 用空格隔开
     * 如 红 L
     * 通过商品规格属性进行拼接
     *
     * @author chenlijia
     * @since 下午 1:34 2019/8/17 0017
     **/
    @ApiModelProperty(value = "商品规格名称")
    private String skuName;

    /**
     * 产品图片
     *
     * @author chenlijia
     * @since 上午 11:23 2019/8/17 0017
     **/
    @ApiModelProperty(value = "产品图片")
    private String productImage;

    /**
     * 库存数量
     * 2020-10-27 修改 支持小数
     *
     * @since 下午 9:18 2019/9/24 0024
     **/
    @ApiModelProperty("库存数量")
    private BigDecimal count;

    /**
     * 是否上架 0否 1是
     *
     * @since 下午 6:00 2019/9/23 0023
     **/
    @ApiModelProperty("是否上架 0否 1是")
    private Integer shelfStatus;


    /**
     * 查找商品价格信息
     *
     * @param list 1
     * @return com.logicalthinking.jiuyou.common.reponseVo.shopCar.ClientShopCarVo
     * @author chenlijia
     * @since 下午 1:36 2019/8/17 0017
     **/
    public ClientShopCarVo findGoodsInfo(List<GoodVo> list) {
        if (Lists.isNotEmpty(list) && StringUtils.hasText(this.getGoodId())) {
            Optional<GoodVo> any = list.stream().filter(e -> Objects.equals(e.getId(), this.getGoodId())).findAny();
            if (any.isPresent()) {
                GoodVo goods = any.get();
                //库存数量
                this.setCount(goods.getStockCount());
                //价格
                this.setGoodsPrice(goods.getPrice());
                //sku名称
                String skuName = goods.releaseSkuName();
                this.setSkuName(skuName);
            }
        }
        return this;
    }


}
