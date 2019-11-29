package com.github.chenlijia1111.mallweb.biz;

import com.github.chenlijia1111.commonModule.biz.ShoppingOrderBiz;
import com.github.chenlijia1111.commonModule.common.requestVo.order.OrderAddParams;
import com.github.chenlijia1111.fightGroup.biz.FightGroupOrderBiz;
import com.github.chenlijia1111.fightGroup.biz.FightGroupProductBiz;
import com.github.chenlijia1111.fightGroup.common.requestVo.fightGroupOrder.FightGroupOrderAddParams;
import com.github.chenlijia1111.fightGroup.common.requestVo.fightGroupProduct.FightGroupProductAddParams;
import com.github.chenlijia1111.spike.biz.SpikeOrderBiz;
import com.github.chenlijia1111.spike.biz.SpikeProductBiz;
import com.github.chenlijia1111.spike.common.requestVo.spikeOrder.SpikeOrderAddParams;
import com.github.chenlijia1111.spike.common.requestVo.spikeProduct.SpikeProductAddParams;
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
    @Autowired
    private SpikeOrderBiz spikeOrderBiz;//秒杀订单
    @Autowired
    private SpikeProductBiz spikeProductBiz;//商品参与秒杀
    @Autowired
    private FightGroupOrderBiz fightGroupOrderBiz;//拼团订单
    @Autowired
    private FightGroupProductBiz fightGroupProductBiz;//商品参加拼团


    public Result addOrder(OrderAddParams params) {
        return shoppingOrderBiz.add(params);
    }


    public Result addSpikeOrder(SpikeOrderAddParams params) {
        return spikeOrderBiz.addSpikeOrder(params, 3);
    }

    public Result productAddSpike(SpikeProductAddParams params) {
        return spikeProductBiz.add(params, false);
    }


    public Result addFightOrder(FightGroupOrderAddParams params){
        return fightGroupOrderBiz.addFightGroupOrder(params,3);
    }

    public Result addFightProduct(FightGroupProductAddParams params){
        return fightGroupProductBiz.add(params,false);
    }

}
