package com.github.chenlijia1111.fightGroup.biz;

import com.github.chenlijia1111.commonModule.common.enums.ProductSnapshotTypeEnum;
import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.entity.ProductSnapshot;
import com.github.chenlijia1111.commonModule.service.ProductServiceI;
import com.github.chenlijia1111.commonModule.service.ProductSnapshotServiceI;
import com.github.chenlijia1111.fightGroup.common.requestVo.fightGroupProduct.FightGroupProductAddParams;
import com.github.chenlijia1111.fightGroup.common.response.product.FightGroupAdminProductVo;
import com.github.chenlijia1111.fightGroup.entity.FightGroupProduct;
import com.github.chenlijia1111.fightGroup.service.FightGroupProductServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.common.constant.TimeConstant;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.RandomUtil;
import com.github.chenlijia1111.utils.list.Lists;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 商品参与拼团
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/26 0026 下午 1:36
 **/
@Service
public class FightGroupProductBiz {


    @Autowired
    private FightGroupProductServiceI fightGroupProductService;//拼团商品
    @Autowired
    private ProductServiceI productService;// 产品
    @Autowired
    private ProductSnapshotServiceI productSnapshotService;// 产品快照


    /**
     * 商品批量加入拼团
     *
     * @param list            1
     * @param repeatWithRange 是否允许在同一时间段重复加入拼团
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:16 2019/11/26 0026
     **/
    @Transactional
    public Result batchAdd(List<FightGroupProductAddParams> list, boolean repeatWithRange) {

        //校验参数
        if (Lists.isEmpty(list)) {
            return Result.failure("参数为空");
        }
        for (FightGroupProductAddParams productAddParams : list) {
            Result result = PropertyCheckUtil.checkProperty(productAddParams);
            if (!result.getSuccess()) {
                return result;
            }
        }

        for (FightGroupProductAddParams productAddParams : list) {
            Result result = add(productAddParams, repeatWithRange);
            if (!result.getSuccess()) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return result;
            }
        }
        return Result.success("操作成功");
    }

    /**
     * 商品参与拼团
     *
     * @param params          1
     * @param repeatWithRange 是否允许在同一时间段重复加入拼团
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:14 2019/11/26 0026
     **/
    public Result add(FightGroupProductAddParams params, boolean repeatWithRange) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }
        //成团人数必须大于2
        if (params.getGroupPersonCount() < 2) {
            return Result.failure("成团人数必须大于等于2");
        }

        //判断是否允许在同一时间段重复加入拼团
        if (!repeatWithRange) {
            FightGroupProduct fightGroupProduct = fightGroupProductService.findByTimeRange(new DateTime(params.getStartTime().getTime()).toString(TimeConstant.DATE_TIME),
                    new DateTime(params.getEndTime().getTime()).toString(TimeConstant.DATE_TIME), params.getProductId(), params.getGoodId());
            if (Objects.nonNull(fightGroupProduct)) {
                return Result.failure("操作失败,该商品在该时间段已参加了拼团,拼团时间段重复");
            }
        }

        //加入
        FightGroupProduct fightGroupProduct = new FightGroupProduct().
                setId(String.valueOf(IDGenerateFactory.FIGHT_GROUP_PRODUCT_ID_UTIL.nextId())).
                setProductId(params.getProductId()).
                setGoodId(params.getGoodId()).
                setStartTime(params.getStartTime()).
                setEndTime(params.getEndTime()).
                setFightPrice(params.getFightPrice()).
                setTotalStockCount(params.getTotalStockCount()).
                setStockCount(params.getTotalStockCount()).
                setGroupPersonCount(params.getGroupPersonCount()).
                setPersonLimitCount(params.getPersonLimitCount()).
                setMaxFightTime(params.getMaxFightTime()).
                setSortNumber(params.getSortNumber()).
                setCreateTime(new Date()).
                setUpdateTime(new Date()).
                setUpdateVersion(RandomUtil.createUUID()).
                setDeleteStatus(BooleanConstant.NO_INTEGER);

        result = fightGroupProductService.add(fightGroupProduct);
        if (result.getSuccess()) {
            // 添加拼团产品缓存
            //订单快照
            AdminProductVo adminProductVo = productService.findAdminProductVoByProductId(params.getProductId());
            FightGroupAdminProductVo fightGroupAdminProductVo = new FightGroupAdminProductVo();
            BeanUtils.copyProperties(adminProductVo, fightGroupAdminProductVo);
            //拼团开始时间
            fightGroupAdminProductVo.setFightStartTime(fightGroupProduct.getStartTime());
            //拼团结束时间
            fightGroupAdminProductVo.setFightEndTime(fightGroupProduct.getEndTime());
            //拼团价格
            fightGroupAdminProductVo.setFightPrice(fightGroupProduct.getFightPrice());
            //成团人数
            fightGroupAdminProductVo.setGroupPersonCount(fightGroupProduct.getGroupPersonCount());
            //每人限购数量
            fightGroupAdminProductVo.setFightGroupPersonLimitCount(fightGroupProduct.getPersonLimitCount());
            //最大拼团时间(分钟),超过自动解散
            fightGroupAdminProductVo.setMaxFightTime(fightGroupProduct.getMaxFightTime());
            //转json字符串
            String productSnapshotStr = JSONUtil.objToStr(fightGroupAdminProductVo);

            ProductSnapshot productSnapshot = new ProductSnapshot(params.getProductId(), ProductSnapshotTypeEnum.FIGHT_GROUP.getType(), productSnapshotStr, new Date());
            productSnapshotService.add(productSnapshot);
        }
        return result;
    }

}
