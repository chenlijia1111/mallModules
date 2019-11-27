package com.github.chenlijia1111.fightGroup.common.schdule;

import com.github.chenlijia1111.commonModule.utils.SpringContextHolder;
import com.github.chenlijia1111.fightGroup.service.FightGroupServiceI;
import com.github.chenlijia1111.fightGroup.service.impl.FightGroupServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 监听拼团团解散
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/27 0027 上午 9:36
 **/
public class ListenFightGroupDisband implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //拼团-团
        FightGroupServiceI fightGroupService = SpringContextHolder.getBean(FightGroupServiceImpl.class);
        //监听拼团团解散
        fightGroupService.scheduleCancelFightGroup();
    }
}
