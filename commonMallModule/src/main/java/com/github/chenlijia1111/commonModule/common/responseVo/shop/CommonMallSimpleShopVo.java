package com.github.chenlijia1111.commonModule.common.responseVo.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回店铺名称以及店铺logo
 * @author Chen LiJia
 * @since 2020/2/20
 */
@ApiModel
public class CommonMallSimpleShopVo {

    /**
     * 店铺Id
     */
    @ApiModelProperty(value = "店铺Id")
    private String shopId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 店铺logo
     */
    @ApiModelProperty(value = "店铺logo")
    private String storeLogo;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }
}
