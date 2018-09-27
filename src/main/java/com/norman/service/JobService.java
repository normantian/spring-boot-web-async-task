package com.norman.service;

import com.norman.model.TaskInfo;
import org.quartz.Job;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tianfei on 2018/9/17.
 */
public interface JobService {
    /**
     * Get all jobs
     *
     * @return
     */
    List<TaskInfo> listAllJobs();

    boolean scheduleOneTimeJob(String jobName, String jobGroup, Class<? extends Job> jobClass, Date date);

    boolean scheduleOneTimeJob(String jobName, String jobGroup, Class<? extends Job> jobClass,
                               Date startTime, Map<String, ?> paramMap, boolean requestRecovery);


    boolean scheduleCronJob(String jobName, String jobGroup, Class<? extends Job> jobClass,
                            Date startTime, String cronExpression, Map<String, Object> paramMap, boolean requestRecovery);

    boolean deleteJob(String jobName, String jobGroup);

    void pauseJob(String jobName, String jobGroup);

    void resumeJob(String jobName, String jobGroup);

    void runNow(String jobName, String jobGroup, Map<String, Object> paramMap);

}

