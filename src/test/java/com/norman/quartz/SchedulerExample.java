package com.norman.quartz;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianfei on 2018/9/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class SchedulerExample {

    @Autowired
    private Scheduler scheduler;

    @Test
    public void runOnce() throws SchedulerException, InterruptedException {

//        DateTime dt = new DateTime();
//        final Date start = dt.plusMinutes(1).plusSeconds(20).toDate();

        LocalDateTime now = LocalDateTime.now();
        final LocalDateTime after1min = now.plusMinutes(0).plusSeconds(20);

        final LocalDateTime after2min = now.plusMinutes(0).plusSeconds(30);
        final Date start = new Date(after1min.atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli());

        final Date start2 = new Date(after2min.atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli());

        System.out.println("task will start " + start);

        //表达式调度构建器
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow();
        //定义一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") //定义name/group
                .startAt(start)
//                .withSchedule(scheduleBuilder)
                .usingJobData("name", "hana")
                .build();

        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") //定义name/group
                .startAt(start2)
//                .withSchedule(scheduleBuilder)
                .usingJobData("name", "norman")
                .build();

        JobDetail jobDetail = JobBuilder.newJob(HelloQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                .withIdentity("jobOnce", "once") //定义name/group
//                .usingJobData("name", "lisa") //定义属性
                .build();

        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.rescheduleJob(trigger.getKey(), trigger2);

//        Thread.sleep(30000L);
        TimeUnit.MINUTES.sleep(2);
        scheduler.shutdown(true);


    }

    @Test
    public void test() throws SchedulerException, InterruptedException {


        // Grab the Scheduler instance from the Factory
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        LocalDateTime now = LocalDateTime.now();
        final LocalDateTime after1min = now.plusMinutes(1);
        final Date start = new Date(after1min.atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli());


        DateTime dt = new DateTime();
//        final Date start = dt.plusMinutes(1).toDate();
        final Date start2 = dt.plusMinutes(2).toDate();

        System.out.println("task will start " + start);

        //表达式调度构建器
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow();
        //定义一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") //定义name/group
                //.startNow()//一旦加入scheduler，立即生效
                .startAt(start)
                .withSchedule(scheduleBuilder)
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule() //使用SimpleTrigger
//                        .withIntervalInSeconds(30) //每隔一秒执行一次
//                        .repeatForever()) //一直执行，奔腾到老不停歇
                .usingJobData("name", "lisa")
                .build();

        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group2") //定义name/group
                //.startNow()//一旦加入scheduler，立即生效
                .startAt(start2)
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule() //使用SimpleTrigger
//                        .withIntervalInSeconds(30) //每隔一秒执行一次
//                        .repeatForever()) //一直执行，奔腾到老不停歇
                .usingJobData("name", "mongo")
                .build();

        scheduler.start();

//        scheduler.resumeAll();

        //定义一个JobDetail
        JobDetail job;

        final JobKey jobKey = JobKey.jobKey("job1", "group1");
        if (scheduler.checkExists(jobKey)) {
            job = scheduler.getJobDetail(jobKey);

            final List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
            if (triggersOfJob.size() > 0) {
                for (Trigger tempTrigger : triggersOfJob) {
                    System.out.println(tempTrigger);
                }
            }

//            System.out.println("job data map: " + job.getJobDataMap().toString());

            scheduler.triggerJob(jobKey);
        } else {
            job = JobBuilder.newJob(HelloQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                    .withIdentity(jobKey) //定义name/group
                    .usingJobData("name", "norman tian") //定义属性
//                    .storeDurably(true)
                    .build();
            scheduler.scheduleJob(job, trigger);
        }

        JobDetail job2 = JobBuilder.newJob(HelloQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                .withIdentity("job2", "group2") //定义name/group
                .usingJobData("name", "norman tian2") //定义属性
                .build();

        //加入这个调度
        System.out.println("add task time is " + new Date());

        scheduler.scheduleJob(job2, trigger2);

//        scheduler.pauseTrigger(trigger2.getKey());
//        scheduler.unscheduleJob(trigger2.getKey());
//        scheduler.deleteJob(job2.getKey());


        // and start it off


        //运行一段时间后关闭
        Thread.sleep(14000);
        scheduler.shutdown(true);

    }

    @Test
    public void test2() throws SchedulerException, InterruptedException {
        scheduler.start();

        final List<JobExecutionContext> currentlyExecutingJobs = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext jobExecutionContext : currentlyExecutingJobs) {
            System.out.println(jobExecutionContext);
        }
        scheduler.resumeAll();


        //运行一段时间后关闭
        Thread.sleep(20000);
        scheduler.shutdown(true);
    }

    @Test
    public void test3() throws SchedulerException, InterruptedException {
        scheduler.start();
//        scheduler.resumeAll();

        //运行一段时间后关闭
        //task will start Mon Sep 24 12:29:42 CST 2018
        TimeUnit.MINUTES.sleep(2);
        Thread.sleep(60000);
        scheduler.shutdown(true);
    }

    @Test
    public void testSimpleTrigger() throws SchedulerException {
        // get a "nice round" time a few seconds in the future...
        Date startTime = DateBuilder.nextGivenSecondDate(new Date(), 15);
        // job1 will only fire once at date/time "ts"
        JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity("job1", "group1").build();

        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(startTime).build();

        // schedule it to run!
        Date ft = scheduler.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        log.info("------- Waiting 1 minutes... ------------");
        try {
            // wait five minutes to show jobs
            Thread.sleep(60L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        log.info("------- Shutting Down ---------------------");

        scheduler.shutdown(true);

        log.info("------- Shutdown Complete -----------------");

        // display some stats about the schedule that just ran
        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");


    }

//    public static void main(String[] args) {
//        DateTime now = DateTime.now();
//
//        final DateTime twoDaysAgo = now.plusDays(2);
//        final DateTime twoDaysAgo2 = now.plusHours(24);
//
//
//        System.out.println(now);
//        System.out.println(twoDaysAgo);
//
//        System.out.println(Days.daysBetween(now, twoDaysAgo2).getDays());
//        System.out.println(Hours.hoursBetween(now, twoDaysAgo).getHours());
//
//        System.out.println(now.compareTo(twoDaysAgo));
//
//        System.out.println(twoDaysAgo.compareTo(now));
//    }


}
