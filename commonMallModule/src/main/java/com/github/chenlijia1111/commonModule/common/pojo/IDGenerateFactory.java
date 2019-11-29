package com.github.chenlijia1111.commonModule.common.pojo;

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
     * 产品id生成器
     *
     * @since 下午 2:45 2019/11/1 0001
     **/
    public static final IDUtil PRODUCT_ID_UTIL = new IDUtil(1, 1);

    /**
     * 商品id生成器
     *
     * @since 下午 2:45 2019/11/1 0001
     **/
    public static final IDUtil GOOD_ID_UTIL = new IDUtil(2, 2);

    /**
     * 订单id生成器
     *
     * @since 下午 2:46 2019/11/1 0001
     **/
    public static final IDUtil ORDER_ID_UTIL = new IDUtil(3, 3);

    /**
     * 评价id生成器
     *
     * @since 下午 2:46 2019/11/1 0001
     **/
    public static final IDUtil EVALUATION_ID_UTIL = new IDUtil(4, 1);


    /**
     * 评价标签id生成器
     *
     * @since 下午 2:46 2019/11/1 0001
     **/
    public static final IDUtil EVALUATION_LABEL_ID_UTIL = new IDUtil(4, 2);


    /**
     * 产品参与秒杀id生成器
     *
     * @since 下午 2:45 2019/11/1 0001
     **/
    public static final IDUtil SPIKE_PRODUCT_ID_UTIL = new IDUtil(5, 1);

    /**
     * 产品参与拼团id生成器
     *
     * @since 下午 2:45 2019/11/1 0001
     **/
    public static final IDUtil FIGHT_GROUP_PRODUCT_ID_UTIL = new IDUtil(6, 1);


    /**
     * 拼团-团id生成器
     *
     * @since 下午 2:45 2019/11/1 0001
     **/
    public static final IDUtil FIGHT_GROUP_ID_UTIL = new IDUtil(6, 2);

    /**
     * 拼团订单记录id生成器
     *
     * @since 下午 2:45 2019/11/1 0001
     **/
    public static final IDUtil FIGHT_GROUP_USER_ORDER_ID_UTIL = new IDUtil(6, 3);


    /**
     * 优惠券id生成器
     *
     * @since 下午 2:45 2019/11/1 0001
     **/
    public static final IDUtil COUPON_ID_UTIL = new IDUtil(7, 1);


}
