package com.norman.quartz;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by tianfei on 2018/9/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SchedulerExample {

    @Autowired
    private Scheduler scheduler;

    @Test
    public void test() throws SchedulerException, InterruptedException {{

        // Grab the Scheduler instance from the Factory
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        DateTime dt = new DateTime();
        final Date start = dt.plusSeconds(5).toDate();
        final Date start2 = dt.plusSeconds(8).toDate();

        System.out.println("task will start "+ start);

        //定义一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") //定义name/group
                //.startNow()//一旦加入scheduler，立即生效
                .startAt(start)
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule() //使用SimpleTrigger
//                        .withIntervalInSeconds(30) //每隔一秒执行一次
//                        .repeatForever()) //一直执行，奔腾到老不停歇
                .usingJobData("name","lisa")
                .build();

        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") //定义name/group
                //.startNow()//一旦加入scheduler，立即生效
                .startAt(start2)
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule() //使用SimpleTrigger
//                        .withIntervalInSeconds(30) //每隔一秒执行一次
//                        .repeatForever()) //一直执行，奔腾到老不停歇
                .usingJobData("name","mongo")
                .build();


        //定义一个JobDetail
        JobDetail job = JobBuilder.newJob(HelloQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                .withIdentity("job1", "group1") //定义name/group
                .usingJobData("name", "norman tian") //定义属性
                .build();

        JobDetail job2 = JobBuilder.newJob(HelloQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                .withIdentity("job2", "group1") //定义name/group
                .usingJobData("name", "norman tian") //定义属性
                .build();

        //加入这个调度
        System.out.println("add task time is " + new Date());
        scheduler.scheduleJob(job, trigger);

        scheduler.scheduleJob(job2, trigger2);


        // and start it off
        scheduler.start();

        //运行一段时间后关闭
        Thread.sleep(14000);
        scheduler.shutdown(true);
    }

    }
    public static void main(String[] args) throws SchedulerException, InterruptedException {

        // Grab the Scheduler instance from the Factory
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        DateTime dt = new DateTime();
        final Date start = dt.plusSeconds(20).toDate();

        System.out.println("task will start "+ start);

        //定义一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1") //定义name/group
                //.startNow()//一旦加入scheduler，立即生效
                .startAt(start)
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule() //使用SimpleTrigger
//                        .withIntervalInSeconds(30) //每隔一秒执行一次
//                        .repeatForever()) //一直执行，奔腾到老不停歇
                .build();

        //定义一个JobDetail
        JobDetail job = JobBuilder.newJob(HelloQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                .withIdentity("job1", "group1") //定义name/group
                .usingJobData("name", "norman tian") //定义属性
                .build();

        //加入这个调度
        System.out.println("add task time is " + new Date());
        scheduler.scheduleJob(job, trigger);


        // and start it off
        scheduler.start();

        //运行一段时间后关闭
        Thread.sleep(50000);
        scheduler.shutdown(true);
    }
}
