package com.github.chenlijia1111.generalModule.common.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * 初始化方法
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/27 0027 上午 9:19
 **/
@Service
public class GeneralInitFunction implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            //执行方法
        }
    }


}
