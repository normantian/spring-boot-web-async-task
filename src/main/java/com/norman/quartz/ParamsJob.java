package com.norman.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @version 1.0.0
 * @description </br>
 * @auther tianfei
 * @date 2018/9/25 下午1:58.
 */
@Slf4j
public class ParamsJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        JobKey jobKey = context.getJobDetail().getKey();
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        final Object paramObj = jobDataMap.get("param");


        log.info("SimpleJob says: " + jobKey + "[" + paramObj + "] executing at " + new Date());
    }
}
