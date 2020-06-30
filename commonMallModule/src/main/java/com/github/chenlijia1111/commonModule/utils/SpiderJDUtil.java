package com.github.chenlijia1111.commonModule.utils;

import com.github.chenlijia1111.commonModule.biz.ProductBiz;
import com.github.chenlijia1111.commonModule.common.requestVo.product.GoodAddParams;
import com.github.chenlijia1111.commonModule.common.requestVo.product.ProductAddParams;
import com.github.chenlijia1111.commonModule.common.requestVo.product.ProductSpecParams;
import com.github.chenlijia1111.commonModule.common.requestVo.product.ProductSpecValueParams;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.WebFileUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 京东商品爬取工具
 * @author Chen LiJia
 * @since 2020/6/17
 */
public class SpiderJDUtil {

    /**
     * 默认类别id
     *
     * @param defaultCategoryId 默认类别Id
     * @param productUrl 爬取的产品详情页面
     * @return
     */
    public static ProductAddParams createSingleProductInfo(String defaultCategoryId, String productUrl) {

        String yyyyMMdd = DateTime.now().toString("yyyyMMdd");

        ProductAddParams productAddParams = new ProductAddParams();
        productAddParams.setCategoryId(defaultCategoryId);
        productAddParams.setBrand("京东爬取商品");
        productAddParams.setProductNo("001");
        productAddParams.setUnit("个");
        productAddParams.setSortNumber(1);
        productAddParams.setContent("京东爬取商品");
        productAddParams.setDescription("京东爬取商品");
        productAddParams.setShelfStatus(1);

        try {
            Document document = Jsoup.parse(new URL(productUrl), 10000);
            //商品名称
            Elements productNameElement = document.getElementsByClass("sku-name");
            if (Objects.nonNull(productNameElement)) {
                String productName = productNameElement.text().trim();
                productAddParams.setName(productName);
            }
            //商品图片数组
            Elements productImagesElementWrapper = document.getElementsByClass("w");
            if (Objects.nonNull(productImagesElementWrapper)) {

                Iterator<Element> iterator = productImagesElementWrapper.iterator();
                while (iterator.hasNext()) {
                    Element wElement = iterator.next();
                    Elements jqzoomMainImgelements = wElement.getElementsByClass("jqzoom main-img");
                    if (Objects.nonNull(jqzoomMainImgelements) && jqzoomMainImgelements.size() > 0) {
                        Elements productImageElements = jqzoomMainImgelements.get(0).getElementsByTag("img");
                        if (Objects.nonNull(productImageElements) && productImageElements.size() > 0) {
                            String productImage = productImageElements.first().attr("data-origin");
                            //将图片转存到本地
                            String saveProductImageDirectory = "C:/upload/img/" + yyyyMMdd;
                            Result result = WebFileUtil.httpImageToLocalImage("http:" + productImage, saveProductImageDirectory);
                            if (result.getSuccess()) {
                                //system/file/downLoad?filePath=20200514/402f5a4ae7c24fba9e4fe16195254d17.jpeg&fileType=img
                                String saveImageUrl = "system/file/downLoad?filePath=" + yyyyMMdd + "/" + result.getData().toString() + "&fileType=img";
                                productAddParams.setSmallPic(JSONUtil.objToStr(Sets.asSets(saveImageUrl)));
                            }
                        }
                    }
                }
            }
            Element chooseAttrsElement = document.getElementById("choose-attrs");
            if (Objects.nonNull(chooseAttrsElement)) {
                Elements specElements = chooseAttrsElement.getElementsByClass("li p-choose");
                if (Objects.nonNull(specElements) && specElements.size() > 0) {
                    Iterator<Element> specIterator = specElements.iterator();

                    //构建产品规格数组
                    ArrayList<ProductSpecParams> productSpecParamList = new ArrayList<>();

                    while (specIterator.hasNext()) {

                        //构建产品规格
                        ProductSpecParams productSpecParam = new ProductSpecParams();

                        //规格名称
                        Element specElement = specIterator.next();
                        String specName = specElement.attr("data-type");
                        productSpecParam.setSpecName(specName);
                        //规格值
                        Elements specValueElements = specElement.getElementsByClass("item");
                        if (Objects.nonNull(specValueElements) && specValueElements.size() > 0) {

                            //构建产品规格值数组
                            ArrayList<ProductSpecValueParams> productSpecValueParamsArrayList = new ArrayList<>();

                            Iterator<Element> specValueIterator = specValueElements.iterator();
                            while (specValueIterator.hasNext()) {
                                Element specValueElement = specValueIterator.next();
                                String specValue = specValueElement.attr("data-value");
                                System.out.println(specValue);

                                ProductSpecValueParams productSpecValueParam = new ProductSpecValueParams();
                                productSpecValueParam.setValue(specValue);
                                productSpecValueParamsArrayList.add(productSpecValueParam);
                            }

                            productSpecParam.setSpecValueList(productSpecValueParamsArrayList);
                        }

                        productSpecParamList.add(productSpecParam);
                    }

                    productAddParams.setProductSpecParamsList(productSpecParamList);

                    //构建商品参数
                    ProductBiz productBiz = new ProductBiz();
                    List<GoodAddParams> goodAddParams = productBiz.releaseProductSkuSpecVoV2(productAddParams.getProductSpecParamsList());
                    if (Lists.isNotEmpty(goodAddParams)) {
                        //默认值
                        for (GoodAddParams goodAddParam : goodAddParams) {
                            goodAddParam.setPrice(2.0);
                            goodAddParam.setVipPrice(2.0);
                            goodAddParam.setMarketPrice(2.0);
                            goodAddParam.setGoodNo("001");
                            goodAddParam.setStockCount(100);
                        }
                    }

                    productAddParams.setGoodList(goodAddParams);

                    return productAddParams;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 爬取京东页面
     * @param defaultCategoryId 默认类别id
     * @param productListPageUrl 爬取的商品列表页面
     * @return
     */
    public static List<ProductAddParams> createProductList(String defaultCategoryId, String productListPageUrl) {

        ArrayList<ProductAddParams> productAddParamsList = new ArrayList<>();

        try {
            Document document = Jsoup.parse(new URL(productListPageUrl), 10000);
            Elements productElements = document.getElementsByClass("gl-item");
            if (Objects.nonNull(productElements) && productElements.size() > 0) {
                Iterator<Element> iterator = productElements.iterator();
                while (iterator.hasNext()) {
                    Element productSingleElement = iterator.next();
                    Elements hrefElements = productSingleElement.getElementsByTag("a");
                    if (Objects.nonNull(hrefElements) && hrefElements.size() > 0) {
                        String href = hrefElements.first().attr("href");
                        if(href.startsWith("//")){
                            href = "http:" + href;
                            ProductAddParams productAddParams = createSingleProductInfo(defaultCategoryId, href);
                            productAddParamsList.add(productAddParams);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productAddParamsList;
    }

}
