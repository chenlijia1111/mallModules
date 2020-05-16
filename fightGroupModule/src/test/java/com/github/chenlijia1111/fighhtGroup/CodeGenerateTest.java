package com.github.chenlijia1111.fighhtGroup;

import com.github.chenlijia1111.utils.code.mybatis.CommonMapperCommentGenerator;
import com.github.chenlijia1111.utils.code.mybatis.MybatisCodeGeneratorUtil;
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
                .setConnectionUrl("jdbc:mysql://127.0.0.1:3306/commonMall?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8").
                setDriverClass("com.mysql.jdbc.Driver").
                setUserId("root").setPassword("root")
                .setTargetProjectPath("D:\\ssmProject\\waibao\\mallModules\\fightGroupModule\\src\\main\\java").
                setTargetDAOPackage("com.github.chenlijia1111.fightGroup.dao").
                setTargetEntityPackage("com.github.chenlijia1111.fightGroup.entity").
                setTargetXMLPackage("com.github.chenlijia1111.fightGroup.mapper").
                setTargetControllerPackage("com.github.chenlijia1111.fightGroup.controller.admin").
                setTargetBizPackage("com.github.chenlijia1111.fightGroup.biz").
                setTargetServicePackage("com.github.chenlijia1111.fightGroup.service")
                .setAuthor("chenLiJia");

        mybatisCodeGeneratorUtil.setExampleCode(false);
        mybatisCodeGeneratorUtil.setCommonCode(false);

        Map<String, String> tableToDomain = mybatisCodeGeneratorUtil.getTableToDoMain();
        tableToDomain.put("s_fight_group", "FightGroup");
        tableToDomain.put("s_fight_group_product", "FightGroupProduct");
        tableToDomain.put("s_fight_group_user_order", "FightGroupUserOrder");

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
        mybatisCodeGeneratorUtil.setTargetControllerPackage(null).setTargetBizPackage(null);
        mybatisCodeGeneratorUtil.generateWithBusinessCode();
    }

}
