package com.norman.config;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/9/30 下午5:13.
 */
@Configuration
public class MySchedulerFactoryBeanCustomizer {

    @Bean
    public SchedulerFactoryBeanCustomizer MySchedulerFactoryBeanCustomizer() {
        return (schedulerFactoryBean) -> {
            schedulerFactoryBean.setSchedulerName("clusteredScheduler");
            schedulerFactoryBean.setOverwriteExistingJobs(true);
        };
    }

}
