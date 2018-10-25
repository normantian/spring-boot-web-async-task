package com.norman.quartz.example6;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/25 3:00 PM.
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class BadJob1 implements Job {

    // Logging
    private static Logger _log = LoggerFactory.getLogger(BadJob1.class);
    private int calculation;

    public BadJob1() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        int denominator = dataMap.getInt("denominator");
        _log.info("---" + jobKey + " executing at " + new Date() + " with denominator " + denominator);

        // a contrived example of an exception that
        // will be generated by this job due to a
        // divide by zero error (only on first run)
        try {
            calculation = 4815 / denominator;
        } catch (Exception e) {
            _log.info("--- Error in job!");
            JobExecutionException e2 = new JobExecutionException(e);

            // fix denominator so the next time this job run
            // it won't fail again
            dataMap.put("denominator", "1");

            // this job will refire immediately
            e2.setRefireImmediately(true);
            throw e2;
        }

        _log.info("---" + jobKey + " completed at " + new Date());
    }
}
