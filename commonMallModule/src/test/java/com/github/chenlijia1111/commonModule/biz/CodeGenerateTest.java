package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.utils.code.mybatis.CommonMapperCommentGenerator;
import com.github.chenlijia1111.utils.code.mybatis.MybatisCodeGeneratorUtil;
import com.mysql.cj.jdbc.Driver;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * 代码生成
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 上午 9:46
 **/
public class CodeGenerateTest {

    private static MybatisCodeGeneratorUtil mybatisCodeGeneratorUtil = MybatisCodeGeneratorUtil.getInstance();

    static {
        mybatisCodeGeneratorUtil.setCommentGeneratorType(CommonMapperCommentGenerator.class.getName())
                .setConnectionUrl("jdbc:mysql://cdb-lob0ggj0.bj.tencentcdb.com:10048/commonMall?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8").
                setDriverClass(Driver.class.getName()).
                setUserId("root").setPassword("clj123456@")
                .setTargetProjectPath("D:\\java\\projects\\mallModules\\commonMallModule\\src\\main\\java").
                setTargetDAOPackage("com.github.chenlijia1111.commonModule.dao").setTargetEntityPackage("com.github.chenlijia1111.commonModule.entity").
                setTargetXMLPackage("com.github.chenlijia1111.commonModule.mapper").
                setTargetControllerPackage("com.github.chenlijia1111.commonModule.controller.admin").setTargetBizPackage("com.github.chenlijia1111.commonModule.biz").
                setTargetServicePackage("com.github.chenlijia1111.commonModule.service")
                .setAuthor("chenLiJia");

        mybatisCodeGeneratorUtil.setExampleCode(false);
        mybatisCodeGeneratorUtil.setCommonCode(false);

        Map<String, String> tableToDomain = mybatisCodeGeneratorUtil.getTableToDoMain();
        tableToDomain.put("s_product_snapshot", "s_product_snapshot");

        //首字母大写
        for (String s : tableToDomain.keySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            String[] strings = s.split("_");
            for (int i = 1; i < strings.length; i++) {
                String word = strings[i];
                //首字母大写
                word = word.substring(0, 1).toUpperCase() + word.substring(1);
                stringBuilder.append(word);
            }
            tableToDomain.put(s, stringBuilder.toString());
        }

        List<String> ignoreDoMainToBusiness = mybatisCodeGeneratorUtil.getIgnoreDoMainToBusiness();
    }


    //生成entity,mapper,dao
    @Test
    public void test1WithChen() {
        mybatisCodeGeneratorUtil.generateCode();
    }

    //生成controller,biz,service
    @Test
    public void test2WithChen() {
        mybatisCodeGeneratorUtil.generateWithBusinessCode();
    }

}
