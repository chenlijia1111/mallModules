package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.enums.ReturnOrderStatusEnum;
import com.github.chenlijia1111.commonModule.common.enums.ReturnTypeEnum;
import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.dao.ImmediatePaymentOrderMapper;
import com.github.chenlijia1111.commonModule.dao.ReceivingGoodsOrderMapper;
import com.github.chenlijia1111.commonModule.dao.ReturnGoodsOrderMapper;
import com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder;
import com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder;
import com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder;
import com.github.chenlijia1111.commonModule.service.ReturnGoodsOrderServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 退货单
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "ReturnGoodsOrderServiceI")
public class ReturnGoodsOrderServiceImpl implements ReturnGoodsOrderServiceI {


    @Resource
    private ReturnGoodsOrderMapper returnGoodsOrderMapper;
    @Resource
    private ImmediatePaymentOrderMapper immediatePaymentOrderMapper;//发货单
    @Resource
    private ReceivingGoodsOrderMapper receivingGoodsOrderMapper;//收货单


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result add(ReturnGoodsOrder params) {

        int i = returnGoodsOrderMapper.insertSelective(params);
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
    public Result update(ReturnGoodsOrder params) {

        int i = returnGoodsOrderMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询退货单列表
     *
     * @param condition 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder>
     * @since 上午 11:19 2019/11/25 0025
     **/
    @Override
    public List<ReturnGoodsOrder> listByCondition(ReturnGoodsOrder condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return returnGoodsOrderMapper.select(condition);
    }

    /**
     * 通过退货单单号查询退货单
     *
     * @param returnNo 退货单单号
     * @return com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder
     * @since 上午 11:20 2019/11/25 0025
     **/
    @Override
    public ReturnGoodsOrder findByReturnNo(String returnNo) {
        if (StringUtils.isNotEmpty(returnNo)) {
            return returnGoodsOrderMapper.selectByPrimaryKey(returnNo);
        }
        return null;
    }

    /**
     * 根据退货单号集合查询退货状态
     *
     * @param returnOrderNoSet
     * @return
     */
    @Override
    public Map<String, Integer> findReturnStatusByReturnOrderNoSet(Set<String> returnOrderNoSet) {
        Map<String, Integer> statusMap = new HashMap<>();
        if (Sets.isNotEmpty(returnOrderNoSet)) {

            //默认都赋值为未退款
            returnOrderNoSet.stream().forEach(e -> statusMap.put(e, ReturnOrderStatusEnum.NOT_RETURN.getReturnOrderStatus()));

            List<ReturnGoodsOrder> returnGoodsOrderList = returnGoodsOrderMapper.listByReturnOrderNoSet(returnOrderNoSet);
            if (Lists.isNotEmpty(returnGoodsOrderList)) {
                //开始处理
                List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderMapper.listByFrontOrderNoSet(returnOrderNoSet);
                //发货单编号集合
                Set<String> sendOrderNoSet = immediatePaymentOrders.stream().map(e -> e.getOrderNo()).collect(Collectors.toSet());
                List<ReceivingGoodsOrder> receivingGoodsOrderList = new ArrayList<>();
                if (Sets.isNotEmpty(sendOrderNoSet)) {
                    receivingGoodsOrderList = receivingGoodsOrderMapper.listByFrontOrderNoSet(sendOrderNoSet);
                }
                for (int i = 0; i < returnGoodsOrderList.size(); i++) {
                    ReturnGoodsOrder returnGoodsOrder = returnGoodsOrderList.get(i);
                    //退货单编号
                    String reOrderNo = returnGoodsOrder.getReOrderNo();
                    //整体状态
                    Integer state = returnGoodsOrder.getState();
                    //退款状态 0初始状态 983042成功 983041 失败或者取消
                    Integer reFundStatus = returnGoodsOrder.getReFundStatus();
                    //商家处理状态 0初始状态 983042成功 983041 失败或者取消
                    Integer checkStatus = returnGoodsOrder.getCheckStatus();
                    //退货类型 1退款 2退货 3退款退货
                    Integer reType = returnGoodsOrder.getReType();
                    if (Objects.equals(state, CommonMallConstants.ORDER_INIT)) {
                        //初始状态
                        statusMap.put(reOrderNo, ReturnOrderStatusEnum.INIT.getReturnOrderStatus());
                    } else if (Objects.equals(state, CommonMallConstants.ORDER_CANCEL)) {
                        //取消--分为客户取消 和商家拒绝
                        if (Objects.equals(checkStatus, CommonMallConstants.ORDER_INIT)) {
                            //客户取消
                            statusMap.put(reOrderNo, ReturnOrderStatusEnum.CUSTOM_CANCEL.getReturnOrderStatus());
                        } else if (Objects.equals(checkStatus, CommonMallConstants.ORDER_CANCEL)) {
                            //商家拒绝
                            statusMap.put(reOrderNo, ReturnOrderStatusEnum.SHOP_DENY.getReturnOrderStatus());
                        }
                    } else if (Objects.equals(state, CommonMallConstants.ORDER_COMPLETE)) {
                        if (Objects.equals(checkStatus, CommonMallConstants.ORDER_COMPLETE)) {
                            //商家同意
                            statusMap.put(reOrderNo, ReturnOrderStatusEnum.SHOP_AGREE.getReturnOrderStatus());
                        }
                        //判断用户是否需要发货
                        if (Objects.equals(reType, ReturnTypeEnum.RETURN_GOOD.getReturnType()) || Objects.equals(reType, ReturnTypeEnum.RETURN_GOOD_AND_MONEY.getReturnType())) {
                            //取最近的一个发货单数据
                            ImmediatePaymentOrder immediatePaymentOrder = immediatePaymentOrders.stream().filter(e -> Objects.equals(e.getFrontOrder(), reOrderNo)).findAny().orElse(null);
                            if (Objects.nonNull(immediatePaymentOrder) && Objects.equals(immediatePaymentOrder.getState(), CommonMallConstants.ORDER_COMPLETE)) {
                                //用户已发货
                                statusMap.put(reOrderNo, ReturnOrderStatusEnum.CUSTOM_SEND.getReturnOrderStatus());
                                //判断是否已收货
                                ReceivingGoodsOrder receivingGoodsOrder = receivingGoodsOrderList.stream().filter(e -> Objects.equals(e.getFrontOrder(), immediatePaymentOrder.getOrderNo())).findAny().orElse(null);
                                if (Objects.nonNull(receivingGoodsOrder) && Objects.equals(CommonMallConstants.ORDER_COMPLETE, receivingGoodsOrder.getState())) {
                                    //商家已收货
                                    statusMap.put(reOrderNo, ReturnOrderStatusEnum.SHOP_RECEIVE.getReturnOrderStatus());
                                    if (Objects.equals(reType, ReturnTypeEnum.RETURN_GOOD.getReturnType())) {
                                        //仅退货
                                        statusMap.put(reOrderNo, ReturnOrderStatusEnum.COMPLETE.getReturnOrderStatus());
                                    }
                                }
                            }
                        }
                        //判断商家是否已打款
                        if (Objects.equals(reFundStatus, CommonMallConstants.ORDER_COMPLETE)) {
                            //商家已打款
                            statusMap.put(reOrderNo, ReturnOrderStatusEnum.COMPLETE.getReturnOrderStatus());
                        }
                    }
                }
            }

        }
        return statusMap;
    }


    /**
     * 根据订单编号集合查询退货状态
     *
     * @param orderNoSet
     * @return
     */
    @Override
    public Map<String, Integer> findReturnStatusByOrderNoSet(Set<String> orderNoSet) {
        Map<String, Integer> statusMap = new HashMap<>();
        if (Sets.isNotEmpty(orderNoSet)) {

            //默认都赋值为未退款
            orderNoSet.stream().forEach(e -> statusMap.put(e, ReturnOrderStatusEnum.NOT_RETURN.getReturnOrderStatus()));

            List<ReturnGoodsOrder> returnGoodsOrderList = returnGoodsOrderMapper.listByOrderNoSet(orderNoSet);
            if (Lists.isNotEmpty(returnGoodsOrderList)) {
                //退货单单号
                Set<String> returnOrderNoSet = returnGoodsOrderList.stream().map(e -> e.getReOrderNo()).collect(Collectors.toSet());
                Map<String, Integer> returnStatusMap = findReturnStatusByReturnOrderNoSet(returnOrderNoSet);
                for (String orderNo : orderNoSet) {
                    //查询最进的一个
                    String hitReturnOrderNo = returnGoodsOrderList.stream().filter(e -> Objects.equals(e.getFrontOrder(), orderNo)).sorted(Comparator.comparing(ReturnGoodsOrder::getReCreateTime).reversed()).map(e -> e.getReOrderNo()).findFirst().orElse(null);
                    statusMap.put(orderNo, returnStatusMap.get(hitReturnOrderNo));
                }
            }
        }
        return statusMap;
    }

    /**
     * 根据订单编号查询售后信息
     *
     * @param orderNo
     * @return
     */
    @Override
    public ReturnGoodsOrder findByOrderNo(String orderNo) {
        if (StringUtils.isNotEmpty(orderNo)) {
            List<ReturnGoodsOrder> returnGoodsOrderList = returnGoodsOrderMapper.listByOrderNoSet(Sets.asSets(orderNo));
            if (Lists.isNotEmpty(returnGoodsOrderList)) {
                return returnGoodsOrderList.get(0);
            }
        }
        return null;
    }

    /**
     * 根据售后订单集合查询售后订单列表
     *
     * @param returnOrderNoSet
     * @return
     */
    @Override
    public List<ReturnGoodsOrder> listByReturnOrderNoSet(Set<String> returnOrderNoSet) {
        if (Sets.isNotEmpty(returnOrderNoSet)) {
            return returnGoodsOrderMapper.listByReturnOrderNoSet(returnOrderNoSet);
        }
        return new ArrayList<>();
    }
}
