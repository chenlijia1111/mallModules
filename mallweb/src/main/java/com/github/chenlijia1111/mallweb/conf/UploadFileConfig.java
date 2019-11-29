package com.github.chenlijia1111.mallweb.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件地址配置映射Bean
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/16 0016 上午 10:04
 **/
@ConfigurationProperties(prefix = "upload")
@Component
@Setter
@Getter
public class UploadFileConfig {

    /**
     * 文件上传地址
     *
     * @since 上午 10:05 2019/11/16 0016
     **/
    private String fileSavePath;

    /**
     * 图片保存地址
     *
     * @since 上午 10:05 2019/11/16 0016
     **/
    private String imgSavePath;

}
