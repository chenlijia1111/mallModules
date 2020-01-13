package com.github.chenlijia1111.commonModule.common.init;

import com.github.chenlijia1111.commonModule.dao.ImmediatePaymentOrderMapper;
import com.github.chenlijia1111.commonModule.dao.ReceivingGoodsOrderMapper;
import com.github.chenlijia1111.commonModule.dao.ReturnGoodsOrderMapper;
import com.github.chenlijia1111.commonModule.dao.ShoppingOrderMapper;
import com.github.chenlijia1111.commonModule.service.impl.*;
import com.github.chenlijia1111.commonModule.utils.SpringContextHolder;
import com.github.chenlijia1111.utils.core.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 初始化方法
 * 定时监听拼团-团解散
 * 查询到达时间限制且还没有拼团成功的拼团,将拼团的状态修改为拼团失败
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/27 0027 上午 9:19
 **/
@Service
public class InitFunction implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            //执行方法
            initMaxOrderNo();
        }
    }

    /**
     * 初始化订单流水号
     *
     * @return void
     * @since 上午 9:52 2019/11/27 0027
     **/
    private void initMaxOrderNo() {
        ShoppingOrderMapper orderMapper = SpringContextHolder.getBean(ShoppingOrderMapper.class);
        ImmediatePaymentOrderMapper immediatePaymentOrderMapper = SpringContextHolder.getBean(ImmediatePaymentOrderMapper.class);
        ReceivingGoodsOrderMapper receivingGoodsOrderMapper = SpringContextHolder.getBean(ReceivingGoodsOrderMapper.class);
        ReturnGoodsOrderMapper returnGoodsOrderMapper = SpringContextHolder.getBean(ReturnGoodsOrderMapper.class);
        //组订单编号
        String maxGroupId = orderMapper.maxGroupId();
        AtomicInteger groupIdSerialNum = createInitOrderAtomicInteger(maxGroupId);
        GroupIdGeneratorServiceImpl.currentNumber = groupIdSerialNum;

        //商家组订单编号
        String maxShopGroupId = orderMapper.maxShopGroupId();
        AtomicInteger shopGroupIdSerialNum = createInitOrderAtomicInteger(maxShopGroupId);
        ShopGroupIdGeneratorServiceImpl.currentNumber = shopGroupIdSerialNum;

        //订单编号
        String maxOrderNo = orderMapper.maxOrderNo();
        AtomicInteger orderNoSerialNum = createInitOrderAtomicInteger(maxOrderNo);
        ShoppingOrderIdGeneratorServiceImpl.currentNumber = orderNoSerialNum;

        //发货单编号
        String maxSendOrderNo = immediatePaymentOrderMapper.maxOrderNo();
        AtomicInteger sendOrderNoSerialNum = createInitOrderAtomicInteger(maxSendOrderNo);
        SendOrderIdGeneratorServiceImpl.currentNumber = sendOrderNoSerialNum;

        //收货单编号
        String maxReceiveOrderNo = receivingGoodsOrderMapper.maxOrderNo();
        AtomicInteger receiveOrderNoSerialNum = createInitOrderAtomicInteger(maxReceiveOrderNo);
        ReceiveOrderIdGeneratorServiceImpl.currentNumber = receiveOrderNoSerialNum;

        //退货单编号
        String maxReturnOrderNo = returnGoodsOrderMapper.maxOrderNo();
        AtomicInteger returnOrderNoSerialNum = createInitOrderAtomicInteger(maxReturnOrderNo);
        ReturnOrderIdGeneratorServiceImpl.currentNumber = returnOrderNoSerialNum;
    }


    /**
     * 根据单号生成当日当前流水号
     * 订单单号组成规则
     * 一位代号 + 年月日 + 6位流水
     * 如 120200111000001
     *
     * @param orderNo
     * @return
     */
    public AtomicInteger createInitOrderAtomicInteger(String orderNo) {
        if (StringUtils.isNotEmpty(orderNo) && orderNo.length() == 15) {
            //判断年月日,判断是否是今天
            String substring = orderNo.substring(1, 9);
            if (Objects.equals(substring, LocalDate.now().toString("yyyyMMdd"))) {
                //是今天
                //判断流水号
                String serialNum = orderNo.substring(9);
                Integer integer = Integer.valueOf(serialNum);
                return new AtomicInteger(integer);
            }
        }
        return new AtomicInteger(0);
    }
}
