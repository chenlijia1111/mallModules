package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodSpecVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.commonModule.dao.GoodSpecMapper;
import com.github.chenlijia1111.commonModule.dao.GoodsMapper;
import com.github.chenlijia1111.commonModule.entity.Goods;
import com.github.chenlijia1111.commonModule.service.GoodsServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商品表
 * <p>
 * 2020-08-07 优化根据产品id查询产品信息，通过 groupBy 分组，在进行赋值，利用 map 减少每次查询对应的数据的时间
 *
 * @author chenLiJia
 * @since 2019-11-01 13:46:54
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "GoodsServiceI")
public class GoodsServiceImpl implements GoodsServiceI {


    @Resource
    private GoodsMapper goodsMapper;//商品
    @Resource
    private GoodSpecMapper goodSpecMapper;//商品规格


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 13:46:54
     **/
    public Result add(Goods params) {

        int i = goodsMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 批量添加商品
     *
     * @param goodsList
     * @return
     */
    @Override
    public Result batchAdd(List<Goods> goodsList) {
        if (Lists.isEmpty(goodsList)) {
            return Result.failure("商品列表为空");
        }

        int i = goodsMapper.batchAdd(goodsList);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 13:46:54
     **/
    public Result update(Goods params) {

        int i = goodsMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询商品列表
     *
     * @param condition
     * @return
     */
    @Override
    public List<GoodVo> listByCondition(Goods condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        List<Goods> goods = goodsMapper.select(condition);
        if (Lists.isNotEmpty(goods)) {

            List<GoodVo> goodVoList = goods.stream().map(e -> {
                GoodVo goodVo = new GoodVo();
                BeanUtils.copyProperties(e, goodVo);
                return goodVo;
            }).collect(Collectors.toList());
            //获取商品规格信息
            getFullInfo(goodVoList);
            return goodVoList;
        }
        return Lists.newArrayList();
    }

    /**
     * 批量删除商品
     *
     * @param goodIdSet 商品id集合
     * @return
     */
    @Override
    public Result batchDelete(Set<String> goodIdSet) {
        if (Sets.isNotEmpty(goodIdSet)) {
            goodsMapper.batchDelete(goodIdSet);
        }
        return Result.success("操作成功");
    }


    /**
     * 通过产品id集合查询商品列表
     *
     * @param productIdSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo>
     * @since 上午 9:58 2019/11/5 0005
     **/
    @Override
    public List<GoodVo> listByProductIdSet(Set<String> productIdSet) {

        if (Sets.isNotEmpty(productIdSet)) {
            List<GoodVo> goodVoList = goodsMapper.listByProductIdSet(productIdSet);
            getFullInfo(goodVoList);
            return goodVoList;
        }

        return Lists.newArrayList();
    }

    /**
     * 通过商品id查询商品
     *
     * @param goodId 1
     * @return com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo
     * @since 下午 5:11 2019/11/5 0005
     **/
    @Override
    public GoodVo findByGoodId(String goodId) {

        if (StringUtils.isNotEmpty(goodId)) {
            Goods goods = goodsMapper.selectByPrimaryKey(goodId);
            if (Objects.nonNull(goods)) {
                GoodVo goodVo = new GoodVo();
                BeanUtils.copyProperties(goods, goodVo);

                getFullInfo(Lists.asList(goodVo));
                return goodVo;
            }

        }
        return null;
    }

    /**
     * 根据商品id集合查询商品集合
     *
     * @param goodIdSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo>
     * @since 上午 11:04 2019/11/22 0022
     **/
    @Override
    public List<GoodVo> listByGoodIdSet(Set<String> goodIdSet) {
        if (Sets.isNotEmpty(goodIdSet)) {
            List<GoodVo> goodVoList = goodsMapper.listByGoodIdSet(goodIdSet);
            //关联商品的规格值
            getFullInfo(goodVoList);
            return goodVoList;
        }

        return Lists.newArrayList();
    }

    /**
     * 获取商品规格详细信息
     *
     * @param list
     */
    private void getFullInfo(List<GoodVo> list) {
        if (Lists.isNotEmpty(list)) {
            //商品id集合
            Set<String> productIdSet = list.stream().map(e -> e.getProductId()).collect(Collectors.toSet());
            List<GoodSpecVo> goodSpecVos = goodSpecMapper.listGoodSpecVoByProductIdSet(productIdSet);
            Map<String, List<GoodSpecVo>> goodSpecVoMap = goodSpecVos.stream().collect(Collectors.groupingBy(e -> e.getGoodId()));
            list.stream().forEach(e -> e.setGoodSpecVoList(goodSpecVoMap.get(e.getId())));
        }
    }


}
