package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import com.github.chenlijia1111.commonModule.dao.ReceivingGoodsOrderMapper;
import com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder;
import com.github.chenlijia1111.commonModule.service.ReceivingGoodsOrderServiceI;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 收货单
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "ReceivingGoodsOrderServiceI")
public class ReceivingGoodsOrderServiceImpl implements ReceivingGoodsOrderServiceI {


    @Resource
    private ReceivingGoodsOrderMapper receivingGoodsOrderMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result add(ReceivingGoodsOrder params) {

        int i = receivingGoodsOrderMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 批量添加
     *
     * @param receivingGoodsOrderList
     * @return
     */
    @Override
    public Result batchAdd(List<ReceivingGoodsOrder> receivingGoodsOrderList) {
        if (Lists.isEmpty(receivingGoodsOrderList)) {
            return Result.failure("数据为空");
        }

        int i = receivingGoodsOrderMapper.batchAdd(receivingGoodsOrderList);
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
    public Result update(ReceivingGoodsOrder params) {

        int i = receivingGoodsOrderMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 根据前一个单号集合查询收货单集合
     *
     * @param frontNoSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder>
     * @since 上午 10:00 2019/11/8 0008
     **/
    @Override
    public List<ReceivingGoodsOrder> listByFrontNoSet(Set<String> frontNoSet) {

        if (Sets.isNotEmpty(frontNoSet)) {
            return receivingGoodsOrderMapper.listByFrontOrderNoSet(frontNoSet);
        }

        return Lists.newArrayList();
    }

    /**
     * 根据售后订单删除收货单
     *
     * @param returnNo
     */
    @Override
    public void deleteByReturnNo(String returnNo) {
        if (StringUtils.isNotEmpty(returnNo)) {
            receivingGoodsOrderMapper.deleteByReturnNo(returnNo);
        }
    }

    /**
     * 修改
     * @param receivingGoodsOrder
     * @param condition
     * @return
     */
    @Override
    public Result update(ReceivingGoodsOrder receivingGoodsOrder, Example condition) {
        if(Objects.nonNull(receivingGoodsOrder) && Objects.nonNull(condition)){
            int i = receivingGoodsOrderMapper.updateByExampleSelective(receivingGoodsOrder, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }
}
