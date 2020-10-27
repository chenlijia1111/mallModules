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
 * 物流券
 * 表示一个物流费用
 * 可以使用条件如满足多少钱减运费或者免运费
 * 可以使用条件如满足多少件减运费或者免运费
 * <p>
 * 物流券的获取方式应该是由调用者在下单时计算得出,不应该依赖于前端传递
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 6:06
 **/
@Setter
@Getter
@Accessors(chain = true)
public class ExpressCoupon extends AbstractCoupon {

    /**
     * 优惠券代号
     *
     * @since 下午 6:08 2019/11/5 0005
     **/
    private String voucherKey = ExpressCoupon.class.getSimpleName();

    /**
     * 优惠券id
     *
     * @since 上午 10:13 2019/11/22 0022
     **/
    private String id;

    /**
     * 物流费
     *
     * @since 下午 3:37 2019/11/21 0021
     **/
    private BigDecimal expressMoney;

    /**
     * 条件金额
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private BigDecimal conditionMoney;

    /**
     * 条件数量
     * 与条件金额二者取其一进行计算
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private Integer conditionCount;

    /**
     * 优惠金额
     * 与折扣率二者取其一进行计算
     *
     * @since 下午 3:39 2019/11/21 0021
     **/
    private BigDecimal subMoney;

    /**
     * 折扣率
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private BigDecimal discount;


    /**
     * 计算当前优惠券对于目标金额的物流费是多少
     * 先算出这些订单总的物流费
     * 然后再按各个订单的订单金额比例进行计算各个订单具体的物流费是多少
     *
     * @param orderList 目标订单
     * @return java.lang.Double 返回具体的物流费
     * @since 上午 11:51 2019/11/22 0022
     **/
    @Override
    public BigDecimal calculatePayable(List<ShoppingOrder> orderList) {
        //最终的物流费--可以解释为物流券的实际金额
        BigDecimal effectMoney = this.getExpressMoney();
        if (Lists.isNotEmpty(orderList)) {
            //订单商品数量
            BigDecimal goodCount = new BigDecimal("0.0");
            for (ShoppingOrder order : orderList) {
                goodCount = BigDecimalUtil.add(goodCount, order.getCount());
            }
            //这些订单的总应付金额
            BigDecimal allOrderAmountTotal = new BigDecimal("0.0");
            for (ShoppingOrder order : orderList) {
                allOrderAmountTotal = allOrderAmountTotal.add(order.getOrderAmountTotal());
            }

            if ((Objects.nonNull(this.getConditionMoney()) && allOrderAmountTotal.compareTo(this.getConditionMoney()) >= 0)
                    || (Objects.nonNull(this.getConditionCount()) && goodCount >= this.conditionCount)) {
                //两个条件满足一个即满足条件
                //享受折扣
                //计算总的物流费用
                if (Objects.nonNull(this.getSubMoney())) {
                    //满金额减物流费的类型
                    effectMoney = allOrderAmountTotal.subtract(this.getSubMoney());
                } else if (Objects.nonNull(this.getDiscount())) {
                    //折扣物流费的类型
                    effectMoney = this.getExpressMoney().multiply(this.getDiscount());
                }

            }
            //按比例计算单个订单的
            //防止除不均匀，最后一个算上除不尽的
            BigDecimal sumExpressMoney = new BigDecimal("0.0");
            for (int i = 0; i < orderList.size(); i++) {
                ShoppingOrder order = orderList.get(i);
                BigDecimal orderAmountTotal = order.getOrderAmountTotal();
                //订单物流费--单个订单的物流费
                BigDecimal orderExpressMoney;
                //这个订单的运费，如果订单金额为0，就直接平分
                if (Objects.equals(allOrderAmountTotal, 0.0)) {
                    orderExpressMoney = effectMoney.divide(new BigDecimal(orderList.size()));
                } else {
                    //订单金额不为0 ，根据订单金额进行计算，各个订单应该支付多少物流费
                    orderExpressMoney = effectMoney.multiply(orderAmountTotal).divide(allOrderAmountTotal);
                }

                //因为用了除法，所有取2位小数
                orderExpressMoney.setScale(2,BigDecimal.ROUND_HALF_UP);

                sumExpressMoney = sumExpressMoney.add(orderExpressMoney);
                if (i == orderList.size() - 1) {
                    //最后一个，看看有没有除尽
                    if (sumExpressMoney.compareTo(effectMoney) < 0) {
                        orderExpressMoney = orderExpressMoney.add(effectMoney).subtract(sumExpressMoney);
                    }
                }

                //加了运费之后的订单金额
                BigDecimal afterExpressOrderTotal = orderAmountTotal.add(orderExpressMoney);
                order.setOrderAmountTotal(afterExpressOrderTotal);

                //添加当前的优惠券进去
                List<AbstractCoupon> couponList = order.getCouponList();
                this.setEffectiveMoney(orderExpressMoney);
                couponList.add(this);
                order.setCouponList(couponList);
            }
        }
        return effectMoney;
    }
}
