package com.github.chenlijia1111.mallweb.conf;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/8/8 0008 下午 7:50
 **/
@Configuration
public class BeanConfig {

    @Bean
    public PageHelper pageHelper() {

        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("dialect", "mysql");
        p.setProperty("reasonable", "false");
        pageHelper.setProperties(p);
        return pageHelper;

    }
}
