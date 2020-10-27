package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.requestVo.PageAbleVo;
import com.github.chenlijia1111.commonModule.common.requestVo.shopCar.ShopCarAddOrUpdateParams;
import com.github.chenlijia1111.commonModule.common.requestVo.shopCar.UpdateShopCarGoodIdParams;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.commonModule.common.responseVo.shop.CommonMallSimpleShopVo;
import com.github.chenlijia1111.commonModule.common.responseVo.shopCar.ClientShopCarGroupByShopVo;
import com.github.chenlijia1111.commonModule.common.responseVo.shopCar.ClientShopCarVo;
import com.github.chenlijia1111.commonModule.entity.Product;
import com.github.chenlijia1111.commonModule.entity.ShopCar;
import com.github.chenlijia1111.commonModule.service.*;
import com.github.chenlijia1111.commonModule.utils.BigDecimalUtil;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.database.mybatis.pojo.Page;
import com.github.chenlijia1111.utils.database.mybatis.pojo.PageInfo;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 购物车
 *
 * @author chenLiJia
 * @since 2020-02-19 17:37:09
 **/
@Service
public class ShopCarBiz {

    @Autowired
    private ShopCarServiceI shopCarService;//购物车
    @Autowired
    private CommonModuleUserServiceI clientUserService;//用户
    @Autowired
    private CommonModuleShopServiceI commonModuleShopService;//通用商城商家
    @Autowired
    private GoodsServiceI goodsService;//商品
    @Autowired
    private ProductServiceI productService;//产品


    /**
     * 加入购物车
     * 并返回购物车商品总数量
     *
     * @param params 1
     * @return
     * @author chenlijia
     * @since 上午 11:25 2019/8/17 0017
     **/
    public Result add(ShopCarAddOrUpdateParams params) {
        return add(params, null);
    }


    /**
     * 加入购物车
     * 并返回购物车商品总数量
     *
     * @param params        1
     * @param currentUserId 用户id 如果传了，就使用这个id，如果没传就通过接口获取当前用户id
     * @return
     * @author chenlijia
     * @since 上午 11:25 2019/8/17 0017
     **/
    public Result add(ShopCarAddOrUpdateParams params, String currentUserId) {

        if (StringUtils.isEmpty(currentUserId)) {
            //验证用户是否登录
            currentUserId = clientUserService.currentUserId();
            if (StringUtils.isEmpty(currentUserId)) {
                return Result.notLogin();
            }
        }

        //验证参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断商品是否存在
        GoodVo goodVo = goodsService.findByGoodId(params.getGoodsId());
        if (Objects.isNull(goodVo)) {
            return Result.failure("商品不存在");
        }
        //判断产品是否存在
        Product product = productService.findByProductId(goodVo.getProductId());
        if (Objects.isNull(product)) {
            return Result.failure("产品信息不存在");
        }

        //加入购物车
        //判断之前有没有加入过
        ShopCar condition = new ShopCar();
        condition.setClientId(currentUserId);
        condition.setGoodId(params.getGoodsId());
        List<ShopCar> shopCars = shopCarService.listByCondition(condition);
        if (Lists.isEmpty(shopCars)) {
            //没有加入过 直接加入
            ShopCar shopCar = new ShopCar();
            shopCar.setGoodId(params.getGoodsId());
            shopCar.setProductId(product.getId());
            shopCar.setClientId(currentUserId);
            shopCar.setGoodCount(params.getGoodsCount());
            shopCar.setUpdateTime(new Date());
            Result add = shopCarService.add(shopCar);
            //返回购物车中当前商品种类的数量
            Integer shopCarAllGoodsKindCount = findShopCarAllGoodsKindCount(currentUserId);
            add.setData(shopCarAllGoodsKindCount);
            return add;
        } else {
            //加入过  在原来的基础上增加数量
            ShopCar shopCar = shopCars.get(0);
            shopCar.setGoodCount(BigDecimalUtil.add(shopCar.getGoodCount(),params.getGoodsCount()));
            Result update = shopCarService.update(shopCar);
            //返回购物车中当前商品种类的数量
            Integer shopCarAllGoodsKindCount = findShopCarAllGoodsKindCount(currentUserId);
            update.setData(shopCarAllGoodsKindCount);
            return update;
        }

    }


    /**
     * 查找用户购物车中商品种类总数量
     *
     * @param client
     * @return
     */
    private Integer findShopCarAllGoodsKindCount(String client) {
        //返回购物车中当前商品种类的数量
        Integer shopCarAllGoodsKindCount;
        if (CommonMallConstants.SHOP_CAR_FILTER_NOT_SHELF_PRODUCT) {
            //要过滤未上架的商品
            shopCarAllGoodsKindCount = shopCarService.findShopCarAllGoodsKindCountWithShelf(client);
        } else {
            //不需要过滤未上架的商品
            shopCarAllGoodsKindCount = shopCarService.findShopCarAllGoodsKindCount(client);
        }
        if (Objects.isNull(shopCarAllGoodsKindCount)) {
            shopCarAllGoodsKindCount = 0;
        }
        return shopCarAllGoodsKindCount;
    }


