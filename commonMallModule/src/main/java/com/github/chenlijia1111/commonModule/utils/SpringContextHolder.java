package com.github.chenlijia1111.commonModule.utils;

import com.github.chenlijia1111.utils.core.LogUtil;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出 ApplicationContext.
 *
 * @author 陈礼佳
 * @date 2018-5-29 下午1:25:40
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    private static Logger logger = new LogUtil(SpringContextHolder.class);

    /**
     * 取得存储在静态变量中的 ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }


    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        if (logger.isDebugEnabled()) {
            logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        }
        applicationContext = null;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.info("注入ApplicationContext到SpringContextHolder:{}", applicationContext);

        if (SpringContextHolder.applicationContext != null) {
            logger.info("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextHolder.applicationContext);
        }

        SpringContextHolder.applicationContext = applicationContext; // NOSONAR
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if (null == applicationContext) {
            logger.error("applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
        }
    }

    /**
     * 根据类型获取所有实现
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
        Map<String, T> beansOfType = applicationContext.getBeansOfType(requiredType);
        return beansOfType;
    }


    /**
     * 获取配置信息
     *
     * @param key 如：server.port
     * @return
     */
    public static String getProperties(String key) {
        Environment environment = applicationContext.getBean(Environment.class);
        if (Objects.nonNull(environment)) {
            return environment.getProperty(key);
        }
        return null;
    }

}
