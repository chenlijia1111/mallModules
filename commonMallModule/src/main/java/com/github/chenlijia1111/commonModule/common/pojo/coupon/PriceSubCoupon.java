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
 * 满价格减券
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 6:06
 **/
@Setter
@Getter
@Accessors(chain = true)
public class PriceSubCoupon extends AbstractCoupon {

    /**
     * 优惠券代号
     *
     * @since 下午 6:08 2019/11/5 0005
     **/
    private String voucherKey = PriceSubCoupon.class.getSimpleName();

    /**
     * 优惠券id
     *
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
     * 优惠金额
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private Double subMoney;


    /**
     * 计算券的金额
     *
     * @param orderList 目标订单
     * @return java.lang.Double 优惠的金额
     * @since 下午 3:18 2019/11/22 0022
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
                //这些订单总共优惠的金额
                effectMoney = this.getSubMoney();
                //按比例计算单个订单优惠了多少钱
                for (ShoppingOrder order : orderList) {
                    Double orderAmountTotal = order.getOrderAmountTotal();
                    //这个订单优惠的金额
                    double orderSubMoney = effectMoney * (orderAmountTotal / allOrderAmountTotal);
                    //保留两位小数
                    orderSubMoney = NumberUtil.doubleToFixLengthDouble(orderSubMoney, 2);

                    //优惠之后的订单金额
                    double v = orderAmountTotal - orderSubMoney;
                    //保留两位小数
                    v = NumberUtil.doubleToFixLengthDouble(v, 2);
                    order.setOrderAmountTotal(v);

                    //添加当前的优惠券进去
                    List<AbstractCoupon> couponList = order.getCouponList();
                    this.setEffectiveMoney(orderSubMoney);
                    couponList.add(this);
                }
            }
        }
        return effectMoney;
    }
}
