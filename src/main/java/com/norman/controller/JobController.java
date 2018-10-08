package com.norman.controller;

import com.norman.model.CronTask;
import com.norman.model.SimpleTask;
import com.norman.model.TaskInfo;
import com.norman.quartz.SimpleJob;
import com.norman.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0.0
 * @description </br>
 * @auther tianfei
 * @date 2018/9/26 下午11:41.
 */
@RestController
@Slf4j
@RequestMapping("/task")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @PostMapping("/simpleTask")
    public String saveSimpleTask(@RequestBody SimpleTask simpleTask) {
        final Long delay = simpleTask.getDelay();
        DateTime startAt = DateTime.now().plus(delay.longValue());

        jobService.scheduleOneTimeJob(simpleTask.getJobName(), simpleTask.getJobGroup(), SimpleJob.class, startAt.toDate());

        return "success";
    }

    @PostMapping("/cronTask")
    public String saveCronTask(@RequestBody CronTask cronTask) {
        final Long delay = cronTask.getDelay();
        Date startAt = null;
        if (delay.longValue() > 0L) {
            startAt = DateTime.now().plus(delay.longValue()).toDate();
        }

        jobService.scheduleCronJob(cronTask.getJobName(), cronTask.getJobGroup(), SimpleJob.class,
                startAt, cronTask.getCronExpression(), null, true);

        return "success";
    }

    @DeleteMapping("/cronTask/{groupName}/{jobName}")
    public String deleteTask(@PathVariable(value = "groupName") String groupName,
                             @PathVariable(value = "jobName") String jobName) {
        return jobService.deleteJob(jobName, groupName) ? "success" : "failed";

    }

    @PutMapping("/cronTask/pause")
    public String pauseTask(@RequestBody SimpleTask simpleTask) {
        jobService.pauseJob(simpleTask.getJobName(), simpleTask.getJobGroup());
        return "success";
    }

    @PutMapping("/cronTask/resume")
    public String resumeTask(@RequestBody SimpleTask simpleTask) {
        jobService.resumeJob(simpleTask.getJobName(), simpleTask.getJobGroup());
        return "success";
    }

    @GetMapping("/tasks")
    public List<TaskInfo> listAllTasks(){
        return jobService.listAllJobs();
    }
}
