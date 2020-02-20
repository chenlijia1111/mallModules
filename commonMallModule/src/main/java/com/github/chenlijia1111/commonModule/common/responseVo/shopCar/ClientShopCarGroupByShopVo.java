package com.github.chenlijia1111.commonModule.common.responseVo.shopCar;

import com.github.chenlijia1111.utils.list.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 客户端购物车返回视图
 * 已同一个商家对购物车的商品进行分组
 * 排序则以最近修改购物车的时间进行排序
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/8/17 0017 上午 11:18
 **/
@Setter
@Getter
@ApiModel
public class ClientShopCarGroupByShopVo {


    /**
     * 商家id
     *
     * @author chenlijia
     * @since 上午 11:24 2019/8/17 0017
     **/
    @ApiModelProperty(value = "商家id")
    private String shopId;

    /**
     * 店铺名称
     * 店铺名称需要调用者自己进行查询 因为产品表只存了店铺Id
     *
     * @author chenlijia
     * @since 上午 11:24 2019/8/17 0017
     **/
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 店铺logo
     */
    @ApiModelProperty(value = "店铺logo")
    private String storeLogo;


    /**
     * 当前商家下的
     * 购物车集合信息
     *
     * @author chenlijia
     * @since 下午 2:11 2019/8/17 0017
     **/
    @ApiModelProperty(value = "当前商家下的购物车集合信息")
    private List<ClientShopCarVo> shopCarVoList;


    /**
     * 查找
     * 当前商家下的
     * 购物车集合信息
     *
     * @param list 1
     * @return com.logicalthinking.jiuyou.common.reponseVo.shopCar.ClientShopCarGroupByShopVo
     * @author chenlijia
     * @since 下午 2:15 2019/8/17 0017
     **/
    public ClientShopCarGroupByShopVo findShopCarList(List<ClientShopCarVo> list) {
        if (Lists.isNotEmpty(list)) {
            List<ClientShopCarVo> collect = list.stream().filter(e -> Objects.equals(e.getShopId(), this.getShopId())).collect(Collectors.toList());
            this.shopCarVoList = collect;
        }
        return this;
    }

}
