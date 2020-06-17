package com.github.chenlijia1111.commonModule.biz.jsoup;

import com.github.chenlijia1111.commonModule.common.requestVo.product.ProductAddParams;
import com.github.chenlijia1111.commonModule.utils.SpiderJDUtil;
import com.github.chenlijia1111.utils.core.JSONUtil;
import org.junit.Test;

import java.util.List;

/**
 * 爬取京东商品
 *
 * @author Chen LiJia
 * @since 2020/6/17
 */
public class SpiderJD {


    @Test
    public void test1() {
        List<ProductAddParams> productList = SpiderJDUtil.createProductList("2", "https://list.jd.com/list.html?cat=9987,653,655");
        System.out.println(JSONUtil.objToStr(productList));
    }

}
