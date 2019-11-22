package com.github.chenlijia1111.commonModule.common.responseVo.product;

import com.github.chenlijia1111.commonModule.entity.Goods;
import com.github.chenlijia1111.utils.list.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
     * 商品规格
     */
    @ApiModelProperty(value = "商品规格")
    private List<GoodSpecVo> goodSpecVoList;

    /**
     * 查询商品规格值
     *
     * @param list
     * @return
     */
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
}
