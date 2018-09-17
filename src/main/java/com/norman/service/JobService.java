package com.norman.service;

import com.norman.model.TaskInfo;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

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

    boolean scheduleOneTimeJob(String jobName, String jobGroup, Class<? extends QuartzJobBean> jobClass, Date date);

}