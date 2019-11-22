package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import com.github.chenlijia1111.commonModule.common.responseVo.product.ProductSpecVo;
import com.github.chenlijia1111.commonModule.dao.ProductSpecMapper;
import com.github.chenlijia1111.commonModule.dao.ProductSpecValueMapper;
import com.github.chenlijia1111.commonModule.entity.ProductSpec;
import com.github.chenlijia1111.commonModule.entity.ProductSpecValue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 产品的规格选项
 *
 * @author chenLiJia
 * @since 2019-11-01 14:34:10
 **/
@Service
public class ProductSpecServiceImpl implements com.github.chenlijia1111.commonModule.service.ProductSpecServiceI {


    @Resource
    private ProductSpecMapper productSpecMapper;
    @Resource
    private ProductSpecValueMapper productSpecValueMapper;//产品规格值


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 14:34:10
     **/
    public Result add(ProductSpec params) {

        int i = productSpecMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 14:34:10
     **/
    public Result update(ProductSpec params) {

        int i = productSpecMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 根据产品id删除规格以及规格值
     *
     * @param productId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:59 2019/11/1 0001
     **/
    @Override
    public Result deleteByProductId(String productId) {

        if (StringUtils.isNotEmpty(productId)) {
            ProductSpec condition = new ProductSpec().setProductId(productId);
            List<ProductSpec> select = productSpecMapper.select(condition);
            if (Objects.nonNull(select)) {
                //规格id集合
                Set<Integer> specIdSet = select.stream().map(e -> e.getId()).collect(Collectors.toSet());
                productSpecMapper.batchDelete(specIdSet);
                productSpecValueMapper.deleteBySpecIdSet(specIdSet);
            }
        }

        return Result.success("操作成功");
    }


    /**
     * 根据产品id集合查询产品的
     *
     * @param productIdSet 1
     * @return java.util.List<com.github.chenlijia1111.commonModule.common.responseVo.product.ProductSpecVo>
     * @since 上午 9:28 2019/11/5 0005
     **/
    @Override
    public List<ProductSpecVo> listProductSpecVoByProductIdSet(Set<String> productIdSet) {

        if (Sets.isNotEmpty(productIdSet)) {
            List<ProductSpecVo> productSpecVos = productSpecMapper.listProductSpecVoByProductIdSet(productIdSet);
            //查询这些产品规格所对应的值
            Set<Integer> productSpecIdSet = productSpecVos.stream().map(e -> e.getId()).collect(Collectors.toSet());
            List<ProductSpecValue> productSpecValueList = productSpecValueMapper.listBySpecIdSet(productSpecIdSet);
            //关联规格值数据
            for (ProductSpecVo specVo : productSpecVos) {
                List<ProductSpecValue> collect = productSpecValueList.stream().filter(e -> Objects.equals(e.getProductSpecId(), specVo.getId())).collect(Collectors.toList());
                specVo.setProductSpecValueList(collect);
            }
            return productSpecVos;
        }

        return Lists.newArrayList();
    }


}
