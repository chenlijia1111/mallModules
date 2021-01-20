package com.github.chenlijia1111.commonModule.dao;

import com.github.chenlijia1111.commonModule.entity.ShopGroupOrder;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 商家组订单
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2021-01-20 09:37:18
 **/
public interface ShopGroupOrderMapper extends Mapper<ShopGroupOrder>, InsertListMapper<ShopGroupOrder> {
}
