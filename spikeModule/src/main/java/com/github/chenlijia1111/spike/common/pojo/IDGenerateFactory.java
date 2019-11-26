package com.github.chenlijia1111.spike.common.pojo;

import com.github.chenlijia1111.utils.core.IDUtil;

/**
 * {@link IDUtil} 工厂
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 2:44
 **/
public class IDGenerateFactory {

    /**
     * 产品参与秒杀id生成器
     *
     * @since 下午 2:45 2019/11/1 0001
     **/
    public static final IDUtil SPIKE_PRODUCT_ID_UTIL = new IDUtil(5, 1);

}
