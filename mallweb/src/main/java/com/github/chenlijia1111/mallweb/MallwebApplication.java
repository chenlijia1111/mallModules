package com.github.chenlijia1111.mallweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(value = {"com.github.chenlijia1111.commonModule.dao"})
@EnableTransactionManagement
@EnableConfigurationProperties
@SpringBootApplication
public class MallwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallwebApplication.class, args);
    }

}
