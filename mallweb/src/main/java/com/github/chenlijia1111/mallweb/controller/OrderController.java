package com.github.chenlijia1111.mallweb.controller;

import com.github.chenlijia1111.commonModule.common.requestVo.order.OrderAddParams;
import com.github.chenlijia1111.mallweb.biz.OrderBiz;
import com.github.chenlijia1111.utils.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制器
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/29 0029 上午 9:38
 **/
@RestController
@RequestMapping(value = "app/order")
@Api(tags = "订单控制器")
public class OrderController {

    @Autowired
    private OrderBiz biz;


    @PostMapping(value = "addOrder")
    @ApiOperation(value = "下单")
    public Result addOrder(@RequestBody OrderAddParams params) {
        return biz.addOrder(params);
    }

}
