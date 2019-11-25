package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.dao.ReturnGoodsOrderMapper;
import com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder;
import com.github.chenlijia1111.commonModule.service.ReturnGoodsOrderServiceI;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 退货单
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "ReturnGoodsOrderServiceI")
public class ReturnGoodsOrderServiceImpl implements ReturnGoodsOrderServiceI {


    @Resource
    private ReturnGoodsOrderMapper returnGoodsOrderMapper;


    /**
     * 添加
     *
     * @param params      添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result add(ReturnGoodsOrder params){
    
        int i = returnGoodsOrderMapper.insertSelective(params);
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
    public Result update(ReturnGoodsOrder params){
    
        int i = returnGoodsOrderMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询退货单列表
     * @since 上午 11:19 2019/11/25 0025
     * @param condition 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder>
     **/
    @Override
    public List<ReturnGoodsOrder> listByCondition(ReturnGoodsOrder condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition,true);
        return returnGoodsOrderMapper.select(condition);
    }

    /**
     * 通过退货单单号查询退货单
     * @since 上午 11:20 2019/11/25 0025
     * @param returnNo 退货单单号
     * @return com.github.chenlijia1111.commonModule.entity.ReturnGoodsOrder
     **/
    @Override
    public ReturnGoodsOrder findByReturnNo(String returnNo) {
        if(StringUtils.isNotEmpty(returnNo)){
            return returnGoodsOrderMapper.selectByPrimaryKey(returnNo);
        }
        return null;
    }


}
