package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.enums.OrderStatusEnum;
import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.responseVo.order.OrderStatusFieldVo;
import com.github.chenlijia1111.commonModule.dao.GoodsMapper;
import com.github.chenlijia1111.commonModule.dao.ShopGroupOrderMapper;
import com.github.chenlijia1111.commonModule.dao.ShoppingOrderMapper;
import com.github.chenlijia1111.commonModule.entity.Goods;
import com.github.chenlijia1111.commonModule.entity.ShopGroupOrder;
import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.commonModule.service.IFindOrderStateHook;
import com.github.chenlijia1111.commonModule.service.ShoppingOrderServiceI;
import com.github.chenlijia1111.commonModule.utils.BigDecimalUtil;
import com.github.chenlijia1111.commonModule.utils.SpringContextHolder;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    private GoodsMapper goodsMapper;//商品
    @Resource
    private ShopGroupOrderMapper shopGroupOrderMapper;// 商家组订单


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
     *
     * @param shoppingOrderList
     * @return
     */
    @Override
    public Result batchAdd(List<ShoppingOrder> shoppingOrderList) {
        if (Lists.isEmpty(shoppingOrderList)) {
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
            //查询订单状态
            List<OrderStatusFieldVo> orderStatusFieldVos = shoppingOrderMapper.listOrderStatusFieldVoByOrderNoSet(orderNoSet);
            //开始处理
            for (String orderNo : orderNoSet) {
                //查询这个订单的信息
                OrderStatusFieldVo orderStatusFieldVo = orderStatusFieldVos.stream().filter(e -> Objects.equals(e.getOrderNo(), orderNo)).findAny().orElse(null);
                if (Objects.nonNull(orderStatusFieldVo)) {
                    if (Objects.equals(orderStatusFieldVo.getOrderState(), CommonMallConstants.ORDER_INIT)) {
                        //初始状态
                        map.put(orderNo, OrderStatusEnum.INIT.getOrderStatus());
                    } else if (Objects.equals(orderStatusFieldVo.getOrderState(), CommonMallConstants.ORDER_CANCEL)) {
                        //取消
                        map.put(orderNo, OrderStatusEnum.CANCEL.getOrderStatus());
                    } else if (Objects.equals(orderStatusFieldVo.getOrderState(), CommonMallConstants.ORDER_COMPLETE)) {
                        //已支付
                        //判断有没有发货
                        if (Objects.equals(CommonMallConstants.ORDER_INIT, orderStatusFieldVo.getSendStatus())) {
                            //未发货
                            map.put(orderNo, OrderStatusEnum.PAYED.getOrderStatus());
                        } else if (Objects.equals(CommonMallConstants.ORDER_COMPLETE, orderStatusFieldVo.getSendStatus())) {
                            //已发货
                            map.put(orderNo, OrderStatusEnum.SEND.getOrderStatus());
                        }

                        //判断是否已收货
                        if (Objects.equals(CommonMallConstants.ORDER_COMPLETE, orderStatusFieldVo.getReceiveStatus())) {
                            //已收货
                            map.put(orderNo, OrderStatusEnum.RECEIVED.getOrderStatus());

                            //判断是否已评价
                            if (Objects.equals(BooleanConstant.YES_INTEGER, orderStatusFieldVo.getEvaluateStatus())) {
                                //已评价
                                map.put(orderNo, OrderStatusEnum.EVALUAED.getOrderStatus());
                            }
                        }
                    }
                    if (Objects.equals(BooleanConstant.YES_INTEGER, orderStatusFieldVo.getCompleteStatus())) {
                        //已完成
                        map.put(orderNo, OrderStatusEnum.COMPLETED.getOrderStatus());
                    }
                }
                //没有找到,订单不存在
            }
        }

        //执行钩子函数
        try {
            IFindOrderStateHook findOrderStateHook = SpringContextHolder.getBean(IFindOrderStateHook.class);
            map = findOrderStateHook.findOrderStateByOrderNoSet(orderNoSet, map);
        } catch (Exception e) {
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

        //执行钩子函数
        try {
            IFindOrderStateHook findOrderStateHook = SpringContextHolder.getBean(IFindOrderStateHook.class);
            map = findOrderStateHook.findGroupStateByGroupIdSet(groupIdSet, map);
        } catch (Exception e) {
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
     *
     * @param shoppingOrder
     * @param condition
     * @return
     */
    @Override
    public Result update(ShoppingOrder shoppingOrder, Example condition) {
        if (Objects.nonNull(shoppingOrder) && Objects.nonNull(condition)) {
            int i = shoppingOrderMapper.updateByExampleSelective(shoppingOrder, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }

    /**
     * 取消订单
     * 客户主动取消订单,只能是待支付或已完成的订单
     * 并且是组订单全部取消
     *
     * @param groupId
     * @param canCancelStatus 可以取消订单的状态
     * @return
     */
    @Override
    public Result cancelOrder(String groupId, List<Integer> canCancelStatus) {
        //组订单id为空
        if (StringUtils.isEmpty(groupId)) {
            return Result.failure("组订单id为空");
        }

        //查询订单是否存在
        ShoppingOrder shoppingOrderCondition = new ShoppingOrder().
                setGroupId(groupId);
        List<ShoppingOrder> shoppingOrders = shoppingOrderMapper.select(shoppingOrderCondition);
        if (Lists.isEmpty(shoppingOrders)) {
            return Result.failure("订单不存在");
        }

        //判断订单状态
        //1初始化 2取消 3已付款 4已发货 5已收货 6已评价 7已完成
        Map<String, Integer> groupStateMap = findGroupStateByGroupIdSet(Sets.asSets(groupId));
        Integer groupState = groupStateMap.get(groupId);
        if (!canCancelStatus.contains(groupState)) {
            return Result.failure("操作失败");
        }

        //取消订单
        for (ShoppingOrder order : shoppingOrders) {
            order.setState(CommonMallConstants.ORDER_CANCEL);
            order.setCancelTime(new Date());
            shoppingOrderMapper.updateByPrimaryKeySelective(order);

            //回补库存
            Goods goodVo = goodsMapper.selectByPrimaryKey(order.getGoodsId());
            if (Objects.nonNull(goodVo)) {
                BigDecimal stackCount = BigDecimalUtil.add(goodVo.getStockCount(), order.getCount());
                goodVo.setStockCount(stackCount);
                goodsMapper.updateByPrimaryKeySelective(goodVo);
            }
        }

        return Result.success("操作成功");
    }

    /**
     * 根据订单编号取消订单
     *
     * @param orderNo
     * @param canCancelStatus 可以取消订单的状态
     * @return
     */
    @Override
    public Result cancelOrderByOrderNo(String orderNo, List<Integer> canCancelStatus) {
        //订单id为空
        if (StringUtils.isEmpty(orderNo)) {
            return Result.failure("订单id为空");
        }

        //查询订单是否存在
        ShoppingOrder shoppingOrder = shoppingOrderMapper.selectByPrimaryKey(orderNo);
        if (Objects.isNull(shoppingOrder)) {
            return Result.failure("数据不存在");
        }

        //判断订单状态
        //1初始化 2取消 3已付款 4已发货 5已收货 6已评价 7已完成
        Map<String, Integer> orderStateMap = findOrderStateByOrderNoSet(Sets.asSets(orderNo));
        Integer orderStatus = orderStateMap.get(orderNo);
        if (!canCancelStatus.contains(orderStatus)) {
            return Result.failure("操作失败");
        }

        //取消订单
        shoppingOrder.setState(CommonMallConstants.ORDER_CANCEL);
        shoppingOrder.setCancelTime(new Date());
        shoppingOrderMapper.updateByPrimaryKeySelective(shoppingOrder);

        //回补库存
        Goods goodVo = goodsMapper.selectByPrimaryKey(shoppingOrder.getGoodsId());
        if (Objects.nonNull(goodVo)) {
            BigDecimal stackCount = BigDecimalUtil.add(goodVo.getStockCount(), shoppingOrder.getCount());
            goodVo.setStockCount(stackCount);
            goodsMapper.updateByPrimaryKeySelective(goodVo);
        }
        return Result.success("操作成功");
    }

    /**
     * 取消订单
     * 前置判断由调用者自己判断
     *
     * @param orderNoSet
     * @return
     */
    @Override
    public Result cancelOrderByOrderNoSet(Set<String> orderNoSet) {
        if (Sets.isNotEmpty(orderNoSet)) {
            List<ShoppingOrder> shoppingOrderList = shoppingOrderMapper.listByOrderNoSetFilterLongField(orderNoSet);
            if (Lists.isNotEmpty(shoppingOrderList)) {
                Set<String> goodIdSet = new HashSet<>();
                Set<String> shopGroupIdSet = new HashSet<>();
                for (ShoppingOrder shoppingOrder : shoppingOrderList) {
                    goodIdSet.add(shoppingOrder.getGoodsId());
                    shopGroupIdSet.add(shoppingOrder.getShopGroupId());
                }
                // 开始执行取消操作
                Date currentTime = new Date();
                ShoppingOrder shoppingOrderSetCondition = new ShoppingOrder().setCancelTime(currentTime).
                        setState(CommonMallConstants.ORDER_CANCEL);
                Example shoppingOrderWhereCondition = Example.builder(ShoppingOrder.class).
                        where(Sqls.custom().andIn("orderNo", orderNoSet)).build();
                shoppingOrderMapper.updateByExampleSelective(shoppingOrderSetCondition, shoppingOrderWhereCondition);
                // 商家组订单
                ShopGroupOrder shopGroupOrderSetCondition = new ShopGroupOrder();
                shopGroupOrderSetCondition.setCancelStatus(BooleanConstant.YES_INTEGER);
                Example shopGroupOrderWhereCondition = Example.builder(ShopGroupOrder.class).
                        where(Sqls.custom().andIn("shopGroupId", shopGroupIdSet)).build();
                shopGroupOrderMapper.updateByExampleSelective(shopGroupOrderSetCondition, shopGroupOrderWhereCondition);

                // 回补库存
                List<Goods> goodsList = goodsMapper.selectByExample(Example.builder(Goods.class).
                        where(Sqls.custom().andIn("id", goodIdSet)).build());
                if (Lists.isNotEmpty(goodsList)) {
                    // 进行回补库存
                    for (ShoppingOrder shoppingOrder : shoppingOrderList) {
                        String goodsId = shoppingOrder.getGoodsId();
                        Goods goods = goodsList.stream().filter(e -> Objects.equals(e.getId(), goodsId)).
                                findAny().orElse(null);
                        if (Objects.nonNull(goods)) {
                            BigDecimal stackCount = BigDecimalUtil.add(goods.getStockCount(), shoppingOrder.getCount());
                            goods.setStockCount(stackCount);
                            goodsMapper.updateByPrimaryKeySelective(goods);
                        }
                    }
                }
            }
        }
        return Result.success("操作成功");
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     */
    @Override
    public List<ShoppingOrder> listByCondition(Example condition) {
        if (Objects.nonNull(condition)) {
            return shoppingOrderMapper.selectByExample(condition);
        }
        return new ArrayList<>();
    }

    /**
     * 根据组订单编号查询
     *
     * @param groupIdSet 1
     * @return
     */
    @Override
    public List<ShoppingOrder> listByGroupIdSet(Set<String> groupIdSet) {
        if (Sets.isNotEmpty(groupIdSet)) {
            return shoppingOrderMapper.listByGroupIdSet(groupIdSet);
        }
        return new ArrayList<>();
    }

    /**
     * 根据组订单编号查询-忽略长字段，加快查询速度
     *
     * @param groupIdSet 1
     * @return
     */
    @Override
    public List<ShoppingOrder> listByGroupIdSetFilterLongField(Set<String> groupIdSet) {
        if (Sets.isNotEmpty(groupIdSet)) {
            return shoppingOrderMapper.listByGroupIdSetFilterLongField(groupIdSet);
        }
        return new ArrayList<>();
    }

    /**
     * 根据订单编号集合查询订单集合
     *
     * @param orderNoSet
     * @return
     */
    @Override
    public List<ShoppingOrder> listByOrderNoSet(Set<String> orderNoSet) {
        if (Sets.isNotEmpty(orderNoSet)) {
            return shoppingOrderMapper.listByOrderNoSet(orderNoSet);
        }
        return new ArrayList<>();
    }

    /**
     * 根据订单编号查询-忽略长字段，加快查询速度
     *
     * @param orderNoSet 1
     * @return
     */
    @Override
    public List<ShoppingOrder> listByOrderNoSetFilterLongField(Set<String> orderNoSet) {
        if (Sets.isNotEmpty(orderNoSet)) {
            return shoppingOrderMapper.listByOrderNoSetFilterLongField(orderNoSet);
        }
        return new ArrayList<>();
    }

    /**
     * 根据商家组订单编号查询
     *
     * @param shopGroupIdSet
     * @return
     */
    @Override
    public List<ShoppingOrder> listByShopGroupIdSet(Set<String> shopGroupIdSet) {
        if (Sets.isNotEmpty(shopGroupIdSet)) {
            return shoppingOrderMapper.listByShopGroupIdSet(shopGroupIdSet);
        }
        return new ArrayList<>();
    }

    /**
     * 根据商家组订单编号查询
     *
     * @param shopGroupIdSet
     * @return
     */
    @Override
    public List<ShoppingOrder> listByShopGroupIdSetFilterLongField(Set<String> shopGroupIdSet) {
        if (Sets.isNotEmpty(shopGroupIdSet)) {
            return shoppingOrderMapper.listByShopGroupIdSetFilterLongField(shopGroupIdSet);
        }
        return new ArrayList<>();
    }

}
