package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.enums.OrderStatusEnum;
import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.dao.*;
import com.github.chenlijia1111.commonModule.entity.Evaluation;
import com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder;
import com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder;
import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.commonModule.service.ShoppingOrderServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "ShoppingOrderServiceI")
public class ShoppingOrderServiceImpl implements ShoppingOrderServiceI {


    @Resource
    private ShoppingOrderMapper shoppingOrderMapper;//购物单
    @Resource
    private ImmediatePaymentOrderMapper immediatePaymentOrderMapper;//发货单
    @Resource
    private ReceivingGoodsOrderMapper receivingGoodsOrderMapper;//收货单
    @Resource
    private GoodsMapper goodsMapper;//商品
    @Resource
    private ProductMapper productMapper;///产品
    @Resource
    private GoodSpecMapper goodSpecMapper;//商品规格
    @Resource
    private EvaluationMapper evaluationMapper;//评价


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result add(ShoppingOrder params) {

        int i = shoppingOrderMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 批量添加购物单
     * @param shoppingOrderList
     * @return
     */
    @Override
    public Result batchAdd(List<ShoppingOrder> shoppingOrderList) {
        if(Lists.isEmpty(shoppingOrderList)){
            return Result.failure("数据为空");
        }


        int i = shoppingOrderMapper.batchAdd(shoppingOrderList);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result update(ShoppingOrder params) {

        int i = shoppingOrderMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询订单
     *
     * @param condition 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ShoppingOrder>
     * @since 上午 10:54 2019/11/6 0006
     **/
    @Override
    public List<ShoppingOrder> listByCondition(ShoppingOrder condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return shoppingOrderMapper.select(condition);
    }

    /**
     * 条件统计数量
     *
     * @param condition 1
     * @return java.lang.Integer
     * @since 上午 11:59 2019/11/21 0021
     **/
    @Override
    public Integer countByCondition(ShoppingOrder condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return shoppingOrderMapper.selectCount(condition);
    }


    /**
     * 通过订单编号集合查询订单状态
     * 订单状态定义 1初始化 2取消 3已付款 4已发货 5已收货 6已评价 7已完成
     *
     * @param orderNoSet 1
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @see com.github.chenlijia1111.commonModule.common.enums.OrderStatusEnum
     * @since 下午 3:38 2019/11/7 0007
     **/
    @Override
    public Map<String, Integer> findOrderStateByOrderNoSet(Set<String> orderNoSet) {

        Map<String, Integer> map = new HashMap<>();

        if (Sets.isNotEmpty(orderNoSet)) {
            //查询这些订单的订单信息、发货单、收货单
            //所有购物单
            List<ShoppingOrder> shoppingOrders = shoppingOrderMapper.listByOrderNoSet(orderNoSet);
            //所有发货单
            List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderMapper.listByFrontOrderNoSet(orderNoSet);
            //所有发货单单号集合
            Set<String> sendOrderNoSet = immediatePaymentOrders.stream().map(e -> e.getOrderNo()).collect(Collectors.toSet());
            //所有收货单
            List<ReceivingGoodsOrder> receivingGoodsOrders = receivingGoodsOrderMapper.listByFrontOrderNoSet(sendOrderNoSet);
            //查询所有评价
            List<Evaluation> evaluations = evaluationMapper.listByOrderNoSet(orderNoSet);

            for (String orderNo : orderNoSet) {
                //查询这个订单的信息
                Optional<ShoppingOrder> any = shoppingOrders.stream().filter(e -> Objects.equals(orderNo, e.getOrderNo())).findAny();
                if (any.isPresent()) {
                    ShoppingOrder shoppingOrder = any.get();
                    Integer state = shoppingOrder.getState();
                    if (Objects.equals(state, CommonMallConstants.ORDER_INIT)) {
                        //初始状态
                        map.put(orderNo, OrderStatusEnum.INIT.getOrderStatus());
                    } else if (Objects.equals(state, CommonMallConstants.ORDER_CANCEL)) {
                        //取消
                        map.put(orderNo, OrderStatusEnum.CANCEL.getOrderStatus());
                    } else if (Objects.equals(state, CommonMallConstants.ORDER_COMPLETE)) {
                        //已支付
                        //判断有没有发货
                        Optional<ImmediatePaymentOrder> any1 = immediatePaymentOrders.stream().filter(e -> Objects.equals(orderNo, e.getFrontOrder())).findAny();
                        if (any1.isPresent()) {
                            ImmediatePaymentOrder immediatePaymentOrder = any1.get();
                            Integer sendState = immediatePaymentOrder.getState();
                            if (Objects.equals(CommonMallConstants.ORDER_INIT, sendState)) {
                                //未发货
                                map.put(orderNo, OrderStatusEnum.PAYED.getOrderStatus());
                            } else if (Objects.equals(CommonMallConstants.ORDER_COMPLETE, sendState)) {
                                //已发货
                                map.put(orderNo, OrderStatusEnum.SEND.getOrderStatus());
                            }

                            //判断是否已收货
                            Optional<ReceivingGoodsOrder> any2 = receivingGoodsOrders.stream().filter(e -> Objects.equals(immediatePaymentOrder.getOrderNo(), e.getFrontOrder())).findAny();
                            if (any2.isPresent()) {
                                ReceivingGoodsOrder receivingGoodsOrder = any2.get();
                                Integer receiveState = receivingGoodsOrder.getState();
                                if (Objects.equals(CommonMallConstants.ORDER_COMPLETE, receiveState)) {
                                    //已收货
                                    map.put(orderNo, OrderStatusEnum.RECEIVED.getOrderStatus());

                                    //判断是否已评价
                                    if (Lists.isNotEmpty(evaluations)) {
                                        Optional<Evaluation> any3 = evaluations.stream().filter(e -> Objects.equals(e.getOrderNo(), orderNo)).findAny();
                                        if (any3.isPresent()) {
                                            //已评价
                                            map.put(orderNo, OrderStatusEnum.EVALUAED.getOrderStatus());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //没有找到,订单不存在
            }
        }

        return map;
    }

    /**
     * 通过组订单id集合查询组订单状态
     * 订单状态定义 1初始化 2取消 3已付款 4已发货 5已收货 6已评价 7已完成
     *
     * @param groupIdSet 1
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @see com.github.chenlijia1111.commonModule.common.enums.OrderStatusEnum
     * @since 下午 3:39 2019/11/7 0007
     **/
    @Override
    public Map<String, Integer> findGroupStateByGroupIdSet(Set<String> groupIdSet) {

        Map<String, Integer> map = new HashMap<>();

        if (Sets.isNotEmpty(groupIdSet)) {
            List<ShoppingOrder> shoppingOrders = shoppingOrderMapper.listByGroupIdSet(groupIdSet);
            //订单编号集合
            Set<String> orderNoSet = shoppingOrders.stream().map(e -> e.getOrderNo()).collect(Collectors.toSet());
            //查询这些订单的状态
            Map<String, Integer> orderStatusMap = findOrderStateByOrderNoSet(orderNoSet);

            for (String groupId : groupIdSet) {
                //查找这个组的订单
                List<ShoppingOrder> groupOrderList = shoppingOrders.stream().filter(e -> Objects.equals(e.getGroupId(), groupId)).collect(Collectors.toList());

                //查找这些订单的订单状态 取最小状态来表示组订单的状态
                Optional<Integer> minStatus = groupOrderList.stream().map(e -> orderStatusMap.get(e.getOrderNo())).
                        collect(Collectors.toList()).stream().filter(e -> Objects.nonNull(e)).min(Comparator.comparing(e -> e));

                if (minStatus.isPresent()) {
                    map.put(groupId, minStatus.get());
                }
            }
        }

        return map;
    }


    /**
     * 通过订单编号查询订单信息
     *
     * @param orderNo 1
     * @return com.github.chenlijia1111.commonModule.entity.ShoppingOrder
     * @since 上午 9:37 2019/11/8 0008
     **/
    @Override
    public ShoppingOrder findByOrderNo(String orderNo) {
        if (StringUtils.isNotEmpty(orderNo)) {
            return shoppingOrderMapper.selectByPrimaryKey(orderNo);
        }
        return null;
    }

    /**
     * 更新
     * @param shoppingOrder
     * @param condition
     * @return
     */
    @Override
    public Result update(ShoppingOrder shoppingOrder, Example condition) {
        if(Objects.nonNull(shoppingOrder) && Objects.nonNull(condition)){
            int i = shoppingOrderMapper.updateByExampleSelective(shoppingOrder, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }
}
