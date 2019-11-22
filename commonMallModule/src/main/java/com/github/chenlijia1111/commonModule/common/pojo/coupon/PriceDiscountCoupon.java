package com.github.chenlijia1111.commonModule.common.pojo.coupon;

import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.utils.core.NumberUtil;
import com.github.chenlijia1111.utils.list.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 满价格折扣券
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 6:06
 **/
@Setter
@Getter
@Accessors(chain = true)
public class PriceDiscountCoupon extends AbstractCoupon{

    /**
     * 优惠券代号
     *
     * @since 下午 6:08 2019/11/5 0005
     **/
    private String voucherKey = PriceDiscountCoupon.class.getSimpleName();

    /**
     * 优惠券id
     * @since 上午 10:13 2019/11/22 0022
     **/
    private String id;

    /**
     * 条件金额
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private Double conditionMoney;


    /**
     * 折扣率
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private Double discount;


    /**
     * 计算券的金额
     * @since 下午 3:13 2019/11/22 0022
     * @param orderList 目标订单
     * @return java.lang.Double 返回具体优惠的金额
     **/
    @Override
    public Double calculatePayable(List<ShoppingOrder> orderList) {
        Double effectMoney = 0.0;
        if (Lists.isNotEmpty(orderList)) {
            //这些订单的总应付金额
            Double allOrderAmountTotal = orderList.stream().collect(Collectors.summingDouble(ShoppingOrder::getOrderAmountTotal));
            if (Objects.nonNull(this.getConditionMoney()) && allOrderAmountTotal >= this.getConditionMoney()) {
                //满足条件
                //享受折扣
                for (ShoppingOrder order : orderList) {
                    Double orderAmountTotal = order.getOrderAmountTotal();
                    //优惠之后的订单金额
                    double v = orderAmountTotal * this.getDiscount();
                    //优惠的金额
                    double subMoney = orderAmountTotal - v;
                    //保留两位小数
                    subMoney = NumberUtil.doubleToFixLengthDouble(subMoney, 2);
                    //保留两位小数
                    v = NumberUtil.doubleToFixLengthDouble(v, 2);
                    order.setOrderAmountTotal(v);

                    //添加当前的优惠券进去
                    List<AbstractCoupon> couponList = order.getCouponList();
                    this.setEffectiveMoney(subMoney);
                    couponList.add(this);
                }
            }
        }
        return effectMoney;
    }
}