    /**
     * 批量删除购物车
     *
     * @param shopCarIdList 1
     * @return
     * @author chenlijia
     * @since 上午 11:26 2019/8/17 0017
     **/
    public Result batchDelete(List<Integer> shopCarIdList) {
        return batchDelete(shopCarIdList, null);
    }

    /**
     * 批量删除购物车
     *
     * @param shopCarIdList 1
     * @param currentUserId 用户id 如果传了，就使用这个id，如果没传就通过接口获取当前用户id
     * @return
     * @author chenlijia
     * @since 上午 11:26 2019/8/17 0017
     **/
    public Result batchDelete(List<Integer> shopCarIdList, String currentUserId) {

        //校验参数
        if (Lists.isEmpty(shopCarIdList)) {
            return Result.failure("请选择要删除的商品");
        }

        if (StringUtils.isEmpty(currentUserId)) {
            //验证用户是否登录
            currentUserId = clientUserService.currentUserId();
            if (StringUtils.isEmpty(currentUserId)) {
                return Result.notLogin();
            }
        }

        //判断这些数据是否是当前用户的
        List<ShopCar> shopCarList = shopCarService.listByShopCarIdSet(shopCarIdList.stream().collect(Collectors.toSet()));
        String finalCurrentUserId = currentUserId;
        Optional<ShopCar> shopCarOptional = shopCarList.stream().filter(e -> !Objects.equals(finalCurrentUserId, e.getClientId())).findAny();
        if (shopCarOptional.isPresent()) {
            return Result.failure("权限不足，无法删除其他用户的购物车数据");
        }

        return shopCarService.batchDelete(shopCarIdList);
    }


    /**
     * 修改购物车商品数量
     * 修改成功之后 返回商品当前的价格
     *
     * @param params 1
     * @return
     * @author chenlijia
     * @since 上午 11:26 2019/8/17 0017
     **/
    public Result updateShopCarCount(ShopCarAddOrUpdateParams params) {
        return updateShopCarCount(params, null);
    }

    /**
     * 修改购物车商品数量
     * 修改成功之后 返回商品当前的价格
     *
     * @param params        1
     * @param currentUserId 用户id 如果传了，就使用这个id，如果没传就通过接口获取当前用户id
     * @return
     * @author chenlijia
     * @since 上午 11:26 2019/8/17 0017
     **/
    public Result updateShopCarCount(ShopCarAddOrUpdateParams params, String currentUserId) {

        if (StringUtils.isEmpty(currentUserId)) {
            //验证用户是否登录
            currentUserId = clientUserService.currentUserId();
            if (StringUtils.isEmpty(currentUserId)) {
                return Result.notLogin();
            }
        }

        //验证参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断商品是否存在
        GoodVo goodVo = goodsService.findByGoodId(params.getGoodsId());
        if (Objects.isNull(goodVo)) {
            return Result.failure("商品不存在");
        }
        //判断产品是否存在
        Product product = productService.findByProductId(goodVo.getProductId());
        if (Objects.isNull(product)) {
            return Result.failure("产品信息不存在");
        }


        //加入购物车
        //判断之前有没有加入过
        ShopCar condition = new ShopCar();
        condition.setClientId(currentUserId);
        condition.setGoodId(params.getGoodsId());
        List<ShopCar> shopCars = shopCarService.listByCondition(condition);
        if (Lists.isEmpty(shopCars)) {
            //没有加入过 直接加入
            ShopCar shopCar = new ShopCar();
            shopCar.setGoodId(params.getGoodsId());
            shopCar.setProductId(product.getId());
            shopCar.setClientId(currentUserId);
            shopCar.setGoodCount(params.getGoodsCount());
            shopCar.setUpdateTime(new Date());
            Result add = shopCarService.add(shopCar);
            return add;
        } else {
            //加入过  在原来的基础上修改数量
            ShopCar shopCar = shopCars.get(0);
            shopCar.setGoodCount(params.getGoodsCount());
            Result update = shopCarService.update(shopCar);
            return update;
        }
    }


