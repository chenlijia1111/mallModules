package com.github.chenlijia1111.commonModule.common.responseVo.evaluate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 评价列表对象
 *
 * @author Chen LiJia
 * @since 2020/5/12
 */
@ApiModel
@Setter
@Getter
public class EvaluateListVo {

    /**
     * 评价id
     */
    @ApiModelProperty("评价id")
    private String id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String clientId;

    /**
     * 商家id
     */
    @ApiModelProperty("商家id")
    private String shopId;

    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    private String orderNo;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private String goodId;

    /**
     * 产品id
     */
    @ApiModelProperty("产品id")
    private String productId;

    /**
     * 评价内容
     */
    @ApiModelProperty("评价内容")
    private String comment;

    /**
     * 图片内容
     */
    @ApiModelProperty("图片内容")
    private String images;

    /**
     * 商品评分
     */
    @ApiModelProperty("商品评分")
    private Double productLevel;

    /**
     * 商家评分
     */
    @ApiModelProperty("商家评分")
    private Double shopLevel;

    /**
     * 服务评分
     */
    @ApiModelProperty("服务评分")
    private Double serviceLevel;

    /**
     * 物流评分
     */
    @ApiModelProperty("物流评分")
    private Double expressLevel;

    /**
     * 父评价(表示追评等其他含义)
     */
    @ApiModelProperty("父评价(表示追评等其他含义)")
    private String parentEvalua;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String userNickName;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String userHeadImage;

    /**
     * 规格名称
     */
    @ApiModelProperty(value = "规格名称")
    private String skuName;

    /**
     * 订单快照
     */
    @JsonIgnore
    private String detailJson;

    /**
     * 是否启用 0否1是
     */
    @ApiModelProperty(value = "是否启用 0否1是")
    private Integer openStatus;

    /**
     * 创建默认的规格名称
     *
     * @param detailJson
     */
    public void setDetailJson(String detailJson) {
        this.detailJson = detailJson;
        if (StringUtils.isNotEmpty(detailJson) && StringUtils.isNotEmpty(this.goodId)) {
            AdminProductVo adminProductVo = JSONUtil.strToObj(detailJson, AdminProductVo.class);
            List<GoodVo> goodVoList = adminProductVo.getGoodVoList();
            Optional<GoodVo> goodVoOptional = goodVoList.stream().filter(e -> Objects.equals(e.getId(), this.goodId)).findAny();
            if (goodVoOptional.isPresent()) {
                GoodVo goodVo = goodVoOptional.get();
                this.skuName = goodVo.releaseSkuName(false, null, " | ");
            }
        }
    }
}
