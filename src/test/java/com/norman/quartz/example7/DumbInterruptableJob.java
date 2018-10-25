package com.norman.quartz.example7;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/25 6:11 PM.
 */
public class DumbInterruptableJob implements InterruptableJob {

    // logging services
    private static Logger _log = LoggerFactory.getLogger(DumbInterruptableJob.class);

    // has the job been interrupted?
    private boolean _interrupted = false;

    // job name
    private JobKey _jobKey = null;

    public DumbInterruptableJob() {
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        _log.info("---" + _jobKey + "  -- INTERRUPTING --");
        _interrupted = true;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        _jobKey = context.getJobDetail().getKey();
        _log.info("---- " + _jobKey + " executing at " + new Date());

        try {
            // main job loop... see the JavaDOC for InterruptableJob for discussion...
            // do some work... in this example we are 'simulating' work by sleeping... :)

            for (int i = 0; i < 4; i++) {
                try {
                    Thread.sleep(1000L);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }

                // periodically check if we've been interrupted...
                if(_interrupted) {
                    _log.info("--- " + _jobKey + "  -- Interrupted... bailing out!");
                    return; // could also choose to throw a JobExecutionException
                    // if that made for sense based on the particular
                    // job's responsibilities/behaviors
                }
            }

        } finally {
            _log.info("---- " + _jobKey + " completed at " + new Date());
        }
    }
}
