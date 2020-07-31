package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.dao.ImmediatePaymentOrderMapper;
import com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder;
import com.github.chenlijia1111.commonModule.service.ImmediatePaymentOrderServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 发货单
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "ImmediatePaymentOrderServiceI")
public class ImmediatePaymentOrderServiceImpl implements ImmediatePaymentOrderServiceI {


    @Resource
    private ImmediatePaymentOrderMapper immediatePaymentOrderMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result add(ImmediatePaymentOrder params) {

        int i = immediatePaymentOrderMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 批量添加发货单
     *
     * @param immediatePaymentOrderList
     * @return
     */
    @Override
    public Result batchAdd(List<ImmediatePaymentOrder> immediatePaymentOrderList) {
        if (Lists.isEmpty(immediatePaymentOrderList)) {
            return Result.failure("数据为空");
        }

        Integer i = immediatePaymentOrderMapper.batchAdd(immediatePaymentOrderList);
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
    public Result update(ImmediatePaymentOrder params) {

        int i = immediatePaymentOrderMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 通过前一个订单集合查询发货单集合
     *
     * @param frontNoSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder>
     * @since 上午 9:40 2019/11/8 0008
     **/
    @Override
    public List<ImmediatePaymentOrder> listByFrontNoSet(Set<String> frontNoSet) {
        if (Sets.isNotEmpty(frontNoSet)) {
            return immediatePaymentOrderMapper.listByFrontOrderNoSet(frontNoSet);
        }
        return Lists.newArrayList();
    }

    /**
     * 删除售后的发货单
     *
     * @param returnOrderNo
     */
    @Override
    public void deleteByReturnOrderNoSet(String returnOrderNo) {
        if (StringUtils.isNotEmpty(returnOrderNo)) {
            immediatePaymentOrderMapper.deleteByExample(Example.builder(ImmediatePaymentOrder.class).
                    where(Sqls.custom().andEqualTo("frontOrder", returnOrderNo)).build());
        }
    }

    /**
     * 查询已经发货了还没有签收的发货单
     * @return
     */
    @Override
    public List<ImmediatePaymentOrder> listNotReceiveSendOrder() {
        return immediatePaymentOrderMapper.listNotReceiveSendOrder();
    }

    /**
     * 修改
     * @param immediatePaymentOrder
     * @param condition
     * @return
     */
    @Override
    public Result update(ImmediatePaymentOrder immediatePaymentOrder, Example condition) {
        if(Objects.nonNull(immediatePaymentOrder) && Objects.nonNull(condition)){
            int i = immediatePaymentOrderMapper.updateByExampleSelective(immediatePaymentOrder, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }
}
