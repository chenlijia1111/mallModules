package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.dao.ShopGroupOrderMapper;
import com.github.chenlijia1111.commonModule.entity.ShopGroupOrder;
import com.github.chenlijia1111.commonModule.service.ShopGroupOrderServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 商家组订单
 *
 * @author chenLiJia
 * @since 2021-01-20 09:37:25
 **/
@Service
public class ShopGroupOrderServiceImpl implements ShopGroupOrderServiceI {


    @Resource
    private ShopGroupOrderMapper shopGroupOrderMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2021-01-20 09:37:25
     **/
    @Override
    public Result add(ShopGroupOrder params) {

        int i = shopGroupOrderMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2021-01-20 09:37:25
     **/
    @Override
    public Result update(ShopGroupOrder params) {

        int i = shopGroupOrderMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 按条件编辑
     *
     * @param entity
     * @param condition
     * @return
     */
    @Override
    public Result update(ShopGroupOrder entity, Example condition) {
        if (Objects.nonNull(entity) && Objects.nonNull(condition)) {
            int i = shopGroupOrderMapper.updateByExampleSelective(entity, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @since 2021-01-20 09:37:25
     **/
    @Override
    public List<ShopGroupOrder> listByCondition(ShopGroupOrder condition) {

        PropertyCheckUtil.transferObjectNotNull(condition, true);
        return shopGroupOrderMapper.select(condition);
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @since 2021-01-20 09:37:25
     **/
    @Override
    public List<ShopGroupOrder> listByCondition(Example condition) {

        if (null != condition) {
            List<ShopGroupOrder> list = shopGroupOrderMapper.selectByExample(condition);
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * 批量添加
     *
     * @param list
     */
    @Override
    public void batchAdd(List<ShopGroupOrder> list) {
        if (Lists.isNotEmpty(list)) {
            shopGroupOrderMapper.insertList(list);
        }
    }
}
