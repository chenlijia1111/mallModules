package com.github.chenlijia1111.commonModule.common.pojo.coupon;

import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.NumberUtil;
import com.github.chenlijia1111.utils.list.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 满数量减券
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 6:06
 **/
@Setter
@Getter
@Accessors(chain = true)
public class CountSubCoupon extends AbstractCoupon{

    /**
     * 优惠券代号
     *
     * @since 下午 6:08 2019/11/5 0005
     **/
    private String voucherKey = CountSubCoupon.class.getSimpleName();

    /**
     * 优惠券id
     * @since 上午 10:13 2019/11/22 0022
     **/
    private String id;

    /**
     * 条件数量
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private Integer conditionCount;

    /**
     * 优惠金额
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private Double subMoney;


    /**
     * 计算当前优惠券对于目标金额可以抵扣多少金额
     * 满多少钱减多少钱,这个是相对于符合条件的所有订单而言的
     * 然后按照订单之间金额的比例计算具体每个订单优惠了多少钱
     *
     * @param orderList 目标订单
     * @return java.lang.Double 返回具体可以抵扣的金额
     * @since 上午 11:51 2019/11/22 0022
     **/
    @Override
    public Double calculatePayable(List<ShoppingOrder> orderList) {
        Double effectMoney = 0.0;
        if (Lists.isNotEmpty(orderList)) {
            //订单商品数量
            Integer goodCount = orderList.stream().collect(Collectors.summingInt(ShoppingOrder::getCount));
            //这些订单的总应付金额
            Double allOrderAmountTotal = orderList.stream().collect(Collectors.summingDouble(ShoppingOrder::getOrderAmountTotal));
            if (Objects.nonNull(this.getConditionCount()) && goodCount >= this.conditionCount) {
                //满足条件
                //享受折扣
                //这些订单总共优惠的金额
                effectMoney = this.getSubMoney();
                //如果总价格小于优惠抵扣价格，赋值为总价格
                if(allOrderAmountTotal < effectMoney){
                    effectMoney = allOrderAmountTotal;
                }
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