    /**
     * 查询客户端购物车列表
     * 以商家进行分组
     * 排序按对应的最近的交易时间进行排序
     * 先查询出商家，在查询商家对应的购物车列表
     *
     * @param pageAbleVo 1
     * @return
     * @author chenlijia
     * @since 上午 11:26 2019/8/17 0017
     **/
    public Result listPage(PageAbleVo pageAbleVo) {

        pageAbleVo = PropertyCheckUtil.transferObjectNotNull(pageAbleVo);

        //验证用户是否登录
        String currentUserId = clientUserService.currentUserId();
        if (StringUtils.isEmpty(currentUserId)) {
            return Result.notLogin();
        }

        //开启分页
        Page.startPage(pageAbleVo.getPage(), pageAbleVo.getLimit());

        List<ClientShopCarGroupByShopVo> list = shopCarService.listPageWithClient(currentUserId);
        if (Lists.isNotEmpty(list)) {
            //关联店铺名称店铺logo
            Set<String> shopIdSet = list.stream().map(e -> e.getShopId()).collect(Collectors.toSet());
            List<CommonMallSimpleShopVo> commonMallSimpleShopVos = commonModuleShopService.listByShopIdSet(shopIdSet);
            if (Lists.isNotEmpty(commonMallSimpleShopVos)) {
                for (ClientShopCarGroupByShopVo vo : list) {
                    Optional<CommonMallSimpleShopVo> any = commonMallSimpleShopVos.stream().filter(e -> Objects.equals(e.getShopId(), vo.getShopId())).findAny();
                    if (any.isPresent()) {
                        CommonMallSimpleShopVo commonMallSimpleShopVo = any.get();
                        vo.setStoreName(commonMallSimpleShopVo.getStoreName());
                        vo.setStoreLogo(commonMallSimpleShopVo.getStoreLogo());
                    }
                }
            }
        }
        PageInfo<ClientShopCarGroupByShopVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }


    /**
     * 通过购物车id查询购物车信息
     *
     * @param list 1
     * @return
     * @author chenlijia
     * @since 14:05 2019/8/23
     **/
    public Result listByShopCarIdList(List<Integer> list) {

        if (Lists.isEmpty(list)) {
            return Result.failure("购物车id集合为空");
        }

        //验证用户是否登录
        String currentUserId = clientUserService.currentUserId();
        if (StringUtils.isEmpty(currentUserId)) {
            return Result.notLogin();
        }

        List<ClientShopCarVo> carIdList = shopCarService.listByShopCarIdList(list, currentUserId);
        return Result.success("查询成功", carIdList);
    }


    /**
     * 查询当前用户购物车数量信息
     *
     * @return
     * @since 下午 4:40 2019/9/21 0021
     **/
    public Result findCurrentUserShopCarCountInfo() {

        //如果当前是游客 直接返回 0
        //验证用户是否登录
        String currentUserId = clientUserService.currentUserId();
        if (StringUtils.isNotEmpty(currentUserId)) {
            //获取当前用户的购物车数量
            Integer shopCarAllGoodsKindCount = findShopCarAllGoodsKindCount(currentUserId);
            return Result.success("查询成功", shopCarAllGoodsKindCount);
        }
        return Result.success("查询成功", 0);
    }


    /**
     * 修改购物车商品
     * 如果购物车中已经有这个商品了，那么就把之前的商品的购物车删除
     * 如果没有则个商品，就直接修改
     *
     * @param params
     * @return
     */
    public Result updateShopCarGoodId(UpdateShopCarGoodIdParams params) {
        return updateShopCarGoodId(params, null);
    }

    /**
     * 修改购物车商品
     * 如果购物车中已经有这个商品了，那么就把之前的商品的购物车删除
     * 如果没有则个商品，就直接修改
     *
     * @param params
     * @param currentUserId 用户id 如果传了，就使用这个id，如果没传就通过接口获取当前用户id
     * @return
     */
    public Result updateShopCarGoodId(UpdateShopCarGoodIdParams params, String currentUserId) {

        if (StringUtils.isEmpty(currentUserId)) {
            //当前用户
            currentUserId = clientUserService.currentUserId();
            if (StringUtils.isEmpty(currentUserId)) {
                return Result.notLogin();
            }
        }

        //判断购物车是否存在
        List<ShopCar> shopCars = shopCarService.listByShopCarIdSet(Sets.asSets(params.getShopCarId()));
        if (Lists.isEmpty(shopCars)) {
            return Result.failure("购物车不存在");
        }

        //原来的购物车
        ShopCar originalShopCar = shopCars.get(0);

        //判断现在要修改的购物车是否存在
        ShopCar condition = new ShopCar();
        condition.setClientId(currentUserId);
        condition.setGoodId(params.getGoodId());
        List<ShopCar> hitShopCars = shopCarService.listByCondition(condition);
        if (Lists.isNotEmpty(hitShopCars)) {
            //这个商品已经在购物车了
            //删除它
            shopCarService.batchDelete(hitShopCars.stream().map(e -> e.getId()).collect(Collectors.toList()));
        }

        //进行修改
        originalShopCar.setGoodId(params.getGoodId());
        originalShopCar.setGoodCount(params.getGoodCount());

        return shopCarService.update(originalShopCar);
    }


}
