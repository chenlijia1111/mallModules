package com.github.chenlijia1111.mallweb.common.convert;

import com.github.chenlijia1111.utils.common.constant.TimeConstant;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Date 请求参数处理 兼容多种前端时间格式
 * 注入spring 进行管理
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/2 0002 下午 2:15
 **/
@Component
public class DateParameterConvert implements Converter<String, Date> {


    public static String dateTimeReg = "^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$";
    public static String dateReg = "^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$";
    public static String dateTimeMinuteReg = "^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}$";


    @Override
    public Date convert(String source) {
        if (StringUtils.hasText(source)) {
            if (Pattern.matches(dateTimeReg, source)) {
                try {
                    Date parse = new SimpleDateFormat(TimeConstant.DATE_TIME).parse(source);
                    return parse;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (Pattern.matches(dateReg, source)) {
                try {
                    Date parse = new SimpleDateFormat(TimeConstant.DATE).parse(source);
                    return parse;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (Pattern.matches(dateTimeMinuteReg, source)) {
                try {
                    Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(source);
                    return parse;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                //前端直接传utc格式的时间
                //Mon Oct 14 2019 18:23:06 GMT 0800
                if (source.endsWith("GMT 0800")) {
                    DateFormat d = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", Locale.ENGLISH);
                    try {
                        return d.parse(source.substring(0, source.lastIndexOf("GMT 0800") - 1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
