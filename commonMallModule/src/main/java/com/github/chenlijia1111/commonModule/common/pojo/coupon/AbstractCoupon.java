package com.github.chenlijia1111.commonModule.common.pojo.coupon;

import com.github.chenlijia1111.commonModule.common.enums.CouponTypeEnum;
import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 抽象优惠券
 * 目前已经实现的券
 * {@link CountDiscountCoupon} 满数量折扣优惠券
 * {@link CountSubCoupon} 满数量减优惠券
 * {@link PriceDiscountCoupon} 满价格折扣优惠券
 * {@link PriceSubCoupon} 满价格减优惠券
 * {@link ExpressCoupon} 物流券
 * {@link ScoreCoupon} 积分券
 * <p>
 * 因为设计时，不可能把所有的费用计算方式都涵盖，如果还需要其他的自定义的优惠券,
 * 只需要实现本类即可,请参考订单中处理优惠券的策略
 * 比如需要以下需求：运费计算需要按件数进行计算  5件以内10元 5件以上每加一件0.5元
 * 那么可以自己定义一个实现类，然后在 {@link AbstractCoupon#calculatePayable(List)} 进行计算即可
 * 订单查询的时候查询运费就取这个优惠券的生效金额就行了
 * Integer freight = 0;
 * String orderCoupon = order.getOrderCoupon();
 * if (StringUtils.isNotEmpty(orderCoupon)) {
 * JsonNode jsonNode = JSONUtil.strToJsonNode(orderCoupon);
 * for (JsonNode node : jsonNode) {
 * if (Objects.equals(ExpressCoupon.class.getSimpleName(), node.get("voucherKey").asText())) {
 * JsonNode effectiveMoney = node.get("effectiveMoney");
 * if (Objects.nonNull(effectiveMoney)) {
 * freight += effectiveMoney.asDouble();
 * }
 * }
 * }
 * }
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/22 0022 上午 11:49
 **/
public abstract class AbstractCoupon {

    /**
     * 生效金额
     **/
    private Double effectiveMoney = 0.0;

    /**
     * 优惠券具体的实现类名称
     * 如 com.github.chenlijia1111.commonModule.common.pojo.coupon.PriceSubCoupon
     */
    private String couponImplClassName = this.getClass().getName();


    /**
     * 计算当前优惠券对于目标金额可以抵扣多少金额
     *
     * @param orderList 目标订单
     * @return java.lang.Double 返回具体可以抵扣的金额
     **/
    public abstract Double calculatePayable(List<ShoppingOrder> orderList);

    /**
     * 获取抵扣金额
     *
     * @return java.lang.Double 返回具体抵扣的金额
     **/
    public Double getEffectiveMoney() {
        return effectiveMoney;
    }

    public void setEffectiveMoney(Double effectiveMoney) {
        this.effectiveMoney = effectiveMoney;
    }

    public String getCouponImplClassName() {
        return couponImplClassName;
    }

    /**
     * 获取当前优惠券的类别
     * 废弃不用
     *
     * @return java.lang.Integer
     **/
    @Deprecated
    public Integer type() {
        Optional<CouponTypeEnum> any = Lists.asList(CouponTypeEnum.values()).stream().filter(e -> Objects.equals(this.getClass(), e.getCouponClass())).findAny();
        if (any.isPresent()) {
            CouponTypeEnum couponTypeEnum = any.get();
            return couponTypeEnum.getType();
        }
        return null;
    }


    /**
     * 根据优惠券类型以及json 转化为优惠券对象
     *
     * @param type       在1.1.3_RELEASE之后的版本可以不传,直接用优惠券内的类名称进行转换
     * @param couponJson 2
     * @return com.github.chenlijia1111.commonModule.common.pojo.coupon.AbstractCoupon
     * @see CouponTypeEnum 优惠券类型枚举
     **/
    @Deprecated
    public static AbstractCoupon transferTypeToCoupon(Integer type, String couponJson) {
        if (Objects.nonNull(type) && StringUtils.isNotEmpty(couponJson)) {
            Optional<CouponTypeEnum> any = Lists.asList(CouponTypeEnum.values()).stream().filter(e -> Objects.equals(e.getType(), type)).findAny();
            if (any.isPresent()) {
                CouponTypeEnum couponTypeEnum = any.get();
                Class<? extends AbstractCoupon> couponClass = couponTypeEnum.getCouponClass();
                AbstractCoupon abstractCoupon = JSONUtil.strToObj(couponJson, couponClass);
                return abstractCoupon;
            }
        }
        return null;
    }

    /**
     * 根据优惠券类型以及json 转化为优惠券对象
     *
     * @param couponJson 2
     * @return com.github.chenlijia1111.commonModule.common.pojo.coupon.AbstractCoupon
     **/
    public static AbstractCoupon transferTypeToCoupon(String couponJson) {
        if (StringUtils.isNotEmpty(couponJson)) {

            String couponImplClassName = JSONUtil.strToJsonNode(couponJson).get("couponImplClassName").asText();
            if(StringUtils.isNotEmpty(couponImplClassName)){
                try {
                    Class<? extends AbstractCoupon> couponClass = (Class<? extends AbstractCoupon>) Class.forName(couponImplClassName);
                    AbstractCoupon abstractCoupon = JSONUtil.strToObj(couponJson, couponClass);
                    return abstractCoupon;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
