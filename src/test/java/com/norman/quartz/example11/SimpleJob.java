package com.norman.quartz.example11;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/29 11:20 AM.
 */
public class SimpleJob implements Job {

    private static Logger _log = LoggerFactory.getLogger(SimpleJob.class);

    // job parameter
    public static final String DELAY_TIME = "delay time";

    /**
     * Empty constructor for job initilization
     */
    public SimpleJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("Executing job: " + jobKey + " executing at " + new Date());

        // wait for a period of time
        long delayTime = context.getJobDetail().getJobDataMap().getLong(DELAY_TIME);
        try {
            Thread.sleep(delayTime);
        } catch (Exception e) {
            //
        }

        _log.info("Finished Executing job: " + jobKey + " at " + new Date());
    }
}
