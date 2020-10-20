package com.github.chenlijia1111.generalModule.common.schedules;

import com.github.chenlijia1111.generalModule.service.VerifyCodeServiceI;
import com.github.chenlijia1111.generalModule.utils.SpringContextHolder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 清理过期验证码
 * 如果需要清除，将当前 Job 在项目初始化的时候创建即可
 * {@code
 *      String groupName = "autoClearCode";
 *
 *      //每天3点运行
 *      Trigger autoClearTrigger = TriggerUtil.createCronTrigger("0 0 3 * * ?", "autoClearCodeTrigger", groupName);
 *
 *      TimerTaskUtil.doTask(autoClearTrigger, AutoClearLimitVerifyCode.class, AutoClearLimitVerifyCode.class.getSimpleName(), groupName);
 * }
 *
 * @author Chen LiJia
 * @since 2020/2/26
 */
public class AutoClearLimitVerifyCode implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        VerifyCodeServiceI verifyCodeService = SpringContextHolder.getBean(VerifyCodeServiceI.class);
        verifyCodeService.clearExpireCode();
    }
}
