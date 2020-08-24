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
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 产品表
 * <p>
 * 2020-08-07 优化根据产品id查询产品信息，通过 groupBy 分组，在进行赋值，利用 map 减少每次查询对应的数据的时间
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
                Map<Integer, List<ProductSpecValue>> productSpecValueMap = productSpecValueList.stream().collect(Collectors.groupingBy(e -> e.getProductSpecId()));
                //关联规格值数据
                for (ProductSpecVo specVo : productSpecVos) {
                    List<ProductSpecValue> collect = productSpecValueMap.get(specVo.getId());
                    specVo.setProductSpecValueList(collect);
                }
                //产品的规格值
                productVo.setProductSpecVoList(productSpecVos);

                //商品信息
                List<GoodVo> goodVoList = goodsMapper.listByProductIdSet(Sets.asSets(productId));
                //关联商品的规格值
                List<GoodSpecVo> goodSpecVos = goodSpecMapper.listGoodSpecVoByProductIdSet(Sets.asSets(productId));
                Map<String, List<GoodSpecVo>> goodSpecVoMap = goodSpecVos.stream().collect(Collectors.groupingBy(e -> e.getGoodId()));
                for (GoodVo goodVo : goodVoList) {
                    List<GoodSpecVo> collect = goodSpecVoMap.get(goodVo.getId());
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
     * 根据产品id集合查询产品详细信息
     *
     * @param productIdSet
     * @return
     */
    @Override
    public List<AdminProductVo> listAdminProductVoByProductIdSet(Set<String> productIdSet) {

        ArrayList<AdminProductVo> list = new ArrayList<>();

        if (Sets.isNotEmpty(productIdSet)) {
            List<Product> productList = productMapper.listByProductIdSet(productIdSet);

            //查询产品的规格信息
            List<ProductSpecVo> productSpecVos = productSpecMapper.listProductSpecVoByProductIdSet(productIdSet);
            //查询这些产品规格所对应的值
            Set<Integer> productSpecIdSet = productSpecVos.stream().map(e -> e.getId()).collect(Collectors.toSet());
            List<ProductSpecValue> productSpecValueList = productSpecValueMapper.listBySpecIdSet(productSpecIdSet);
            Map<Integer, List<ProductSpecValue>> productSpecValueMap = productSpecValueList.stream().collect(Collectors.groupingBy(e -> e.getProductSpecId()));
            //关联规格值数据
            for (ProductSpecVo specVo : productSpecVos) {
                List<ProductSpecValue> collect = productSpecValueMap.get(specVo.getId());
                specVo.setProductSpecValueList(collect);
            }

            //商品信息
            List<GoodVo> goodVoList = goodsMapper.listByProductIdSet(productIdSet);
            //关联商品的规格值
            List<GoodSpecVo> goodSpecVos = goodSpecMapper.listGoodSpecVoByProductIdSet(productIdSet);
            Map<String, List<GoodSpecVo>> goodSpecVoMap = goodSpecVos.stream().collect(Collectors.groupingBy(e -> e.getGoodId()));
            for (GoodVo goodVo : goodVoList) {
                List<GoodSpecVo> hitGoodSpecVoList = goodSpecVoMap.get(goodVo.getId());
                goodVo.setGoodSpecVoList(hitGoodSpecVoList);
            }

            Map<String, List<ProductSpecVo>> productSpecVoMap = productSpecVos.stream().collect(Collectors.groupingBy(e -> e.getProductId()));
            Map<String, List<GoodVo>> goodVoMap = goodVoList.stream().collect(Collectors.groupingBy(e -> e.getProductId()));

            //构建数据
            for (String productId : productIdSet) {
                Product product = productList.stream().filter(e -> Objects.equals(productId, e.getId())).findAny().orElse(null);
                if (Objects.nonNull(product)) {
                    //构建数据
                    AdminProductVo productVo = new AdminProductVo();
                    BeanUtils.copyProperties(product, productVo);
                    //产品的规格值
                    List<ProductSpecVo> currentProductProductSpecVoList = productSpecVoMap.get(productId);
                    productVo.setProductSpecVoList(currentProductProductSpecVoList);
                    //产品的商品列表
                    List<GoodVo> currentProductGoodVoList = goodVoMap.get(productId);
                    productVo.setGoodVoList(currentProductGoodVoList);
                    list.add(productVo);
                }
            }
        }
        return list;
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
                Optional<BigDecimal> minMarketPriceOption = collect.stream().map(e -> e.getMarketPrice()).min(BigDecimal::compareTo);
                Optional<BigDecimal> maxMarketPriceOption = collect.stream().map(e -> e.getMarketPrice()).max(BigDecimal::compareTo);
                if (minMarketPriceOption.isPresent() && maxMarketPriceOption.isPresent()) {
                    BigDecimal minMarketPrice = minMarketPriceOption.get();
                    BigDecimal maxMarketPrice = maxMarketPriceOption.get();
                    vo.setMarketPriceRange(Objects.equals(minMarketPrice, maxMarketPrice) ? (minMarketPrice + "") : (minMarketPrice + "-" + maxMarketPrice));
                }

                //查询最小售价以及最大售价
                Optional<BigDecimal> minPriceOption = collect.stream().map(e -> e.getPrice()).min(BigDecimal::compareTo);
                Optional<BigDecimal> maxPriceOption = collect.stream().map(e -> e.getPrice()).max(BigDecimal::compareTo);
                if (minPriceOption.isPresent() && maxPriceOption.isPresent()) {
                    BigDecimal minPrice = minPriceOption.get();
                    BigDecimal maxPrice = maxPriceOption.get();
                    vo.setPriceRange(Objects.equals(minPrice, maxPrice) ? (minPrice + "") : (minPrice + "-" + maxPrice));
                }

                //查询最小会员价以及最大会员价
                Optional<BigDecimal> minVIPPriceOption = collect.stream().map(e -> e.getVipPrice()).min(BigDecimal::compareTo);
                Optional<BigDecimal> maxVIPPriceOption = collect.stream().map(e -> e.getVipPrice()).max(BigDecimal::compareTo);
                if (minVIPPriceOption.isPresent() && maxVIPPriceOption.isPresent()) {
                    BigDecimal minVIPPrice = minVIPPriceOption.get();
                    BigDecimal maxVIPPrice = maxVIPPriceOption.get();
                    vo.setVipPriceRange(Objects.equals(minVIPPrice, maxVIPPrice) ? (minVIPPrice + "") : (minVIPPrice + "-" + maxVIPPrice));
                }
            }
        }
    }

    /**
     * 通过商品id查询商家id集合
     *
     * @param goodIdSet
     * @return
     */
    @Override
    public Set<String> listShopIdSetByGoodIdSet(Set<String> goodIdSet) {
        if (Sets.isNotEmpty(goodIdSet)) {
            return productMapper.listShopIdSetByGoodIdSet(goodIdSet);
        }
        return new HashSet<>();
    }
}
