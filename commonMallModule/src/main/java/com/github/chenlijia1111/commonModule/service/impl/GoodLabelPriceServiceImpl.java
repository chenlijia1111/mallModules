package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import tk.mybatis.mapper.entity.Example;
import com.github.chenlijia1111.commonModule.entity.GoodLabelPrice;
import com.github.chenlijia1111.commonModule.dao.GoodLabelPriceMapper;
import com.github.chenlijia1111.commonModule.service.GoodLabelPriceServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

/**
 * 商品标签价
 * @author chenLiJia
 * @since 2020-12-04 09:28:29
 **/
@Service
public class GoodLabelPriceServiceImpl implements GoodLabelPriceServiceI {


    @Resource
    private GoodLabelPriceMapper goodLabelPriceMapper;


    /**
     * 添加
     *
     * @param params      添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-12-04 09:28:29
     **/
    @Override
    public Result add(GoodLabelPrice params){

        int i = goodLabelPriceMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params      编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-12-04 09:28:29
     **/
    @Override
    public Result update(GoodLabelPrice params){

        int i = goodLabelPriceMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 按条件编辑
     * @param entity
     * @param condition
     * @return
     */
    @Override
    public Result update(GoodLabelPrice entity, Example condition) {
        if(Objects.nonNull(entity) && Objects.nonNull(condition)){
            int i = goodLabelPriceMapper.updateByExampleSelective(entity, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @since 2020-12-04 09:28:29
     **/
    @Override
    public List<GoodLabelPrice> listByCondition(GoodLabelPrice condition){

        PropertyCheckUtil.transferObjectNotNull(condition, true);
        return goodLabelPriceMapper.select(condition);
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @since 2020-12-04 09:28:29
     **/
    @Override
    public List<GoodLabelPrice> listByCondition(Example condition){

        if (null != condition) {
           List<GoodLabelPrice> list = goodLabelPriceMapper.selectByExample(condition);
           return list;
        }
        return new ArrayList<>();
    }

    /**
     * 查询商品标签价格
     *
     * @param goodIdSet 商品id集合
     * @param labelName 标签名称
     * @return
     */
    @Override
    public List<GoodLabelPrice> listByGoodIdSet(Set<String> goodIdSet, String labelName) {
        if (Sets.isNotEmpty(goodIdSet)) {
            return goodLabelPriceMapper.listByGoodIdSet(goodIdSet, labelName);
        }
        return new ArrayList<>();
    }

    /**
     * 批量添加
     *
     * @param list
     */
    @Override
    public void batchAdd(List<GoodLabelPrice> list) {
        if (Lists.isNotEmpty(list)) {
            goodLabelPriceMapper.insertList(list);
        }
    }

    /**
     * 删除产品下的所有商品标签价格
     *
     * @param productId
     */
    @Override
    public void deleteByProductId(String productId) {
        if (StringUtils.isNotEmpty(productId)) {
            goodLabelPriceMapper.deleteByProductId(productId);
        }
    }

}
