package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.pojo.IDGenerateFactory;
import com.github.chenlijia1111.commonModule.common.requestVo.product.*;
import com.github.chenlijia1111.commonModule.common.responseVo.product.*;
import com.github.chenlijia1111.commonModule.entity.*;
import com.github.chenlijia1111.commonModule.service.*;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 产品表
 *
 * @author chenLiJia
 * @since 2019-11-01 13:46:54
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "ProductBiz")
public class ProductBiz {

    /**
     * 更新产品信息的时候
     * 跟新商品信息
     * 是否需要忽略 goodNo
     * 默认否 不忽略
     * 为什么会有这个需求呢
     * 有时候这个goodNo 是后台生成的，但是前端传递数据的时候不好处理哪些是变化的了商品信息
     * 哪些是没变化的商品信息，并且还要去做一系列的匹配操作等等
     * 所以这里给一个字段，编辑的时候 goodNo 就跟 goodId 一样，不会变
     */
    public static Integer UPDATE_PRODUCT_IGNORE_GOOD_NO = BooleanConstant.NO_INTEGER;

    @Autowired
    private ProductServiceI productService;//产品
    @Autowired
    private GoodsServiceI goodsService;//商品
    @Autowired
    private ProductSpecServiceI productSpecService;//产品规格名称
    @Autowired
    private ProductSpecValueServiceI productSpecValueService;//产品规格值
    @Autowired
    private GoodSpecServiceI goodSpecService;//商品规格
    @Resource
    private CommonModuleShopServiceI shopService;//商家

