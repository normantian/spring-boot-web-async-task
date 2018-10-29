package com.norman.quartz.example9;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/29 10:36 AM.
 */
public class Job1Listener implements JobListener {


    private static Logger _log = LoggerFactory.getLogger(Job1Listener.class);


    @Override
    public String getName() {
        return "job1_to_job2";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        _log.info("Job1Listener says: Job Is about to be executed.");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        _log.info("Job1Listener says: Job Execution was vetoed.");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        _log.info("Job1Listener says: Job was executed.");

        // Simple job #2
        JobDetail job2 = newJob(SimpleJob2.class).withIdentity("job2").build();

        Trigger trigger = newTrigger().withIdentity("job2Trigger").startNow().build();

        try {
            // schedule the job to run!
            context.getScheduler().scheduleJob(job2, trigger);
        } catch (SchedulerException e) {
            _log.warn("Unable to schedule job2!");
            e.printStackTrace();
        }
    }
}
