package com.norman.quartz.example9;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/29 10:45 AM.
 */
public class ListenerExample {

    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(ListenerExample.class);

        log.info("------- Initializing ----------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete -----------");

        log.info("------- Scheduling Jobs -------------------");

        // schedule a job to run immediately

        JobDetail job = newJob(SimpleJob1.class).withIdentity("job1").build();

        Trigger trigger = newTrigger().withIdentity("trigger1").startNow().build();

        // Set up the listener
        JobListener listener = new Job1Listener();
        Matcher<JobKey> matcher = KeyMatcher.keyEquals(job.getKey());
        sched.getListenerManager().addJobListener(listener, matcher);

        // schedule the job to run
        sched.scheduleJob(job, trigger);

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        log.info("------- Starting Scheduler ----------------");
        sched.start();

        // wait 30 seconds:
        // note: nothing will run
        log.info("------- Waiting 30 seconds... --------------");
        try {
            // wait 30 seconds to show jobs
            Thread.sleep(30L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        // shut down the scheduler
        log.info("------- Shutting Down ---------------------");
        sched.shutdown(true);
        log.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

    }

    public static void main(String[] args) throws Exception {
        ListenerExample example = new ListenerExample();
        example.run();

    }
}