    /**
     * 添加商品
     *
     * @param params                    1
     * @param productIdGeneratorService 产品id生成器
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:18 2019/11/1 0001
     **/
    @Transactional
    public Result add(ProductAddParams params, IdGeneratorServiceI productIdGeneratorService) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }
        //校验商品
        List<GoodAddParams> goodList = params.getGoodList();
        for (GoodAddParams goodAddParams : goodList) {
            result = PropertyCheckUtil.checkProperty(goodAddParams);
            if (!result.getSuccess()) {
                return result;
            }
        }

        //当前商家id,为了适配有些系统可能不需要商家这个角色，直接就是后台添加商品,所以这里不做限制
        String shopId = shopService.currentShopId();
        //当前时间
        Date currentTime = new Date();

        //产品id
        String productId = productIdGeneratorService.createOrderNo();
        Product product = new Product().setId(productId).
                setName(params.getName()).
                setCategoryId(params.getCategoryId()).
                setBrand(params.getBrand()).
                setUnit(params.getUnit()).
                setSortNumber(params.getSortNumber()).
                setSmallPic(params.getSmallPic()).
                setContent(params.getContent()).
                setDescription(params.getDescription()).
                setShelfStatus(params.getShelfStatus()).
                setCreateTime(currentTime).
                setUpdateTime(currentTime).
                setDeleteStatus(BooleanConstant.NO_INTEGER).
                setShops(shopId);

        //添加产品
        productService.add(product);

        //所有产品规格记录,用于后面添加商品关联id
        List<ProductSpec> allProductSpecRecode = Lists.newArrayList();
        List<ProductSpecValue> allProductSpecValueRecode = Lists.newArrayList();

        //添加产品规格
        List<ProductSpecParams> productSpecParamsList = params.getProductSpecParamsList();
        for (ProductSpecParams specParams : productSpecParamsList) {
            ProductSpec productSpec = new ProductSpec().setProductId(productId).setSpecName(specParams.getSpecName());
            productSpecService.add(productSpec);
            allProductSpecRecode.add(productSpec);

            //添加产品规格值
            List<ProductSpecValueParams> specValueList = specParams.getSpecValueList();
            List<ProductSpecValue> productSpecValueList = specValueList.stream().map(e -> new ProductSpecValue().
                    setProductSpecId(productSpec.getId()).
                    setSpecValueImage(e.getImageValue()).
                    setSpecValue(e.getValue())).collect(Collectors.toList());
            productSpecValueService.batchAdd(productSpecValueList);
            allProductSpecValueRecode.addAll(productSpecValueList);
        }

        //添加商品
        //构造批量插入商品 商品规格数据
        List<Goods> goodsList = new ArrayList<>();
        List<GoodSpec> goodSpecList = new ArrayList<>();
        for (GoodAddParams goodAddParams : goodList) {
            //商品id
            String goodId = String.valueOf(IDGenerateFactory.GOOD_ID_UTIL.nextId());
            Goods goods = new Goods().setId(goodId).
                    setProductId(productId).
                    setPrice(goodAddParams.getPrice()).
                    setVipPrice(goodAddParams.getVipPrice()).
                    setMarketPrice(goodAddParams.getMarketPrice()).
                    setGoodNo(goodAddParams.getGoodNo()).
                    setCreateTime(currentTime).
                    setUpdateTime(currentTime).
                    setDeleteStatus(BooleanConstant.NO_INTEGER).
                    setStockCount(goodAddParams.getStockCount()).
                    setGoodImage(goodAddParams.getGoodImage());

            if (Objects.equals(BooleanConstant.YES_INTEGER, UPDATE_PRODUCT_IGNORE_GOOD_NO)) {
                //忽略 goodNo
                goods.setGoodNo(null);
            }
            goodsList.add(goods);

            //添加这个商品的规格属性
            List<GoodSpecParams> goodSpecParamsList = goodAddParams.getGoodSpecParamsList();
            for (GoodSpecParams goodSpecParams : goodSpecParamsList) {
                //查询这个规格所对应的的前面的产品规格值的id  比较名字相同的就是
                Optional<ProductSpec> any = allProductSpecRecode.stream().filter(e -> Objects.equals(e.getSpecName(), goodSpecParams.getSpecName())).findAny();
                if (any.isPresent()) {
                    ProductSpec productSpec = any.get();
                    Optional<ProductSpecValue> any1 = allProductSpecValueRecode.stream().filter(e -> Objects.equals(e.getProductSpecId(), productSpec.getId()) &&
                            Objects.equals(e.getSpecValueImage(), goodSpecParams.getSpecImageValue()) &&
                            Objects.equals(e.getSpecValue(), goodSpecParams.getSpecValue())).findAny();
                    if (any1.isPresent()) {
                        ProductSpecValue productSpecValue = any1.get();
                        GoodSpec goodSpec = new GoodSpec().setGoodId(goodId).setSpecValueId(productSpecValue.getId());
                        goodSpecList.add(goodSpec);
                    }
                }
            }
        }

        //批量插入商品 商品规格
        goodsService.batchAdd(goodsList);
        goodSpecService.batchAdd(goodSpecList);

        return Result.success("操作成功", productId);
    }

    /**
     * 修改商品
     * 需要判断是不是把某个商品规格删除了
     * 直接通过规格名字去比对
     * <p>
     * 规格可以先删除再加，
     * 但是商品因为是关联到订单的，
     * 所以不可以贸贸然的随便删，
     * 需要对比确实这个商品不存在了再删,
     * 而且是逻辑删除
     * <p>
     * 通过规格确定一个唯一的sku
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:18 2019/11/1 0001
     **/
    @Transactional
    public Result update(ProductUpdateParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkPropertyWithIgnore(params, Lists.asList("content"));
        if (!result.getSuccess()) {
            return result;
        }

        //判断商品是否存在
        Product productCondition = new Product().setId(params.getId()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<Product> products = productService.listByCondition(productCondition);
        if (Lists.isEmpty(products)) {
            return Result.failure("数据不存在");
        }

        //当前时间
        Date currentTime = new Date();

        //产品id
        Product product = new Product().setId(params.getId()).
                setName(params.getName()).
                setCategoryId(params.getCategoryId()).
                setBrand(params.getBrand()).
                setUnit(params.getUnit()).
                setSortNumber(params.getSortNumber()).
                setSmallPic(params.getSmallPic()).
                setContent(params.getContent()).
                setDescription(params.getDescription()).
                setShelfStatus(params.getShelfStatus()).
                setUpdateTime(currentTime).
                setDeleteStatus(BooleanConstant.NO_INTEGER);

        //添加产品
        productService.update(product);

        //查询出这个产品原先的所有商品
        Goods goodCondition = new Goods().setProductId(params.getId()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<GoodVo> listByCondition = goodsService.listByCondition(goodCondition);

        //这是前端传递过来修改的商品id集合,那么没传过来的就是要删除的了
        List<String> updatedGoodIdList = Lists.newArrayList();

        //先删除这个产品所有的规格记录，这个删除必须要在查询出这个产品原先的所有商品之后，不然就查不到规格了
        productSpecService.deleteByProductId(params.getId());
        //删除所有商品的规格值,后面直接重新添加这些商品的规格值
        goodSpecService.deleteByProductId(params.getId());

        //所有产品规格记录,用于后面添加商品关联id
        List<ProductSpec> allProductSpecRecode = Lists.newArrayList();
        List<ProductSpecValue> allProductSpecValueRecode = Lists.newArrayList();

        //添加产品规格
        List<ProductSpecParams> productSpecParamsList = params.getProductSpecParamsList();
        for (ProductSpecParams specParams : productSpecParamsList) {
            ProductSpec productSpec = new ProductSpec().setProductId(params.getId()).setSpecName(specParams.getSpecName());
            productSpecService.add(productSpec);
            allProductSpecRecode.add(productSpec);

            //添加产品规格值
            List<ProductSpecValueParams> specValueList = specParams.getSpecValueList();
            List<ProductSpecValue> productSpecValueList = specValueList.stream().map(e -> new ProductSpecValue().
                    setProductSpecId(productSpec.getId()).
                    setSpecValueImage(e.getImageValue()).
                    setSpecValue(e.getValue())).collect(Collectors.toList());
            productSpecValueService.batchAdd(productSpecValueList);
            allProductSpecValueRecode.addAll(productSpecValueList);
        }

        //添加商品
        //添加参数里的商品集合
        List<GoodAddParams> goodList = params.getGoodList();
        //商品规格集合,用于批量添加  减少时间消耗
        List<GoodSpec> batchAddGoodSpecList = new ArrayList<>();

        for (GoodAddParams goodAddParams : goodList) {
            //看能不能找到匹配的商品信息，找到了，就用更新操作，没找到就用添加操作
            GoodVo goodVo = findMatchGoodVo(goodAddParams.transferToReleaseProductSkuVo(), listByCondition);
            Goods goods = null;
            if (Objects.nonNull(goodVo)) {

                //前端传过来的商品id
                updatedGoodIdList.add(goodVo.getId());
                //存在
                //做变更操作
                //商品id
                String goodId = goodVo.getId();
                goods = new Goods().setId(goodId).
                        setProductId(params.getId()).
                        setPrice(goodAddParams.getPrice()).
                        setVipPrice(goodAddParams.getVipPrice()).
                        setMarketPrice(goodAddParams.getMarketPrice()).
                        setGoodNo(goodAddParams.getGoodNo()).
                        setUpdateTime(currentTime).
                        setStockCount(goodAddParams.getStockCount()).
                        setGoodImage(goodAddParams.getGoodImage());

                goodsService.update(goods);

                updatedGoodIdList.add(goodId);
            } else {
                //新增
                //商品id
                String goodId = IDGenerateFactory.GOOD_ID_UTIL.nextId() + "";
                goods = new Goods().setId(goodId).
                        setProductId(params.getId()).
                        setPrice(goodAddParams.getPrice()).
                        setVipPrice(goodAddParams.getVipPrice()).
                        setMarketPrice(goodAddParams.getMarketPrice()).
                        setGoodNo(goodAddParams.getGoodNo()).
                        setCreateTime(currentTime).
                        setUpdateTime(currentTime).
                        setDeleteStatus(BooleanConstant.NO_INTEGER).
                        setStockCount(goodAddParams.getStockCount()).
                        setGoodImage(goodAddParams.getGoodImage());

                goodsService.add(goods);
            }


            //添加这个商品的规格属性
            List<GoodSpecParams> goodSpecParamsList = goodAddParams.getGoodSpecParamsList();
            for (GoodSpecParams goodSpecParams : goodSpecParamsList) {
                //查询这个规格所对应的的前面的产品规格值的id  比较名字相同的就是
                Optional<ProductSpec> any = allProductSpecRecode.stream().filter(e -> Objects.equals(e.getSpecName(), goodSpecParams.getSpecName())).findAny();
                if (any.isPresent()) {
                    ProductSpec productSpec = any.get();
                    Optional<ProductSpecValue> any1 = allProductSpecValueRecode.stream().filter(e -> Objects.equals(e.getProductSpecId(), productSpec.getId()) &&
                            StringUtils.equalsIgnoreBlank(e.getSpecValueImage(), goodSpecParams.getSpecImageValue()) &&
                            StringUtils.equalsIgnoreBlank(e.getSpecValue(), goodSpecParams.getSpecValue())).findAny();
                    if (any1.isPresent()) {
                        ProductSpecValue productSpecValue = any1.get();
                        GoodSpec goodSpec = new GoodSpec().setGoodId(goods.getId()).setSpecValueId(productSpecValue.getId());
                        batchAddGoodSpecList.add(goodSpec);
                    }
                }
            }
        }

        //批量添加商品规格
        if (Lists.isNotEmpty(batchAddGoodSpecList)) {
            goodSpecService.batchAdd(batchAddGoodSpecList);
        }

        //判断那些前端删除了的商品,删掉
        List<GoodVo> deletedGoodList = listByCondition.stream().filter(e -> !updatedGoodIdList.contains(e.getId())).collect(Collectors.toList());
        Set<String> deletedGoodIdSet = deletedGoodList.stream().map(e -> e.getId()).collect(Collectors.toSet());
        goodsService.batchDelete(deletedGoodIdSet);

        return Result.success("操作成功");
    }

    /**
     * 删除商品
     *
     * @param productId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:19 2019/11/1 0001
     **/
    @Transactional
    public Result delete(String productId) {

        if (StringUtils.isEmpty(productId)) {
            return Result.failure("产品id为空");
        }

        //判断数据是否存在
        Product productCondition = new Product().setId(productId).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<Product> products = productService.listByCondition(productCondition);
        if (Lists.isEmpty(products)) {
            return Result.failure("数据不存在");
        }

        //删除产品
        Product deleteProduct = new Product().setId(productId).setDeleteStatus(BooleanConstant.YES_INTEGER);
        productService.update(deleteProduct);

        //删除商品
        //查询出这个产品原先的所有商品
        Goods goodCondition = new Goods().setProductId(productId).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<GoodVo> listByCondition = goodsService.listByCondition(goodCondition);
        Set<String> deleteGoodIdSet = listByCondition.stream().map(e -> e.getId()).collect(Collectors.toSet());
        goodsService.batchDelete(deleteGoodIdSet);

        return Result.success("操作成功");
    }


    /**
     * 修改上架状态
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:20 2019/11/1 0001
     **/
    public Result updateShelfStatus(UpdateShelfStatusParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断数据是否存在
        Product productCondition = new Product().setId(params.getProductId()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<Product> products = productService.listByCondition(productCondition);
        if (Lists.isEmpty(products)) {
            return Result.failure("数据不存在");
        }

        Product product = new Product().setId(params.getProductId()).setShelfStatus(params.getState());
        return productService.update(product);
    }


    /**
     * 编辑排序值
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:27 2019/11/4 0004
     **/
    public Result updateSortNumber(UpdateSortNumberParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        Product product = new Product().setId(params.getId()).setSortNumber(params.getSortNumber());

        return productService.update(product);
    }


    /**
     * 通过产品Id查询商品详情
     *
     * @param productId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:21 2019/11/5 0005
     **/
    public Result findByProductId(String productId) {

        //校验参数
        if (StringUtils.isEmpty(productId)) {
            return Result.failure("产品Id不存在");
        }

        AdminProductVo productVo = productService.findAdminProductVoByProductId(productId);

        return Objects.isNull(productVo) ? Result.failure("产品信息不存在") : Result.success("查询成功", productVo);
    }


    /**
     * 批量修改产品上下架状态
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:50 2019/11/13 0013
     **/
    @Transactional
    public Result batchUpdateShelfStatus(BatchUpdateProductShelfStatusParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        List<String> idList = params.getIdList();
        for (String id : idList) {
            UpdateShelfStatusParams statusParams = new UpdateShelfStatusParams();
            statusParams.setProductId(id);
            statusParams.setState(params.getState());
            result = updateShelfStatus(statusParams);
            if (!result.getSuccess()) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return result;
            }
        }

        return Result.success("操作成功");
    }

    /**
     * 批量删除产品
     *
     * @param productIdList 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:50 2019/11/13 0013
     **/
    @Transactional
    public Result batchDelete(List<String> productIdList) {

        //校验参数
        if (Lists.isEmpty(productIdList)) {
            return Result.failure("产品id为空");
        }

        for (String id : productIdList) {
            Result delete = delete(id);
            if (!delete.getSuccess()) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return delete;
            }
        }

        return Result.success("操作成功");
    }

    //-------------------------------------为前端构建规格信息 V1------------------------------------

    /**
     * 添加产品时根据产品规格信息构建sku
     * 供前端调用
     *
     * @param productSpecParamsList
     * @return
     */
    public Result releaseProductSkuVo(List<ProductSpecParams> productSpecParamsList) {

        //校验一下数据
        if (Lists.isEmpty(productSpecParamsList)) {
            return Result.failure("产品参数为空");
        }
        for (ProductSpecParams productSpecParams : productSpecParamsList) {
            String specName = productSpecParams.getSpecName();
            if (StringUtils.isEmpty(specName)) {
                return Result.failure("规格名称为空");
            }
            List<ProductSpecValueParams> specValueList = productSpecParams.getSpecValueList();
            if (Lists.isEmpty(specValueList)) {
                return Result.failure("规格值数组为空");
            }
        }

        List<List<ReleaseProductSkuSpecVo>> list = releaseProductSkuSpecVo(productSpecParamsList);

        //构建返回数据
        List<ReleaseProductSkuVo> skuVoList = new ArrayList<>();
        for (List<ReleaseProductSkuSpecVo> releaseProductSkuSpecVos : list) {
            ReleaseProductSkuVo vo = new ReleaseProductSkuVo();
            vo.setSkuSpecVos(releaseProductSkuSpecVos);
            //价格库存再创建商品的时候默认为无
            skuVoList.add(vo);
        }

        return Result.success("查询成功", skuVoList);
    }


    /**
     * 根据产品规格为前端构建sku信息
     *
     * @param productSpecParamsList
     * @return
     */
    private List<List<ReleaseProductSkuSpecVo>> releaseProductSkuSpecVo(List<ProductSpecParams> productSpecParamsList) {

        List<List<ReleaseProductSkuSpecVo>> list = new ArrayList<>();

        if (Lists.isNotEmpty(productSpecParamsList)) {
            //开始构建

            for (int i = 0; i < productSpecParamsList.size(); i++) {

                ProductSpecParams productSpecParams = productSpecParamsList.get(i);
                String specName = productSpecParams.getSpecName();
                List<ProductSpecValueParams> specValueList = productSpecParams.getSpecValueList();

                if (i == 0) {
                    for (int j = 0; j < specValueList.size(); j++) {
                        //第一个规格,直接创建
                        ProductSpecValueParams specValueParams = specValueList.get(j);
                        List<ReleaseProductSkuSpecVo> releaseProductSkuSpecVos = new ArrayList<>();
                        ReleaseProductSkuSpecVo releaseProductSkuSpecVo = releaseReleaseProductSkuSpecVo(specName, specValueParams);
                        releaseProductSkuSpecVos.add(releaseProductSkuSpecVo);
                        list.add(releaseProductSkuSpecVos);

                    }
                } else {
                    //不是第一个就直接在直接的基础上拼接即可
                    List<List<ReleaseProductSkuSpecVo>> copyList = list.stream().collect(Collectors.toList());
                    list.clear();

                    for (List<ReleaseProductSkuSpecVo> skuSpecVoList : copyList) {
                        for (int j = 0; j < specValueList.size(); j++) {
                            ProductSpecValueParams specValueParams = specValueList.get(j);
                            ReleaseProductSkuSpecVo releaseProductSkuSpecVo = releaseReleaseProductSkuSpecVo(specName, specValueParams);
                            List<ReleaseProductSkuSpecVo> collect = skuSpecVoList.stream().collect(Collectors.toList());
                            collect.add(releaseProductSkuSpecVo);
                            list.add(collect);

                        }
                    }

                }
            }
            return list;
        }

        return list;
    }


    /**
     * 构建sku单个属性信息
     *
     * @param specName
     * @param productSpecValueParams
     * @return
     */
    private ReleaseProductSkuSpecVo releaseReleaseProductSkuSpecVo(String specName, ProductSpecValueParams productSpecValueParams) {
        ReleaseProductSkuSpecVo skuSpecVo = new ReleaseProductSkuSpecVo();
        skuSpecVo.setSpecName(specName);
        skuSpecVo.setValue(productSpecValueParams.getValue());
        skuSpecVo.setImageValue(productSpecValueParams.getImageValue());
        return skuSpecVo;
    }

    /**
     * 修改产品时根据产品规格信息构建sku
     * 供前端调用
     *
     * @param params
     * @return
     */
    public Result releaseUpdateProductSkuVo(ReleaseUpdateProductSkuParams params) {
        List<ProductSpecParams> productSpecParamsList = params.getProductSpecParamsList();

        //校验一下数据
        if (Objects.isNull(params.getProductId())) {
            return Result.failure("产品id为空");
        }
        if (Lists.isEmpty(productSpecParamsList)) {
            return Result.failure("产品参数为空");
        }
        for (ProductSpecParams productSpecParams : productSpecParamsList) {
            String specName = productSpecParams.getSpecName();
            if (StringUtils.isEmpty(specName)) {
                return Result.failure("规格名称为空");
            }
            List<ProductSpecValueParams> specValueList = productSpecParams.getSpecValueList();
            if (Lists.isEmpty(specValueList)) {
                return Result.failure("规格值数组为空");
            }
        }

        List<List<ReleaseProductSkuSpecVo>> list = releaseProductSkuSpecVo(productSpecParamsList);
        //转化对象
        List<ReleaseProductSkuVo> copyList = list.stream().map(e -> {
            ReleaseProductSkuVo vo = new ReleaseProductSkuVo();
            vo.setSkuSpecVos(e);
            return vo;
        }).collect(Collectors.toList());

        //查询这个商品信息
        AdminProductVo adminProductVo = productService.findAdminProductVoByProductId(params.getProductId());
        List<GoodVo> goodVoList = adminProductVo.getGoodVoList();

        for (ReleaseProductSkuVo releaseProductSkuVo : copyList) {
            GoodVo matchGoodVo = findMatchGoodVo(releaseProductSkuVo, goodVoList);
            if (Objects.nonNull(matchGoodVo)) {
                //找到了对应的商品 赋值价格 库存
                releaseProductSkuVo.setPrice(matchGoodVo.getPrice());
                releaseProductSkuVo.setVipPrice(matchGoodVo.getVipPrice());
                releaseProductSkuVo.setMarketPrice(matchGoodVo.getMarketPrice());
                releaseProductSkuVo.setStockCount(matchGoodVo.getStockCount());
            }
        }

        return Result.success("查询成功", copyList);
    }


    /**
     * 查询规格所匹配到的商品信息
     *
     * @param releaseProductSkuVo
     * @param goodVoList
     * @return
     */
    private GoodVo findMatchGoodVo(ReleaseProductSkuVo releaseProductSkuVo, List<GoodVo> goodVoList) {

        //如果规格的数量相同 且 都能再 goodsVo里面找到 那就是这个商品了
        List<ReleaseProductSkuSpecVo> skuSpecVos = releaseProductSkuVo.getSkuSpecVos();
        for (GoodVo goodVo : goodVoList) {
            List<GoodSpecVo> goodSpecVoList = goodVo.getGoodSpecVoList();
            if (skuSpecVos.size() != goodSpecVoList.size()) {
                continue;
            }

            for (int i = 0; i < goodSpecVoList.size(); i++) {
                GoodSpecVo specVo = goodSpecVoList.get(i);
                String specName = specVo.getSpecName();
                String specValue = specVo.getSpecValue();
                Optional<ReleaseProductSkuSpecVo> any = skuSpecVos.stream().filter(e -> StringUtils.equalsIgnoreBlank(e.getSpecName(), specName) && StringUtils.equalsIgnoreBlank(e.getValue(), specValue)).findAny();
                if (!any.isPresent()) {
                    //有一个没找到 直接结束循环 去判断下一个商品
                    break;
                }

                //到了最后一个了 说明就是匹配到的 good 对象
                if (i == goodSpecVoList.size() - 1) {
                    //找到了
                    return goodVo;
                }
            }
        }

        return null;
    }

    //-------------------------------------为前端构建规格信息 V1------------------------------------


    //-------------------------------------为前端构建规格信息 V2   返回添加商品参数，前端无需转换------------------------------------

    /**
     * 添加产品时根据产品规格信息构建sku
     * 供前端调用
     * 2020-06-17 直接返回添加参数供前端使用，前端无需转换
     *
     * @param productSpecParamsList
     * @return
     */
    public Result releaseProductSkuVoV2(List<ProductSpecParams> productSpecParamsList) {

        //校验一下数据
        if (Lists.isEmpty(productSpecParamsList)) {
            return Result.failure("产品参数为空");
        }
        for (ProductSpecParams productSpecParams : productSpecParamsList) {
            String specName = productSpecParams.getSpecName();
            if (StringUtils.isEmpty(specName)) {
                return Result.failure("规格名称为空");
            }
            List<ProductSpecValueParams> specValueList = productSpecParams.getSpecValueList();
            if (Lists.isEmpty(specValueList)) {
                return Result.failure("规格值数组为空");
            }
        }

        List<GoodAddParams> list = releaseProductSkuSpecVoV2(productSpecParamsList);

        return Result.success("查询成功", list);
    }


    /**
     * 根据产品规格为前端构建sku信息
     * 2020-06-17 直接返回添加参数供前端使用，前端无需转换
     *
     * @param productSpecParamsList
     * @return
     */
    public List<GoodAddParams> releaseProductSkuSpecVoV2(List<ProductSpecParams> productSpecParamsList) {

        List<GoodAddParams> list = new ArrayList<>();

        if (Lists.isNotEmpty(productSpecParamsList)) {
            //开始构建

            for (int i = 0; i < productSpecParamsList.size(); i++) {

                ProductSpecParams productSpecParams = productSpecParamsList.get(i);
                String specName = productSpecParams.getSpecName();
                List<ProductSpecValueParams> specValueList = productSpecParams.getSpecValueList();

                if (i == 0) {
                    for (int j = 0; j < specValueList.size(); j++) {
                        //第一个规格,直接创建
                        ProductSpecValueParams specValueParams = specValueList.get(j);

                        GoodAddParams goodAddParams = new GoodAddParams();

                        List<GoodSpecParams> goodSpecParamsList = new ArrayList<>();
                        GoodSpecParams goodSpecParams = releaseReleaseProductSkuSpecVoV2(specName, specValueParams);
                        goodSpecParamsList.add(goodSpecParams);

                        goodAddParams.setGoodSpecParamsList(goodSpecParamsList);

                        list.add(goodAddParams);

                    }
                } else {
                    //不是第一个就直接在直接的基础上拼接即可
                    List<GoodAddParams> copyList = list.stream().collect(Collectors.toList());
                    list.clear();

                    for (GoodAddParams goodAddParams : copyList) {
                        for (int j = 0; j < specValueList.size(); j++) {

                            GoodAddParams goodAddParams1 = new GoodAddParams();

                            ProductSpecValueParams specValueParams = specValueList.get(j);
                            GoodSpecParams goodSpecParams = releaseReleaseProductSkuSpecVoV2(specName, specValueParams);
                            List<GoodSpecParams> collect = goodAddParams.getGoodSpecParamsList().stream().collect(Collectors.toList());
                            collect.add(goodSpecParams);

                            goodAddParams1.setGoodSpecParamsList(collect);

                            list.add(goodAddParams1);

                        }
                    }

                }
            }
            return list;
        }

        return list;
    }


    /**
     * 构建sku单个属性信息
     * 2020-06-17 直接返回添加参数供前端使用，前端无需转换
     *
     * @param specName
     * @param productSpecValueParams
     * @return
     */
    private GoodSpecParams releaseReleaseProductSkuSpecVoV2(String specName, ProductSpecValueParams productSpecValueParams) {
        GoodSpecParams goodSpecParams = new GoodSpecParams();
        goodSpecParams.setSpecName(specName);
        goodSpecParams.setSpecValue(productSpecValueParams.getValue());
        goodSpecParams.setSpecImageValue(productSpecValueParams.getImageValue());
        return goodSpecParams;
    }

    /**
     * 修改产品时根据产品规格信息构建sku
     * 供前端调用
     * 2020-06-17 直接返回添加参数供前端使用，前端无需转换
     *
     * @param params
     * @return
     */
    public Result releaseUpdateProductSkuVoV2(ReleaseUpdateProductSkuParams params) {
        List<ProductSpecParams> productSpecParamsList = params.getProductSpecParamsList();

        //校验一下数据
        if (Objects.isNull(params.getProductId())) {
            return Result.failure("产品id为空");
        }
        if (Lists.isEmpty(productSpecParamsList)) {
            return Result.failure("产品参数为空");
        }
        for (ProductSpecParams productSpecParams : productSpecParamsList) {
            String specName = productSpecParams.getSpecName();
            if (StringUtils.isEmpty(specName)) {
                return Result.failure("规格名称为空");
            }
            List<ProductSpecValueParams> specValueList = productSpecParams.getSpecValueList();
            if (Lists.isEmpty(specValueList)) {
                return Result.failure("规格值数组为空");
            }
        }

        List<GoodAddParams> list = releaseProductSkuSpecVoV2(productSpecParamsList);

        //查询这个商品信息
        AdminProductVo adminProductVo = productService.findAdminProductVoByProductId(params.getProductId());
        List<GoodVo> goodVoList = adminProductVo.getGoodVoList();

        for (GoodAddParams goodAddParams : list) {
            GoodVo matchGoodVo = findMatchGoodVoV2(goodAddParams, goodVoList);
            if (Objects.nonNull(matchGoodVo)) {
                //找到了对应的商品 赋值价格 库存
                goodAddParams.setPrice(matchGoodVo.getPrice());
                goodAddParams.setVipPrice(matchGoodVo.getVipPrice());
                goodAddParams.setMarketPrice(matchGoodVo.getMarketPrice());
                goodAddParams.setStockCount(matchGoodVo.getStockCount());
            }
        }

        return Result.success("查询成功", list);
    }


    /**
     * 查询规格所匹配到的商品信息
     * 2020-06-17 直接返回添加参数供前端使用，前端无需转换
     *
     * @param goodAddParams
     * @param goodVoList
     * @return
     */
    private GoodVo findMatchGoodVoV2(GoodAddParams goodAddParams, List<GoodVo> goodVoList) {

        //如果规格的数量相同 且 都能再 goodsVo里面找到 那就是这个商品了
        List<GoodSpecParams> goodSpecParamsList = goodAddParams.getGoodSpecParamsList();
        for (GoodVo goodVo : goodVoList) {
            List<GoodSpecVo> goodSpecVoList = goodVo.getGoodSpecVoList();
            if (goodSpecParamsList.size() != goodSpecVoList.size()) {
                continue;
            }

            for (int i = 0; i < goodSpecVoList.size(); i++) {
                GoodSpecVo specVo = goodSpecVoList.get(i);
                String specName = specVo.getSpecName();
                String specValue = specVo.getSpecValue();
                Optional<GoodSpecParams> any = goodSpecParamsList.stream().filter(e -> StringUtils.equalsIgnoreBlank(e.getSpecName(), specName) && StringUtils.equalsIgnoreBlank(e.getSpecValue(), specValue)).findAny();
                if (!any.isPresent()) {
                    //有一个没找到 直接结束循环 去判断下一个商品
                    break;
                }

                //到了最后一个了 说明就是匹配到的 good 对象
                if (i == goodSpecVoList.size() - 1) {
                    //找到了
                    return goodVo;
                }
            }
        }

        return null;
    }


}
