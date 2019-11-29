package com.github.chenlijia1111.mallweb.biz;

import com.github.chenlijia1111.commonModule.biz.ShoppingOrderBiz;
import com.github.chenlijia1111.commonModule.common.requestVo.order.OrderAddParams;
import com.github.chenlijia1111.utils.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/29 0029 上午 9:39
 **/
@Service
public class OrderBiz {

    @Autowired
    private ShoppingOrderBiz shoppingOrderBiz;


    public Result addOrder(OrderAddParams params) {
        return shoppingOrderBiz.add(params);
    }

}
