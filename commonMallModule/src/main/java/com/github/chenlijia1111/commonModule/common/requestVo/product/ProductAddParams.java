package com.github.chenlijia1111.commonModule.common.requestVo.product;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.commonModule.common.checkFunction.StateCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 产品添加参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 1:51
 **/
@ApiModel
@Setter
@Getter
public class ProductAddParams {

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @PropertyCheck(name = "商品名称")
    private String name;

    /**
     * 商品类别
     * @since 下午 2:44 2019/11/21 0021
     **/
    @ApiModelProperty(value = "商品类别")
    private String categoryId;

    /**
     * 商品品牌
     * @since 下午 2:44 2019/11/21 0021
     **/
    @ApiModelProperty(value = "商品品牌")
    private String brand;

    /**
     * 产品编号
     * @since 下午 2:47 2019/11/21 0021
     **/
    @ApiModelProperty(value = "产品编号")
    private String productNo;

    /**
     * 单位
     * @since 下午 2:48 2019/11/21 0021
     **/
    @ApiModelProperty(value = "单位")
    private String unit;

    /**
     * 排序值
     * @since 下午 2:48 2019/11/21 0021
     **/
    @ApiModelProperty(value = "排序值")
    private Integer sortNumber;


    /**
     * 商品图片
     */
    @ApiModelProperty("商品图片")
    @PropertyCheck(name = "商品图片")
    private String smallPic;

    /**
     * 内容 富文本内容
     */
    @ApiModelProperty("商品详情")
    @PropertyCheck(name = "商品详情")
    private String content;

    /**
     * 是否上架 0未上架 1上架
     */
    @ApiModelProperty("是否上架 0未上架 1上架")
    @PropertyCheck(name = "商品状态", checkFunction = StateCheck.class)
    private Integer shelfStatus;

    /**
     * 产品规格
     * 产品规格和商品列表可以为空
     * 为空就是没有规格的产品
     * 默认添加一个默认商品
     * 一旦后面添加了规格，就把默认的商品删掉
     *
     * @since 下午 2:08 2019/11/1 0001
     **/
    @ApiModelProperty(value = "产品规格")
    @PropertyCheck(name = "产品规格")
    private List<ProductSpecParams> productSpecParamsList;

    /**
     * 商品列表
     * 可以为空
     *
     * @since 下午 2:10 2019/11/1 0001
     **/
    @ApiModelProperty(value = "商品列表")
    @PropertyCheck(name = "商品列表")
    private List<GoodAddParams> goodList;
}
