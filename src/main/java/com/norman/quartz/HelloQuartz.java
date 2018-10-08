package com.norman.quartz;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by tianfei on 2018/9/17.
 */
@Component
public class HelloQuartz extends QuartzJobBean {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail detail = jobExecutionContext.getJobDetail();
        final Trigger trigger = jobExecutionContext.getTrigger();
        String triggerParam = trigger.getJobDataMap().getString("name");
        String name = detail.getJobDataMap().getString("name");
        System.out.println("say hello to " + name + " trigger param is "+ triggerParam  + " at " + new Date());

        final JobDataMap map = jobExecutionContext.getMergedJobDataMap();


        System.out.println("merge job data map : " + map.getWrappedMap().toString());

        System.out.println();
//        stringRedisTemplate.opsForValue().set("name", name);
    }
}
