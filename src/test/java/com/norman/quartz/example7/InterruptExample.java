package com.norman.quartz.example7;

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
 * @date 2018/10/25 6:09 PM.
 */
public class InterruptExample {

    public void run() throws Exception {
        final Logger log = LoggerFactory.getLogger(InterruptExample.class);

        log.info("------- Initializing ----------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete -----------");

        log.info("------- Scheduling Jobs -------------------");

        // get a "nice round" time a few seconds in the future...
        Date startTime = nextGivenSecondDate(null, 15);

        JobDetail job = newJob(DumbInterruptableJob.class).withIdentity("interruptableJob1", "group1").build();

        SimpleTrigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();

        Date ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // start up the scheduler (jobs do not start to fire until
        // the scheduler has been started)
        sched.start();
        log.info("------- Started Scheduler -----------------");

        log.info("------- Starting loop to interrupt job every 7 seconds ----------");
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(7000L);
                // tell the scheduler to interrupt our job
                sched.interrupt(job.getKey());
            } catch (Exception e) {
                //
            }
        }

        log.info("------- Shutting Down ---------------------");

        sched.shutdown(true);

        log.info("------- Shutdown Complete -----------------");
        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) throws Exception {
        InterruptExample example = new InterruptExample();
        example.run();
    }
}
