package com.norman.quartz.example5;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/25 2:47 PM.
 */
public class StatefulDumbJob implements Job {

    public static final String NUM_EXECUTIONS  = "NumExecutions";

    public static final String EXECUTION_DELAY = "ExecutionDelay";

    public StatefulDumbJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("---" + context.getJobDetail().getKey() + " executing.[" + new Date() + "]");

        JobDataMap map = context.getJobDetail().getJobDataMap();

        int executeCount = 0;
        if (map.containsKey(NUM_EXECUTIONS)) {
            executeCount = map.getInt(NUM_EXECUTIONS);
        }

        executeCount++;

        map.put(NUM_EXECUTIONS, executeCount);

        long delay = 5000l;
        if (map.containsKey(EXECUTION_DELAY)) {
            delay = map.getLong(EXECUTION_DELAY);
        }

        try {
            Thread.sleep(delay);
        } catch (Exception ignore) {
            //
        }

        System.err.println("  -" + context.getJobDetail().getKey() + " complete (" + executeCount + ").");

    }
}
