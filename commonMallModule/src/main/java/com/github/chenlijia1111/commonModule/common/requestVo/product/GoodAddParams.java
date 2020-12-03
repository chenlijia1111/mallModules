package com.github.chenlijia1111.commonModule.common.requestVo.product;

import com.github.chenlijia1111.commonModule.common.checkFunction.NaturalNumberCheck;
import com.github.chenlijia1111.commonModule.common.checkFunction.PriceCheck;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.ReleaseProductSkuSpecVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.ReleaseProductSkuVo;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.utils.list.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品添加参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 2:03
 **/
@ApiModel
@Setter
@Getter
public class GoodAddParams {

    /**
     * 商品的规格
     *
     * @since 下午 3:10 2019/11/1 0001
     **/
    @ApiModelProperty(value = "商品的规格")
    @PropertyCheck(name = "商品的规格")
    private List<GoodSpecParams> goodSpecParamsList;

    /**
     * 售价
     */
    @ApiModelProperty("售价")
    @PropertyCheck(name = "售价", checkFunction = PriceCheck.class)
    private Double price;

    /**
     * 会员价
     */
    @ApiModelProperty("会员价")
    private Double vipPrice;

    /**
     * 市场价
     */
    @ApiModelProperty("市场价")
    private Double marketPrice;

    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    private String goodNo;

    /**
     * 库存
     * 2020-10-27 修改，支持小数
     *
     * @since 下午 2:50 2019/11/21 0021
     **/
    @ApiModelProperty(value = "库存")
    @PropertyCheck(name = "库存")
    private BigDecimal stockCount;

    /**
     * 商品图片
     * 2020/04/22 新增 用于选择规格 选中时显示的图片
     */
    @ApiModelProperty(value = "商品图片")
    private String goodImage;

    /**
     * 2020-12-03
     * 商品标签价格添加参数
     */
    @ApiModelProperty(value = "商品标签价格添加参数")
    private List<GoodLabelPriceAddParams> goodLabelPriceList;


    /**
     * 转换 为构建规格对象
     *
     * @return
     */
    public ReleaseProductSkuVo transferToReleaseProductSkuVo() {
        ReleaseProductSkuVo vo = new ReleaseProductSkuVo();
        vo.setPrice(this.price);
        vo.setMarketPrice(this.marketPrice);
        vo.setVipPrice(this.vipPrice);
        vo.setStockCount(this.stockCount);

        if (Lists.isNotEmpty(this.goodSpecParamsList)) {
            List<GoodSpecParams> goodSpecParamsList = this.goodSpecParamsList;

            List<ReleaseProductSkuSpecVo> skuSpecVos = new ArrayList<>();
            for (GoodSpecParams goodSpecParams : goodSpecParamsList) {
                ReleaseProductSkuSpecVo specVo = new ReleaseProductSkuSpecVo();

                specVo.setSpecName(goodSpecParams.getSpecName());
                specVo.setValue(goodSpecParams.getSpecValue());
                specVo.setImageValue(goodSpecParams.getSpecImageValue());

                skuSpecVos.add(specVo);
            }

            vo.setSkuSpecVos(skuSpecVos);
        }
        return vo;
    }

    /**
     * 构建规格名称
     * 不用默认的sku生成方式 自定义
     * 与 {@link GoodVo#releaseSkuName()} 进行同步
     *
     * @return
     */
    public String releaseSkuName() {

        StringBuilder skuName = new StringBuilder();

        List<GoodSpecParams> goodSpecParamsList = this.getGoodSpecParamsList();
        if (Lists.isNotEmpty(goodSpecParamsList)) {
            for (GoodSpecParams goodSpecVo : goodSpecParamsList) {
                if (GoodVo.needSpecName) {
                    skuName.append(goodSpecVo.getSpecName());
                    if (StringUtils.isNotEmpty(GoodVo.specNameAndValueDelimiter)) {
                        skuName.append(GoodVo.specNameAndValueDelimiter);
                    }
                }

                skuName.append(goodSpecVo.getSpecValue());
                if (StringUtils.isNotEmpty(GoodVo.specDelimiter)) {
                    skuName.append(GoodVo.specDelimiter);
                }
            }

            //删除最后一个规格分隔符
            if (StringUtils.isNotEmpty(GoodVo.specDelimiter)) {
                skuName.delete(skuName.length() - GoodVo.specDelimiter.length(), skuName.length());
            }
        }
        return skuName.toString();
    }

}
