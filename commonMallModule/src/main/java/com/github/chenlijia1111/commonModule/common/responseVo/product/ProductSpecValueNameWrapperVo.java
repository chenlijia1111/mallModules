package com.github.chenlijia1111.commonModule.common.responseVo.product;

import lombok.Getter;
import lombok.Setter;

/**
 * 产品规格值映射对象
 * @author Chen LiJia
 * @since 2021/3/10
 */
@Setter
@Getter
public class ProductSpecValueNameWrapperVo {

    /**
     * 产品规格值id
     */
    private Integer productSpecValueId;

    /**
     * 产品规格值
     */
    private String productSpecValue;

    /**
     * 产品规格值图片
     */
    private String productSpecImageValue;

    /**
     * 产品规格id
     */
    private String productSpecId;

    /**
     * 产品规格名称
     */
    private String productSpecName;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 产品名称
     */
    private String productName;

}
