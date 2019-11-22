package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import com.github.chenlijia1111.commonModule.dao.ReceivingGoodsOrderMapper;
import com.github.chenlijia1111.commonModule.entity.ReceivingGoodsOrder;
import com.github.chenlijia1111.commonModule.service.ReceivingGoodsOrderServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 收货单
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@Service
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


}
