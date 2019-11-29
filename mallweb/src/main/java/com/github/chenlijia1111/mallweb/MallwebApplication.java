package com.github.chenlijia1111.mallweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(value = {"com.github.chenlijia1111.commonModule.dao",
        "com.github.chenlijia1111.spike.dao",
        "com.github.chenlijia1111.fightGroup.dao",
        "com.github.chenlijia1111.mallweb.dao"})
@ComponentScan(value = {"com.github.chenlijia1111.commonModule",
        "com.github.chenlijia1111.spike",
        "com.github.chenlijia1111.fightGroup",
        "com.github.chenlijia1111.mallweb"})
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableSwagger2
@SpringBootApplication
public class MallwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallwebApplication.class, args);
    }

}
