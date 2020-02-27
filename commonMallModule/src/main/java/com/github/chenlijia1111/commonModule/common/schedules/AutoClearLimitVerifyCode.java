package com.github.chenlijia1111.commonModule.common.schedules;

import com.github.chenlijia1111.commonModule.service.VerifyCodeServiceI;
import com.github.chenlijia1111.commonModule.utils.SpringContextHolder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 清理过期验证码
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
