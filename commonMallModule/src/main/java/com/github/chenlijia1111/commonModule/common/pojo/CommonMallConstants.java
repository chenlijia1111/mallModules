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


}
