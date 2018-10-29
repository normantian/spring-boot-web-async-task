package com.norman.quartz.example12;

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
 * @date 2018/10/29 11:46 AM.
 */
public class SimpleJob implements Job {

    public static final String MESSAGE = "msg";

    private static Logger _log = LoggerFactory.getLogger(SimpleJob.class);

    public SimpleJob() {
    }


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();

        String message = (String) context.getJobDetail().getJobDataMap().get(MESSAGE);

        _log.info("SimpleJob: " + jobKey + " executing at " + new Date());
        _log.info("SimpleJob: msg: " + message);
    }
}
