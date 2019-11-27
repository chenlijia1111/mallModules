package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.commonModule.entity.ClientAddress;
import com.github.chenlijia1111.commonModule.dao.ClientAddressMapper;
import com.github.chenlijia1111.commonModule.service.ClientAddressServiceI;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 用户地址信息表
 * @author chenLiJia
 * @since 2019-11-27 10:37:42
 **/
@Service
public class ClientAddressServiceImpl implements ClientAddressServiceI {


    @Resource
    private ClientAddressMapper clientAddressMapper;


    /**
     * 添加
     *
     * @param params      添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-27 10:37:42
     **/
    public Result add(ClientAddress params){
    
        int i = clientAddressMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params      编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-27 10:37:42
     **/
    public Result update(ClientAddress params){
    
        int i = clientAddressMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


    /**
     * 将某个用户的收货地址全部改为非默认地址
     *
     * @param clientId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenlijia
     * @since 下午 12:59 2019/8/15 0015
     **/
    @Override
    public Result updateClientCommonAddressByClient(String clientId) {
        if (Objects.isNull(clientId)) {
            return Result.failure("用户id为空");
        }

        clientAddressMapper.updateClientCommonAddressByClient(clientId);
        return Result.success("操作成功");
    }

    /**
     * 条件查询用户收货地址
     * @since 下午 2:14 2019/11/5 0005
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.ClientAddress>
     **/
    @Override
    public List<ClientAddress> listByCondition(ClientAddress condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition,true);
        return clientAddressMapper.select(condition);
    }

    /**
     * 主键查询
     * @since 上午 11:10 2019/11/19 0019
     * @param id 1
     * @return com.logicalthining.endeshop.entity.ClientAddress
     **/
    @Override
    public ClientAddress findById(Integer id) {
        if(Objects.nonNull(id)){
            return clientAddressMapper.selectByPrimaryKey(id);
        }
        return null;
    }


}
