package com.github.chenlijia1111.fightGroup.common.init;

import com.github.chenlijia1111.fightGroup.common.schdule.ListenFightGroupDisband;
import com.github.chenlijia1111.utils.timer.TimerTaskUtil;
import com.github.chenlijia1111.utils.timer.TriggerUtil;
import org.quartz.Trigger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * 初始化方法
 * 定时监听拼团-团解散
 * 查询到达时间限制且还没有拼团成功的拼团,将拼团的状态修改为拼团失败
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/27 0027 上午 9:19
 **/
@Service
public class InitFunction implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            //执行方法
            addCancelFightGroupTask();
        }
    }

    /**
     * 添加任务-监听团解散
     * 每天的0点执行
     *
     * @return void
     * @since 上午 9:52 2019/11/27 0027
     **/
    private void addCancelFightGroupTask() {
        String groupName = "fightGroup";
        String name = "cancelFightGroup";
        Trigger trigger = TriggerUtil.createCronTrigger("0 0 0 * * ? *", name, groupName);

        TimerTaskUtil.doTask(trigger, ListenFightGroupDisband.class, name, groupName);
    }
}
