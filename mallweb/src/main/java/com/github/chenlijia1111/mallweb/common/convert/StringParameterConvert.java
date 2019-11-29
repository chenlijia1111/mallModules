package com.github.chenlijia1111.mallweb.common.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * String 请求参数处理 去除前后的空格
 * 注入spring 进行管理
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/2 0002 下午 2:15
 **/
@Component
public class StringParameterConvert implements Converter<String, String> {


    @Override
    public String convert(String source) {
        if (null != source) {
            source = source.trim();
            return source;
        }
        return null;
    }
}
