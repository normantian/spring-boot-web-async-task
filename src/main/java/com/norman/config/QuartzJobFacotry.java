//package com.norman.config;
//
//import org.quartz.spi.TriggerFiredBundle;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.scheduling.quartz.SpringBeanJobFactory;
//
///**
// * Created by tianfei on 2018/9/17.
// */
//public class QuartzJobFacotry extends SpringBeanJobFactory implements ApplicationContextAware {
//
//    private transient AutowireCapableBeanFactory beanFactory;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        beanFactory = applicationContext.getAutowireCapableBeanFactory();
//    }
//
//    @Override
//    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
//        final Object job = super.createJobInstance(bundle);
//        beanFactory.autowireBean(job);
//        return job;
//    }
//}
