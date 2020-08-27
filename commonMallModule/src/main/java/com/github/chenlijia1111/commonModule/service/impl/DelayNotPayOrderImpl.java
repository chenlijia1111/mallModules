package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.responseVo.order.DelayNotPayOrder;
import com.github.chenlijia1111.commonModule.dao.ShoppingOrderMapper;
import com.github.chenlijia1111.commonModule.service.IDelayNotPayOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 接口约束
 * 查询待支付订单
 * <p>
 * 系统会有一个默认实现，如果调用者有特殊需求，待支付订单可能比较特殊
 * 可以另外实现一个实现类并注入 spring 即可，系统会进行判断
 * 优先使用调用者的实现类
 *
 * @author Chen LiJia
 * @since 2020/8/27
 */
@Service("mallDelayNotPayOrderImpl")
public class DelayNotPayOrderImpl implements IDelayNotPayOrder {

    @Resource
    private ShoppingOrderMapper shoppingOrderMapper;

    /**
     * 列出未支付的订单
     * 用处：在系统启动的时候获取未支付的订单放入延时队列中去，等待处理
     *
     * @return
     */
    @Override
    public List<DelayNotPayOrder> listDelayNotPayOrder() {
        if (Objects.equals(CommonMallConstants.DELAY_NOT_PAY_ORDER_GROUP_ID_TYPE, 1)) {
            //以组订单的形式聚合待支付
            return shoppingOrderMapper.listDelayNotPayOrder();
        } else if (Objects.equals(CommonMallConstants.DELAY_NOT_PAY_ORDER_GROUP_ID_TYPE, 2)) {
            //以商家组订单的形式聚合待支付
            return shoppingOrderMapper.listDelayNotPayOrderWithShopGroupNo();
        } else if (Objects.equals(CommonMallConstants.DELAY_NOT_PAY_ORDER_GROUP_ID_TYPE, 3)) {
            //以单个订单的形式展示
            return shoppingOrderMapper.listDelayNotPayOrderWithOrderNo();
        }
        return new ArrayList<>();
    }
}
