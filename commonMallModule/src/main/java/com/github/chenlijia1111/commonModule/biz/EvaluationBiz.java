package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.requestVo.evaluation.AddEvaluationParams;
import com.github.chenlijia1111.commonModule.common.requestVo.evaluation.QueryParams;
import com.github.chenlijia1111.commonModule.common.responseVo.evaluate.EvaluateListVo;
import com.github.chenlijia1111.commonModule.common.responseVo.evaluation.LabelCountVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.common.responseVo.user.CommonSimpleUser;
import com.github.chenlijia1111.commonModule.entity.Evaluation;
import com.github.chenlijia1111.commonModule.entity.EvaluationLabel;
import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.commonModule.service.CommonModuleUserServiceI;
import com.github.chenlijia1111.commonModule.service.EvaluationLabelServiceI;
import com.github.chenlijia1111.commonModule.service.EvaluationServiceI;
import com.github.chenlijia1111.commonModule.service.ShoppingOrderServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.database.mybatis.pojo.PageInfo;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 评价表
 * TODO
 * 将要修改的东西
 * 1.5新增是否启用状态 后台可以设置前端可以看到的评论
 *
 * @author chenLiJia
 * @since 2019-11-25 13:45:34
 **/
@Service
public class EvaluationBiz {


    @Autowired
    private EvaluationServiceI evaluationService;//评价
    @Autowired
    private EvaluationLabelServiceI evaluationLabelService;//评价标签
    @Resource
    private CommonModuleUserServiceI userService;//用户
    @Autowired
    private ShoppingOrderServiceI shoppingOrderService;//订单


    /**
     * 添加评价
     * 返回评价Id
     *
     * @param params           1
     * @param repeatEvaluation 是否可重复评价
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 1:58 2019/11/25 0025
     **/
    public Result addEvaluation(AddEvaluationParams params, boolean repeatEvaluation) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //当前用户id
        String currentUserId = userService.currentUserId();
        if (StringUtils.isEmpty(currentUserId)) {
            return Result.notLogin();
        }

        //查询订单
        ShoppingOrder order = shoppingOrderService.findByOrderNo(params.getOrderNo());
        if (Objects.isNull(order)) {
            return Result.failure("订单不存在");
        }

        //判断是否是当前用户的订单,防止评价其他人的订单
        if (!Objects.equals(order.getCustom(), currentUserId)) {
            return Result.failure("操作不合法,无法评价其他用户的订单");
        }

        //如果是追评,判断父评价是否存在
        if (StringUtils.isNotEmpty(params.getParentEvalua())) {
            Evaluation parentEvaluation = evaluationService.findById(params.getParentEvalua());
            if (Objects.isNull(parentEvaluation) || Objects.equals(BooleanConstant.YES_INTEGER, parentEvaluation.getDeleteStatus())) {
                return Result.failure("父评价不存在");
            }
        }
        //判断是否允许重复评价
        if (!repeatEvaluation) {
            //查询该订单的评价数量
            Evaluation evaluationCondition = new Evaluation().setOrderNo(params.getOrderNo());
            Integer evaluationCount = evaluationService.countByCondition(evaluationCondition);
            if (Objects.nonNull(evaluationCount) && evaluationCount > 0) {
                return Result.failure("该订单已评价,无法重复评价");
            }
        }

        //订单快照
        String detailsJson = order.getDetailsJson();
        AdminProductVo adminProductVo = JSONUtil.strToObj(detailsJson, AdminProductVo.class);

        //添加评价数据
        //评价id
        String evaluationId = String.valueOf(IDGenerateFactory.EVALUATION_ID_UTIL.nextId());
        Evaluation evaluation = new Evaluation().setId(evaluationId).
                setClientId(order.getCustom()).
                setShopId(order.getShops()).
                setOrderNo(order.getOrderNo()).
                setGoodId(order.getGoodsId()).
                setProductId(adminProductVo.getId()).
                setComment(params.getComment()).
                setImages(params.getImages()).
                setProductLevel(params.getProductLevel()).
                setShopLevel(params.getShopLevel()).
                setServiceLevel(params.getServiceLevel()).
                setExpressLevel(params.getExpressLevel()).
                setParentEvalua(params.getParentEvalua()).
                setCreateTime(new Date()).
                setDeleteStatus(BooleanConstant.NO_INTEGER).
                setOpenStatus(BooleanConstant.YES_INTEGER);

        evaluationService.add(evaluation);

        //评价标签
        List<String> labels = params.getLabels();
        if (Lists.isNotEmpty(labels)) {
            List<EvaluationLabel> evaluationLabels = labels.stream().map(e -> {
                EvaluationLabel evaluationLabel = new EvaluationLabel().
                        setId(String.valueOf(IDGenerateFactory.EVALUATION_LABEL_ID_UTIL.nextId())).
                        setEvaluaId(evaluationId).
                        setLabelName(e);
                return evaluationLabel;
            }).collect(Collectors.toList());
            evaluationLabelService.batchAdd(evaluationLabels);
        }

        //评价之后，订单修改为已完成
        if (!Objects.equals(order.getCompleteStatus(), BooleanConstant.YES_INTEGER)) {
            order.setCompleteStatus(BooleanConstant.YES_INTEGER);
            shoppingOrderService.update(order);
        }

        return Result.success("操作成功", evaluationId);
    }

    /**
     * 根据产品Id查询评价信息
     *
     * @param params
     * @return
     */
    public Result listPage(QueryParams params) {


        List<EvaluateListVo> list = evaluationService.listPage(params);
        PageInfo<EvaluateListVo> pageInfo = new PageInfo<>(list);

        //关联用户信息
        if (Lists.isNotEmpty(list)) {
            Set<String> userIdSet = list.stream().map(e -> e.getClientId()).collect(Collectors.toSet());
            List<CommonSimpleUser> commonSimpleUsers = userService.listCommonSimpleUserByIdSet(userIdSet);
            if (Lists.isNotEmpty(commonSimpleUsers)) {
                for (int i = 0; i < list.size(); i++) {
                    EvaluateListVo vo = list.get(i);
                    Optional<CommonSimpleUser> commonSimpleUserOptional = commonSimpleUsers.stream().filter(e -> Objects.equals(e.getId(), vo.getClientId())).findAny();
                    if (commonSimpleUserOptional.isPresent()) {
                        CommonSimpleUser simpleUser = commonSimpleUserOptional.get();
                        vo.setUserNickName(simpleUser.getNickName());
                        vo.setUserHeadImage(simpleUser.getHeadImage());
                    }
                }
            }
        }
        return Result.success("查询成功", pageInfo);
    }

    /**
     * 统计评价标签数量
     *
     * @param productId
     * @return
     */
    public Result labelCount(String productId) {

        if (StringUtils.isEmpty(productId)) {
            return Result.failure("产品id为空");
        }

        List<LabelCountVo> labelCountVos = evaluationLabelService.listLabelCountVo(productId);
        return Result.success("查询成功", labelCountVos);
    }

}
