package com.norman.quartz.example9;

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
 * @date 2018/10/29 10:42 AM.
 */
public class SimpleJob2 implements Job {
    private static Logger _log = LoggerFactory.getLogger(SimpleJob2.class);

    /**
     * Empty constructor for job initialization
     */
    public SimpleJob2() {
    }

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("SimpleJob2 says: " + jobKey + " executing at " + new Date());
    }
}
