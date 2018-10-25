package com.norman.quartz.example5;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/25 2:49 PM.
 */
public class MisfireExample {

    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(MisfireExample.class);

        log.info("------- Initializing -------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete -----------");

        log.info("------- Scheduling Jobs -----------");

        // jobs can be scheduled before start() has been called

        // get a "nice round" time a few seconds in the future...
        Date startTime = nextGivenSecondDate(null, 15);

        // statefulJob1 will run every three seconds
        // (but it will delay for ten seconds)
        JobDetail job = newJob(StatefulDumbJob.class).withIdentity("statefulJob1", "group1")
                .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L).build();

        SimpleTrigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(3).repeatForever()).build();

        Date ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // statefulJob2 will run every three seconds
        // (but it will delay for ten seconds - and therefore purposely misfire after a few iterations)
        job = newJob(StatefulDumbJob.class).withIdentity("statefulJob2", "group1")
                .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L).build();

        trigger = newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(3).repeatForever()
                        .withMisfireHandlingInstructionNowWithExistingCount()) // set misfire instructions
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        log.info("------- Starting Scheduler ----------------");

        // jobs don't start firing until start() has been called...
        sched.start();

        log.info("------- Started Scheduler -----------------");

        try {
            // sleep for ten minutes for triggers to file....
            Thread.sleep(600L * 1000L);
        } catch (Exception e) {
            //
        }

        log.info("------- Shutting Down ---------------------");

        sched.shutdown(true);

        log.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) throws Exception  {
        MisfireExample example = new MisfireExample();
        example.run();
    }
}
