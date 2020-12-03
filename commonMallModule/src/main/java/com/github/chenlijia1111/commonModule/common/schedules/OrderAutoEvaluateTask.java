package com.github.chenlijia1111.commonModule.common.schedules;

import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.dao.EvaluationMapper;
import com.github.chenlijia1111.commonModule.dao.ProductSnapshotMapper;
import com.github.chenlijia1111.commonModule.dao.ShoppingOrderMapper;
import com.github.chenlijia1111.commonModule.entity.Evaluation;
import com.github.chenlijia1111.commonModule.entity.ProductSnapshot;
import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 订单隔指定时间自动评价
 * <p>
 * 一般情况都是根据订单收货时间开始 间隔一定时间 自动好评
 * <p>
 * 如果要使用的话就把他注入到spring中去
 * 在项目启动时，已经进行查询了待支付的订单进延时队列了，调用者不用自己实现，
 * 我会判断项目是否注入了这个实例，如果注入了才会去查询
 *
 * @author Chen LiJia
 * @since 2020/5/15
 */
public class OrderAutoEvaluateTask implements IOrderAutoTask {

    @Resource
    private ShoppingOrderMapper shoppingOrderMapper;
    @Resource
    private EvaluationMapper evaluationMapper;//评价
    @Resource
    private ProductSnapshotMapper productSnapshotMapper;// 产品快照

    /**
     * 默认好评内容
     */
    private String defaultEvaluateComment = "此用户未填写评价内容";

    /**
     * 好评级别 星级
     */
    private Double defaultEvaluateLevel = 5.0;


    //处理自动收货逻辑
    @Override
    public void autoDealDelayOrder(OrderDelayPojo pojo) {
        if (Objects.equals(pojo.getOrderAutoTaskClass(), this.getClass())) {
            //需要当前实现类处理的数据
            String orderNo = pojo.getOrderNo();
            //查询是否已评价
            List<Evaluation> evaluationList = evaluationMapper.listByOrderNoSet(Sets.asSets(orderNo));
            if (Lists.isEmpty(evaluationList)) {
                //还没有评价，默认好评
                //查询订单信息
                ShoppingOrder shoppingOrder = shoppingOrderMapper.selectByPrimaryKey(orderNo);
                if (Objects.nonNull(shoppingOrder)) {
                    //查询产品id
                    String productSnapshotId = shoppingOrder.getDetailsJson();
                    Optional<AdminProductVo> adminProductVoOptional = Optional.empty();
                    if (StringUtils.isInt(productSnapshotId)) {
                        ProductSnapshot productSnapshot = productSnapshotMapper.selectByPrimaryKey(Integer.valueOf(productSnapshotId));
                        if (Objects.nonNull(productSnapshot)) {
                            AdminProductVo adminProductVo = JSONUtil.strToObj(productSnapshot.getProductJson(), AdminProductVo.class);
                            adminProductVoOptional = Optional.ofNullable(adminProductVo);
                        }
                    }
                    //添加评价
                    String evaluateId = String.valueOf(IDGenerateFactory.EVALUATION_ID_UTIL.nextId());
                    Evaluation evaluation = new Evaluation().setId(evaluateId).
                            setClientId(shoppingOrder.getCustom()).
                            setShopId(shoppingOrder.getShops()).
                            setOrderNo(shoppingOrder.getOrderNo()).
                            setGoodId(shoppingOrder.getGoodsId()).
                            setProductId(adminProductVoOptional.map(e -> e.getId()).orElse(null)).
                            setComment(defaultEvaluateComment).
                            setProductLevel(defaultEvaluateLevel).
                            setShopLevel(defaultEvaluateLevel).
                            setServiceLevel(defaultEvaluateLevel).
                            setExpressLevel(defaultEvaluateLevel).
                            setCreateTime(new Date()).
                            setDeleteStatus(BooleanConstant.NO_INTEGER);
                    evaluationMapper.insertSelective(evaluation);
                    //评价之后，订单状态修改已完成
                    shoppingOrder.setCompleteStatus(BooleanConstant.YES_INTEGER);
                    shoppingOrderMapper.updateByPrimaryKeySelective(shoppingOrder);
                }

            }

        }
    }

}
