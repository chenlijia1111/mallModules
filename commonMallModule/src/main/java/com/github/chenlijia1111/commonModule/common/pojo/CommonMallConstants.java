package com.github.chenlijia1111.commonModule.common.pojo;

/**
 * 通用商城中的系统常量
 *
 * @author chenlijia
 * @since 上午 10:51 2019/8/5 0005
 **/
public class CommonMallConstants {

    //订单状态 初始状态
    public static final Integer ORDER_INIT = 0;

    //订单状态 取消状态
    public static final Integer ORDER_CANCEL = 0XF0001;

    //订单状态 完成状态
    public static final Integer ORDER_COMPLETE = 0XF0002;

    //注入spring的bean的前缀名称
    public static final String BEAN_SUFFIX = "commonModule";


    //超时未支付时间 取消订单 允许修改 分钟
    public static Integer CANCEL_NOT_PAY_ORDER_LIMIT_MINUTES = 15;

    //超时未评价时间 自动评价 允许修改 分钟 默认7天
    public static Integer NOT_EVALUATE_ORDER_LIMIT_MINUTES = 7 * 24 * 60;

    //超时未收货时间 已签收后隔一段时间自动收货 自动评价 允许修改 分钟 默认7天
    public static Integer NOT_RECEIVE_ORDER_LIMIT_MINUTES = 7 * 24 * 60;

    //购物车 计算数量 是否需要过滤未上架的商品 默认否
    public static boolean SHOP_CAR_FILTER_NOT_SHELF_PRODUCT = false;

    /**
     * 查询未支付订单聚合方式，默认以组订单进行聚合
     * 1以组订单聚合 2以商家组订单聚合 3以订单为单个数据
     * 用于取消的时候决定取消订单的单位
     * 是直接将整个组订单取消，还是取消一个商家组订单，还是取消单个订单
     * {@link com.github.chenlijia1111.commonModule.service.ICancelOrderHook}
     * {@link com.github.chenlijia1111.commonModule.common.schedules.OrderCancelTimeLimitTask}
     * {@link com.github.chenlijia1111.commonModule.service.IDelayNotPayOrder}
     */
    public static Integer DELAY_NOT_PAY_ORDER_GROUP_ID_TYPE = 1;


}
