//package com.norman.config;
//
//import com.norman.listener.JobsListener;
//import com.norman.listener.TriggerListener;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.PropertiesFactoryBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
//import java.io.IOException;
//import java.util.Properties;
//
///**
// * Created by tianfei on 2018/9/17.
// */
////@Configuration
//public class QuartzConfig {
//
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Autowired
//    private TriggerListener triggerListener;
//
//    @Autowired
//    private JobsListener jobsListener;
//
//    @Bean
//    public Properties quartzProperties() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz1.properties"));
//        propertiesFactoryBean.afterPropertiesSet();
//        return propertiesFactoryBean.getObject();
//    }
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setOverwriteExistingJobs(true);
//
//        factory.setQuartzProperties(quartzProperties());
//
//        //Register listeners to get notification on Trigger misfire etc
//        factory.setGlobalTriggerListeners(triggerListener);
//        factory.setGlobalJobListeners(jobsListener);
//
//        QuartzJobFacotry jobFactory = new QuartzJobFacotry();
//        jobFactory.setApplicationContext(applicationContext);
//        factory.setJobFactory(jobFactory);
//
//        return factory;
//    }
//
//    @Bean
//    public Scheduler scheduler() throws IOException, SchedulerException {
//        final SchedulerFactoryBean schedulerFactoryBean = schedulerFactoryBean();
//
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        scheduler.start();
//        return scheduler;
//    }
//}
