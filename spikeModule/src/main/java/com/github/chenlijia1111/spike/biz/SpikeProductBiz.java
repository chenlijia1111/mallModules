package com.github.chenlijia1111.spike.biz;

import com.github.chenlijia1111.commonModule.common.enums.ProductSnapshotTypeEnum;
import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo;
import com.github.chenlijia1111.commonModule.entity.ProductSnapshot;
import com.github.chenlijia1111.commonModule.service.ProductServiceI;
import com.github.chenlijia1111.commonModule.service.ProductSnapshotServiceI;
import com.github.chenlijia1111.spike.common.requestVo.spikeProduct.SpikeProductAddParams;
import com.github.chenlijia1111.spike.common.response.product.SpikeAdminProductVo;
import com.github.chenlijia1111.spike.entity.SpikeProduct;
import com.github.chenlijia1111.spike.service.SpikeProductServiceI;
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
 * 商品参与秒杀
 * 一般对于秒杀来说,同一个商品应该是不可以出现在同一个时间段的
 * <p>
 * 可能有些对于秒杀商品的加入还需要审核等额外操作,这些需要调用者定制处理
 * 建一个关联表进行关联
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/25 0025 下午 4:16
 **/
@Service
public class SpikeProductBiz {

    @Autowired
    private SpikeProductServiceI spikeProductService;//商品参与秒杀记录表
    @Autowired
    private ProductServiceI productService;// 产品 //
    @Autowired
    private ProductSnapshotServiceI productSnapshotService;// 产品快照

    /**
     * 产品批量加入秒杀
     *
     * @param list            1
     * @param repeatWithRange 是否允许在同一时间段重复加入秒杀
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:15 2019/11/25 0025
     **/
    @Transactional
    public Result batchAdd(List<SpikeProductAddParams> list, boolean repeatWithRange) {

        //校验参数
        if (Lists.isEmpty(list)) {
            return Result.failure("参数为空");
        }
        for (SpikeProductAddParams params : list) {
            Result result = PropertyCheckUtil.checkProperty(params);
            if (!result.getSuccess()) {
                return result;
            }
        }

        //开始添加
        for (SpikeProductAddParams params : list) {
            Result add = add(params, repeatWithRange);
            if (!add.getSuccess()) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return add;
            }
        }

        return Result.success("操作成功");
    }

    /**
     * 产品加入秒杀
     *
     * @param params          1
     * @param repeatWithRange 是否允许在同一时间段重复加入秒杀
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:02 2019/11/26 0026
     **/
    public Result add(SpikeProductAddParams params, boolean repeatWithRange) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断是否可以在同一时间段重复加入秒杀
        if (!repeatWithRange) {
            SpikeProduct spikeProduct = spikeProductService.findByTimeRange(new DateTime(params.getStartTime().getTime()).toString(TimeConstant.DATE_TIME),
                    new DateTime(params.getEndTime().getTime()).toString(TimeConstant.DATE_TIME), params.getProductId(), params.getGoodId());
            if (Objects.nonNull(spikeProduct)) {
                return Result.failure("操作失败,该商品在该时间段已参加了秒杀,秒杀时间段重复");
            }
        }

        //加入
        SpikeProduct spikeProduct = new SpikeProduct().
                setId(String.valueOf(IDGenerateFactory.SPIKE_PRODUCT_ID_UTIL.nextId())).
                setProductId(params.getProductId()).
                setGoodId(params.getGoodId()).
                setStartTime(params.getStartTime()).
                setEndTime(params.getEndTime()).
                setSpikePrice(params.getSpikePrice()).
                setTotalStockCount(params.getStockCount()).
                setStockCount(params.getStockCount()).
                setCreateTime(new Date()).
                setUpdateTime(new Date()).
                setUpdateVersion(RandomUtil.createUUID()).
                setOrderNumber(params.getOrderNumber()).
                setDeleteStatus(BooleanConstant.NO_INTEGER);

        result = spikeProductService.add(spikeProduct);

        if (result.getSuccess()) {
            //秒杀订单产品快照
            AdminProductVo adminProductVo = productService.findAdminProductVoByProductId(params.getProductId());
            SpikeAdminProductVo spikeAdminProductVo = new SpikeAdminProductVo();
            BeanUtils.copyProperties(adminProductVo, spikeAdminProductVo);
            //参与场次开始时间
            spikeAdminProductVo.setSpikeStartTime(spikeProduct.getStartTime());
            //参与场次结束时间
            spikeAdminProductVo.setSpikeEndTime(spikeProduct.getEndTime());
            //秒杀价格
            spikeAdminProductVo.setSpikePrice(spikeProduct.getSpikePrice());
            String productSnapshotStr = JSONUtil.objToStr(spikeAdminProductVo);

            ProductSnapshot productSnapshot = new ProductSnapshot(params.getProductId(), ProductSnapshotTypeEnum.SPIKE.getType(), productSnapshotStr, new Date());
            productSnapshotService.add(productSnapshot);
        }

        return result;
    }
}
