package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.responseVo.product.*;
import com.github.chenlijia1111.commonModule.dao.*;
import com.github.chenlijia1111.commonModule.entity.Goods;
import com.github.chenlijia1111.commonModule.entity.Product;
import com.github.chenlijia1111.commonModule.entity.ProductSpecValue;
import com.github.chenlijia1111.commonModule.service.ProductServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 产品表
 *
 * @author chenLiJia
 * @since 2019-11-01 13:46:54
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "ProductServiceI")
public class ProductServiceImpl implements ProductServiceI {


    @Resource
    private ProductMapper productMapper;//产品
    @Resource
    private GoodsMapper goodsMapper;//商品
    @Resource
    private ProductSpecMapper productSpecMapper;
    @Resource
    private ProductSpecValueMapper productSpecValueMapper;//产品规格值
    @Resource
    private GoodSpecMapper goodSpecMapper;//商品规格
    @Resource
    private ShoppingOrderMapper shoppingOrderMapper;//订单


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 13:46:54
     **/
    public Result add(Product params) {

        int i = productMapper.insertSelective(params);
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
    public Result update(Product params) {

        int i = productMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     */
    @Override
    public List<Product> listByCondition(Product condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return productMapper.select(condition);
    }

    /**
     * 条件统计数量
     *
     * @param condition
     * @return
     */
    @Override
    public Integer countByCondition(Product condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        Integer count = productMapper.selectCount(condition);
        count = Objects.isNull(count) ? 0 : count;
        return count;
    }

    /**
     * 通过产品id查询产品信息
     *
     * @param productId 1
     * @return com.github.chenlijia1111.commonModule.entity.Product
     * @since 下午 5:17 2019/11/5 0005
     **/
    @Override
    public Product findByProductId(String productId) {
        return StringUtils.isEmpty(productId) ? null : productMapper.selectByPrimaryKey(productId);
    }

    /**
     * 根据产品id集合查询产品信息
     *
     * @param productIdSet
     * @return
     */
    @Override
    public List<Product> listByProductIdSet(Set<String> productIdSet) {
        if (Sets.isNotEmpty(productIdSet)) {
            return productMapper.listByProductIdSet(productIdSet);
        }
        return new ArrayList<>();
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     */
    @Override
    public List<Product> listByCondition(Example condition) {
        if (Objects.nonNull(condition)) {
            return productMapper.selectByExample(condition);
        }
        return new ArrayList<>();
    }

    /**
     * 按条件统计
     *
     * @param condition
     * @return
     */
    @Override
    public Integer countByCondition(Example condition) {
        if (Objects.nonNull(condition)) {
            return productMapper.selectCountByExample(condition);
        }
        return 0;
    }

    /**
     * 按条件修改
     *
     * @param product
     * @param condition
     * @return
     */
    @Override
    public Result update(Product product, Example condition) {
        if (Objects.nonNull(product) && Objects.nonNull(condition)) {
            int i = productMapper.updateByExampleSelective(product, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }

    /**
     * 查询产品详细信息
     *
     * @param productId 1
     * @return com.github.chenlijia1111.commonModule.common.responseVo.product.AdminProductVo
     * @since 下午 7:36 2019/11/7 0007
     **/
    @Override
    public AdminProductVo findAdminProductVoByProductId(String productId) {

        if (StringUtils.isNotEmpty(productId)) {
            Product product = productMapper.selectByPrimaryKey(productId);
            if (Objects.nonNull(product) && Objects.equals(product.getDeleteStatus(), BooleanConstant.NO_INTEGER)) {
                AdminProductVo productVo = new AdminProductVo();
                BeanUtils.copyProperties(product, productVo);

                //查询产品的规格信息
                List<ProductSpecVo> productSpecVos = productSpecMapper.listProductSpecVoByProductIdSet(Sets.asSets(productId));
                //查询这些产品规格所对应的值
                Set<Integer> productSpecIdSet = productSpecVos.stream().map(e -> e.getId()).collect(Collectors.toSet());
                List<ProductSpecValue> productSpecValueList = productSpecValueMapper.listBySpecIdSet(productSpecIdSet);
                //关联规格值数据
                for (ProductSpecVo specVo : productSpecVos) {
                    List<ProductSpecValue> collect = productSpecValueList.stream().filter(e -> Objects.equals(e.getProductSpecId(), specVo.getId())).collect(Collectors.toList());
                    specVo.setProductSpecValueList(collect);
                }
                //产品的规格值
                productVo.setProductSpecVoList(productSpecVos);

                //商品信息
                List<GoodVo> goodVoList = goodsMapper.listByProductIdSet(Sets.asSets(productId));
                //商品id集合
                Set<String> goodIdSet = goodVoList.stream().map(e -> e.getId()).collect(Collectors.toSet());
                //关联商品的规格值
                List<GoodSpecVo> goodSpecVos = goodSpecMapper.listGoodSpecVoByGoodIdSet(goodIdSet);
                for (GoodVo goodVo : goodVoList) {
                    List<GoodSpecVo> collect = goodSpecVos.stream().filter(e -> Objects.equals(e.getGoodId(), goodVo.getId())).collect(Collectors.toList());
                    goodVo.setGoodSpecVoList(collect);
                }
                //产品的商品列表
                productVo.setGoodVoList(goodVoList);

                return productVo;
            }
        }
        return null;
    }

    /**
     * 获取后台商品详细细信息
     *
     * @param list 1
     * @return void
     * @since 上午 9:40 2019/11/4 0004
     **/
    private void getAdminProductFullInfo(List<AdminProductListVo> list) {
        if (Lists.isNotEmpty(list)) {
            Set<String> productIdSet = list.stream().map(e -> e.getId()).collect(Collectors.toSet());
            List<GoodVo> goods = goodsMapper.listByProductIdSet(productIdSet);
            for (AdminProductListVo vo : list) {
                List<Goods> collect = goods.stream().filter(e -> Objects.equals(e.getProductId(), vo.getId())).collect(Collectors.toList());
                //查询最小市场价以及最大市场价
                Optional<Double> minMarketPriceOption = collect.stream().map(e -> e.getMarketPrice()).min(Comparator.comparing(e -> e));
                Optional<Double> maxMarketPriceOption = collect.stream().map(e -> e.getMarketPrice()).max(Comparator.comparing(e -> e));
                if (minMarketPriceOption.isPresent() && maxMarketPriceOption.isPresent()) {
                    Double minMarketPrice = minMarketPriceOption.get();
                    Double maxMarketPrice = maxMarketPriceOption.get();
                    vo.setMarketPriceRange(Objects.equals(minMarketPrice, maxMarketPrice) ? (minMarketPrice + "") : (minMarketPrice + "-" + maxMarketPrice));
                }

                //查询最小售价以及最大售价
                Optional<Double> minPriceOption = collect.stream().map(e -> e.getPrice()).min(Comparator.comparing(e -> e));
                Optional<Double> maxPriceOption = collect.stream().map(e -> e.getPrice()).max(Comparator.comparing(e -> e));
                if (minPriceOption.isPresent() && maxPriceOption.isPresent()) {
                    Double minPrice = minPriceOption.get();
                    Double maxPrice = maxPriceOption.get();
                    vo.setPriceRange(Objects.equals(minPrice, maxPrice) ? (minPrice + "") : (minPrice + "-" + maxPrice));
                }

                //查询最小会员价以及最大会员价
                Optional<Double> minVIPPriceOption = collect.stream().map(e -> e.getVipPrice()).min(Comparator.comparing(e -> e));
                Optional<Double> maxVIPPriceOption = collect.stream().map(e -> e.getVipPrice()).max(Comparator.comparing(e -> e));
                if (minVIPPriceOption.isPresent() && maxVIPPriceOption.isPresent()) {
                    Double minVIPPrice = minVIPPriceOption.get();
                    Double maxVIPPrice = maxVIPPriceOption.get();
                    vo.setVipPriceRange(Objects.equals(minVIPPrice, maxVIPPrice) ? (minVIPPrice + "") : (minVIPPrice + "-" + maxVIPPrice));
                }
            }
        }
    }


}
