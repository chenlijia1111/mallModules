package com.github.chenlijia1111.commonModule.common.responseVo.product;

import com.github.chenlijia1111.commonModule.entity.GoodLabelPrice;
import com.github.chenlijia1111.commonModule.entity.Goods;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 陈礼佳
 * @since 2019/11/3 10:22
 */
@ApiModel
@Setter
@Getter
public class GoodVo extends Goods {


    /**
     * sku构建参数 是否需要规格名称
     * 可在项目启动之前修改
     */
    public static boolean needSpecName = false;

    /**
     * sku构建参数 规格名称与规格值之间的分隔符
     * 可在项目启动之前修改
     */
    public static String specNameAndValueDelimiter = null;

    /**
     * sku构建参数 规格之间的分隔符
     * 可在项目启动之前修改
     */
    public static String specDelimiter = "/";

    /**
     * 规格名称
     * 默认不进行构建，调用者自行构建
     */
    private String skuName;


    /**
     * 商品规格
     */
    @ApiModelProperty(value = "商品规格")
    private List<GoodSpecVo> goodSpecVoList;

    /**
     * 商品标签价格集合
     */
    @ApiModelProperty(value = "商品标签价格集合")
    private List<GoodLabelPrice> goodLabelPriceList;

    /**
     * 查询商品规格值
     * 2020-08-07---废弃，每个商品都通过这种方式去获取规格数据，消耗时间
     * 已修改为 group by 的实行获取
     * ----> GoodServiceImpl -> getFullInfo()
     *
     * @param list
     * @return
     */
    @Deprecated
    public GoodVo findGoodSpecVo(List<GoodSpecVo> list) {
        if (Lists.isNotEmpty(list)) {
            List<GoodSpecVo> collect = list.stream().filter(e -> Objects.equals(e.getGoodId(), this.getId())).collect(Collectors.toList());
            this.setGoodSpecVoList(collect);
        }
        return this;
    }


    /**
     * 判断两个商品是否是同一个商品
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof GoodVo)) {
            return false;
        }

        GoodVo goodVo = (GoodVo) o;

        List<GoodSpecVo> thisGoodSpecVoList = this.getGoodSpecVoList();
        List<GoodSpecVo> goodSpecVoList = goodVo.getGoodSpecVoList();
        if (Lists.isEmpty(thisGoodSpecVoList) || Lists.isEmpty(goodSpecVoList)) {
            return false;
        }

        Collections.sort(thisGoodSpecVoList, Comparator.comparing(GoodSpecVo::getSpecName));
        Collections.sort(goodSpecVoList, Comparator.comparing(GoodSpecVo::getSpecName));

        //比较两个数组是否相同
        return Objects.equals(thisGoodSpecVoList, goodSpecVoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodSpecVoList);
    }

    /**
     * 转化为规格比较器
     *
     * @return
     */
    public List<GoodSpecCompareVo> toGoodSpecCompareVo() {
        List<GoodSpecCompareVo> list = Lists.newArrayList();

        if (Lists.isNotEmpty(this.getGoodSpecVoList())) {
            List<GoodSpecVo> goodSpecVoList = this.getGoodSpecVoList();
            for (GoodSpecVo goodSpecVo : goodSpecVoList) {
                GoodSpecCompareVo goodSpecCompareVo = new GoodSpecCompareVo();
                goodSpecCompareVo.setGoodId(this.getId()).
                        setSpecName(goodSpecVo.getSpecName()).
                        setSpecImageValue(goodSpecVo.getSpecImageValue()).
                        setSpecValue(goodSpecVo.getSpecValue());
                list.add(goodSpecCompareVo);
            }
        }
        return list;
    }

    /**
     * 构建规格名称
     * 默认规格名称生成方式  示例：红色/L
     *
     * @return
     */
    public String releaseSkuName() {
        return releaseSkuName(needSpecName, specNameAndValueDelimiter, specDelimiter);
    }


    /**
     * 构建规格名称
     * 不用默认的sku生成方式 自定义
     *
     * @param needSpecName              是否需要规格名称
     * @param specNameAndValueDelimiter 规格名称与规格值之间的分隔符
     * @param specDelimiter             规格之间的分隔符
     * @return
     */
    public String releaseSkuName(boolean needSpecName, String specNameAndValueDelimiter, String specDelimiter) {

        StringBuilder skuName = new StringBuilder();

        List<GoodSpecVo> goodSpecVoList = this.getGoodSpecVoList();
        if (Lists.isNotEmpty(goodSpecVoList)) {
            for (GoodSpecVo goodSpecVo : goodSpecVoList) {
                if (needSpecName) {
                    skuName.append(goodSpecVo.getSpecName());
                    if (StringUtils.isNotEmpty(specNameAndValueDelimiter)) {
                        skuName.append(specNameAndValueDelimiter);
                    }
                }

                skuName.append(goodSpecVo.getSpecValue());
                if (StringUtils.isNotEmpty(specDelimiter)) {
                    skuName.append(specDelimiter);
                }
            }

            //删除最后一个规格分隔符
            if (StringUtils.isNotEmpty(specDelimiter)) {
                skuName.delete(skuName.length() - specDelimiter.length(), skuName.length());
            }
        }
        return skuName.toString();
    }

    /**
     * 根据标签重新渲染价格
     *
     * @param labelName
     */
    public void reRenderPriceWithLabel(String labelName) {
        if (StringUtils.isNotEmpty(labelName) && Lists.isNotEmpty(this.goodLabelPriceList)) {
            BigDecimal price = this.goodLabelPriceList.stream().filter(e -> Objects.equals(e.getLabelName(), labelName)).map(e -> e.getGoodPrice()).findAny().orElse(this.getPrice());
            setPrice(price);
        }
    }

}
