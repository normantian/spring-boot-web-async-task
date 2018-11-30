package com.norman.service.impl;

import com.norman.model.TaskInfo;
import com.norman.service.JobService;
import com.norman.util.ScheduleUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianfei on 2018/9/17.
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService, InitializingBean {


    @Autowired
    private Scheduler scheduler;

    @Override
    public void afterPropertiesSet() throws Exception {
//        Date startAt = DateTime.now().plusMinutes(1).toDate();
//        log.info("test job will start at " + startAt);
//        this.scheduleOneTimeJob("testJob", "test-group", SimpleJob.class, startAt);
    }

    @Override
    public List<TaskInfo> listAllJobs() {
        List<TaskInfo> list = new ArrayList<>();

        try {
            for (String groupJob : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(groupJob))) {
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    for (Trigger trigger : triggers) {
                        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                        String cronExpression = "";
                        Date nextFireTime = null;

                        if (trigger instanceof CronTrigger) {
                            CronTrigger cronTrigger = (CronTrigger) trigger;
                            cronExpression = cronTrigger.getCronExpression();
                            nextFireTime = cronTrigger.getNextFireTime();
                        } else if (trigger instanceof SimpleTrigger) {
                            SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
                            nextFireTime = simpleTrigger.getNextFireTime();
                        }
                        TaskInfo info = new TaskInfo();
                        info.setJobName(jobKey.getName());
                        info.setJobGroup(jobKey.getGroup());
                        info.setJobDescription(jobDetail.getDescription());
                        info.setJobStatus(triggerState.name());
                        info.setCronExpression(cronExpression);
                        info.setNextFireDate(nextFireTime);

                        list.add(info);
                    }
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public boolean scheduleOneTimeJob(String jobName, String jobGroup, Class<? extends Job> jobClass, Date date) {
        return this.scheduleOneTimeJob(jobName, jobGroup, jobClass, date, null, true);
    }

    /**
     * 验证是否存在
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    private boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }

    @Override
    public boolean scheduleOneTimeJob(String jobName, String jobGroup, Class<? extends Job> jobClass,
                                      Date startTime, Map<String, ?> paramMap, boolean requestRecovery) {
        final JobKey jobKey = ScheduleUtils.getJobKey(jobName, jobGroup);

        final TriggerKey triggerKey = ScheduleUtils.getTriggerKey(jobName, jobGroup);

        JobDataMap jobDataMap = CollectionUtils.isEmpty(paramMap) ? new JobDataMap(new HashMap<>()) : new JobDataMap(paramMap);

        JobDetail jobDetail = ScheduleUtils.createJobDetail(jobKey, jobClass, jobDataMap, requestRecovery);

        final Trigger singleTrigger = ScheduleUtils.createSingleTrigger(triggerKey, startTime);

        final Date date = ScheduleUtils.create(scheduler, jobDetail, singleTrigger);

        if (date != null) {
            log.info("Job with key jobKey: {} and group : {} scheduled successfully for date :{}", jobName, jobGroup, date);
        }

        return (date != null);
    }

    @Override
    public boolean scheduleCronJob(String jobName, String jobGroup, Class<? extends Job> jobClass, Date startTime, String cronExpression, Map<String, Object> paramMap, boolean requestRecovery) {
        final JobKey jobKey = ScheduleUtils.getJobKey(jobName, jobGroup);
        final TriggerKey triggerKey = ScheduleUtils.getTriggerKey(jobName, jobGroup);

        JobDataMap jobDataMap = CollectionUtils.isEmpty(paramMap) ? new JobDataMap(new HashMap<>()) : new JobDataMap(paramMap);

        JobDetail jobDetail = ScheduleUtils.createJobDetail(jobKey, jobClass, jobDataMap, requestRecovery);

        log.info("task will start at {}", startTime);
        Trigger cronTrigger = ScheduleUtils.createCronTrigger(triggerKey, startTime, cronExpression);

        ScheduleUtils.create(scheduler, jobDetail, cronTrigger);

        return false;
    }

    @Override
    public boolean deleteJob(String jobName, String jobGroup) {
        boolean deleted = ScheduleUtils.deleteScheduleJob(scheduler, jobName, jobGroup);
        log.info("Job with key jobKey: {} and group : {} delete successfully.", jobName, jobGroup, deleted ? "successfully" : "failed");
        return deleted;
    }

    @Override
    public void pauseJob(String jobName, String jobGroup) {
        ScheduleUtils.pauseJob(scheduler, jobName, jobGroup);
    }

    @Override
    public void resumeJob(String jobName, String jobGroup) {
        ScheduleUtils.resumeJob(scheduler, jobName, jobGroup);
    }

    @Override
    public void runNow(String jobName, String jobGroup, Map<String, Object> paramMap) {
        JobKey jobKey = ScheduleUtils.getJobKey(jobName, jobGroup);
        JobDataMap jobDataMap = new JobDataMap(paramMap);
        ScheduleUtils.run(scheduler, jobKey, jobDataMap);
    }

    @PreDestroy
    public void destory(){
        log.info("quartz job service  destory method  using  @PreDestroy.....");
    }

    @PostConstruct
    public void init(){
        log.info("quartz job service  init method  using  @PostConstruct.....");
    }


}
