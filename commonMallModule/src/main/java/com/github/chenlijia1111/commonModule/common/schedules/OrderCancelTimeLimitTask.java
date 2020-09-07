package com.github.chenlijia1111.commonModule.common.schedules;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.dao.ShoppingOrderMapper;
import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.commonModule.service.ICancelOrderHook;
import com.github.chenlijia1111.commonModule.utils.SpringContextHolder;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 任务
 * 订单定时取消策略
 * 延时队列
 * <p>
 * 如果要使用的话就把他注入到spring中去
 * 在项目启动时，没有进行查询了待支付的订单进延时队列，因为设涉及到物流数据的查询,调用者需要自己实现，
 * 我会判断项目是否注入了这个实例，如果注入了才会去查询
 * <p>
 * 取消订单之后会调用取消订单的钩子函数
 *
 * @author Chen LiJia
 * @see com.github.chenlijia1111.commonModule.service.ICancelOrderHook
 * 方便调用者处理其他自定义逻辑
 * @since 2020/4/8
 */
public class OrderCancelTimeLimitTask implements IOrderAutoTask {

    @Resource
    private ShoppingOrderMapper shoppingOrderMapper;

    @Override
    public void autoDealDelayOrder(OrderDelayPojo pojo) {
        if (Objects.equals(pojo.getOrderAutoTaskClass(), this.getClass())) {
            List<ShoppingOrder> shoppingOrders = null;
            //处理待支付订单的类型
            Integer delayNotPayOrderGroupIdType = CommonMallConstants.DELAY_NOT_PAY_ORDER_GROUP_ID_TYPE;
            String orderNo = pojo.getOrderNo();
            if (Objects.equals(delayNotPayOrderGroupIdType, 1)) {
                //组订单编号
                shoppingOrders = shoppingOrderMapper.listByGroupIdSetFilterLongField(Sets.asSets(orderNo));
            } else if (Objects.equals(delayNotPayOrderGroupIdType, 2)) {
                //商家组订单编号
                shoppingOrders = shoppingOrderMapper.listByShopGroupIdSetFilterLongField(Sets.asSets(orderNo));
            } else if (Objects.equals(delayNotPayOrderGroupIdType, 3)) {
                //订单编号
                shoppingOrders = shoppingOrderMapper.listByOrderNoSetFilterLongField(Sets.asSets(orderNo));
            }

            if (Lists.isNotEmpty(shoppingOrders)) {

                //判断状态，支付了就不需要取消了 只有初始状态才需要取消 即 0
                Optional<ShoppingOrder> initOrderAny = shoppingOrders.stream().filter(e -> Objects.equals(CommonMallConstants.ORDER_INIT, e.getState())).findAny();
                if (initOrderAny.isPresent()) {
                    //存在未支付的订单
                    //可以取消
                    for (int i = 0; i < shoppingOrders.size(); i++) {
                        ShoppingOrder shoppingOrder = shoppingOrders.get(i);
                        //修改为取消
                        shoppingOrder.setState(CommonMallConstants.ORDER_CANCEL);
                        //取消时间
                        shoppingOrder.setCancelTime(new Date());
                        shoppingOrderMapper.updateByPrimaryKeySelective(shoppingOrder);
                    }
                    //执行钩子函数  回补库存放到钩子函数进行执行
                    //因为可能不是每一个订单都需要回补库存的，比如预订单，他下定金的时候是不扣库存，到了付尾款才扣库存
                    //如果是待支付，取消就不需要回补库存了
                    try {
                        ICancelOrderHook cancelOrderHook = SpringContextHolder.getBean(ICancelOrderHook.class);
                        if (Objects.equals(delayNotPayOrderGroupIdType, 1)) {
                            //组订单编号
                            cancelOrderHook.cancelOrderByGroupId(orderNo);
                        } else if (Objects.equals(delayNotPayOrderGroupIdType, 2)) {
                            //商家组订单编号
                            cancelOrderHook.cancelOrderByShopGroupId(orderNo);
                        } else if (Objects.equals(delayNotPayOrderGroupIdType, 3)) {
                            //订单编号
                            cancelOrderHook.cancelOrderByOrderNo(orderNo);
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            }
        }
    }


}
