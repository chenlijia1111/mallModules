package com.github.chenlijia1111.commonModule.common.pojo.coupon;

import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.utils.core.NumberUtil;
import com.github.chenlijia1111.utils.list.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 积分券
 * 积分券一般都是一开始规则就定好的
 * 有些个别系统也可能会针对某个商品单独设置,
 * 处理结算的时候只需要从数据库查询有没有这个优惠券就行了
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 6:06
 **/
@Setter
@Getter
@Accessors(chain = true)
public class ScoreCoupon extends AbstractCoupon {

    /**
     * 优惠券代号
     *
     * @since 下午 6:08 2019/11/5 0005
     **/
    private String voucherKey = ScoreCoupon.class.getSimpleName();

    /**
     * 优惠券id
     *
     * @since 上午 10:13 2019/11/22 0022
     **/
    private String id;

    /**
     * 抵扣的积分
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private BigDecimal score;

    /**
     * 兑换比率 不可以为空,没有这个无法换算出对应可以抵扣的金额
     * ratio 为 1.5 表示 1 个积分可以抵扣 1.5 元
     *
     * @since 下午 4:07 2019/11/21 0021
     **/
    private BigDecimal ratio;

    /**
     * 最多可以抵扣的积分
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private BigDecimal maxScore;

    /**
     * 最多可以抵扣的金额比率
     * 比如maxMoneyRatio 是0.2 那么对多只能抵扣订单金额的 百分之二十
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private BigDecimal maxMoneyRatio;


    /**
     * 计算券的金额
     *
     * @param orderList 目标订单
     * @return java.lang.Double 优惠的金额
     * @since 下午 3:26 2019/11/22 0022
     **/
    @Override
    public BigDecimal calculatePayable(List<ShoppingOrder> orderList) {
        //总的优惠金额
        BigDecimal effectMoney = new BigDecimal("0.0");
        if (Lists.isNotEmpty(orderList)) {
            //这些订单的总应付金额
            BigDecimal allOrderAmountTotal = new BigDecimal("0.0");
            for (ShoppingOrder order : orderList) {
                allOrderAmountTotal = allOrderAmountTotal.add(order.getOrderAmountTotal());
            }

            if (Objects.nonNull(this.getScore()) && Objects.nonNull(this.getRatio())) {

                //判断是否超过了最多可以转换的积分
                if (Objects.nonNull(this.getMaxScore()) && this.getScore().compareTo(this.getMaxScore()) > 0) {
                    this.setScore(this.getMaxScore());
                }

                //积分转化的金额
                effectMoney = this.score.multiply(this.getRatio());
                //判断最多可以转换的金额
                if (Objects.nonNull(this.getMaxMoneyRatio())) {
                    BigDecimal maxTransferMoney = allOrderAmountTotal.multiply(this.getMaxMoneyRatio());
                    if (effectMoney.compareTo(maxTransferMoney) > 0) {
                        //最多可以转化的积分
                        BigDecimal maxTransferScore = maxTransferMoney.divide(this.getRatio());
                        //因为用了除法，所有保留两位小数
                        maxTransferScore.setScale(2,BigDecimal.ROUND_HALF_UP);
                        this.setScore(maxTransferScore);
                        //积分转化的金额
                        effectMoney = this.score.multiply(this.getRatio());
                    }
                }

                //按比例计算单个订单优惠了多少钱
                for (ShoppingOrder order : orderList) {
                    BigDecimal orderAmountTotal = order.getOrderAmountTotal();
                    //这个订单优惠的金额
                    BigDecimal orderSubMoney = effectMoney.multiply(orderAmountTotal).divide(allOrderAmountTotal);
                    //因为用了除法，保留两位小数
                    orderSubMoney.setScale(2,BigDecimal.ROUND_HALF_UP);

                    //优惠之后的订单金额
                    BigDecimal afterSubOrderMoney = orderAmountTotal.subtract(orderSubMoney);
                    order.setOrderAmountTotal(afterSubOrderMoney);

                    //添加当前的优惠券进去
                    List<AbstractCoupon> couponList = order.getCouponList();
                    this.setEffectiveMoney(orderSubMoney);
                    couponList.add(this);
                    order.setCouponList(couponList);
                }
            }
        }
        return effectMoney;
    }
}
