package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import com.github.chenlijia1111.commonModule.dao.ImmediatePaymentOrderMapper;
import com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder;
import com.github.chenlijia1111.commonModule.service.ImmediatePaymentOrderServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 发货单
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
     * @param params      添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result add(ImmediatePaymentOrder params){
    
        int i = immediatePaymentOrderMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params      编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result update(ImmediatePaymentOrder params){
    
        int i = immediatePaymentOrderMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 通过前一个订单集合查询发货单集合
     * @since 上午 9:40 2019/11/8 0008
     * @param frontNoSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ImmediatePaymentOrder>
     **/
    @Override
    public List<ImmediatePaymentOrder> listByFrontNoSet(Set<String> frontNoSet) {
        if(Sets.isNotEmpty(frontNoSet)){
            return immediatePaymentOrderMapper.listByFrontOrderNoSet(frontNoSet);
        }
        return Lists.newArrayList();
    }


}
