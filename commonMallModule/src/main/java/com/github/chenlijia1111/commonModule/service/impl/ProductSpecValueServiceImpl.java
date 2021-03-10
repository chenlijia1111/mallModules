package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.common.responseVo.product.ProductSpecValueNameWrapperVo;
import com.github.chenlijia1111.commonModule.dao.ProductSpecValueMapper;
import com.github.chenlijia1111.commonModule.entity.ProductSpecValue;
import com.github.chenlijia1111.commonModule.service.ProductSpecValueServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 产品规格值
 *
 * @author chenLiJia
 * @since 2019-11-01 14:34:10
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "ProductSpecValueServiceI")
public class ProductSpecValueServiceImpl implements ProductSpecValueServiceI {


    @Resource
    private ProductSpecValueMapper productSpecValueMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 14:34:10
     **/
    public Result add(ProductSpecValue params) {

        int i = productSpecValueMapper.insertSelective(params);
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
    public Result update(ProductSpecValue params) {

        int i = productSpecValueMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


    /**
     * 批量添加规格值
     *
     * @param list 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:54 2019/11/1 0001
     **/
    @Override
    public Result batchAdd(List<ProductSpecValue> list) {
        if (Lists.isNotEmpty(list)) {
            productSpecValueMapper.batchAdd(list);
        }
        return Result.success("操作成功");
    }

    /**
     * 查询产品id的所有规格值对象映射
     *
     * @param productIdSet
     * @return
     */
    @Override
    public List<ProductSpecValueNameWrapperVo> listProductSpecValueNameWrapperVo(Set<String> productIdSet) {
        if (Sets.isNotEmpty(productIdSet)) {
            return productSpecValueMapper.listProductSpecValueNameWrapperVo(productIdSet);
        }
        return new ArrayList<>();
    }
}
