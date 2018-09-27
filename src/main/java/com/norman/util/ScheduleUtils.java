package com.norman.util;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.utils.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @version 1.0.0
 * @description </br>
 * @auther tianfei
 * @date 2018/9/26 下午10:40.
 */

/**
 * @version 1.0.0
 * @description 定时任务工具类</br>
 * @auther tianfei
 * @date 2018/9/25 下午5:25.
 */
public abstract class ScheduleUtils {


    /**
     * 日志对象
     */
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleUtils.class);

    private static final String TRIGGER_PREFIX = "TRIGGER_";


    public static Date create(Scheduler scheduler, JobDetail jobDetail, Trigger trigger) {
        Date date = null;
        if (!isExistTrigger(scheduler, trigger.getKey())) {
            try {
                date = scheduler.scheduleJob(jobDetail, trigger);
            } catch (SchedulerException e) {
                LOG.error("创建定时任务{}失败,{}", jobDetail.getKey().toString(), e);
                e.printStackTrace();
            }
        } else {
            date = rescheduleJob(scheduler, trigger);
        }
        return date;
    }

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(String jobName, String group) {
        return TriggerKey.triggerKey(TRIGGER_PREFIX + jobName, group);
    }

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(String jobName) {
        return TriggerKey.triggerKey(TRIGGER_PREFIX + jobName, Key.DEFAULT_GROUP);
    }


    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(String jobName, String group) {
        return JobKey.jobKey(jobName, group);
    }

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(String jobName) {
        return JobKey.jobKey(jobName, Key.DEFAULT_GROUP);
    }

    /**
     * 暂停任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void pauseJob(Scheduler scheduler, String jobName, String jobGroup) {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            LOG.error("暂停定时任务{}失败,{}", jobKey.toString(), e);
            e.printStackTrace();
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, String jobName, String jobGroup) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            LOG.error("暂停定时任务{}失败,{}", jobKey.toString(), e);
        }
    }

    /**
     * 恢复所有定时任务
     *
     * @param scheduler
     */
    public static void resumeAllJobs(Scheduler scheduler) {
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {
            LOG.error("恢复所有定时任务失败,{}", e);
            e.printStackTrace();
        }
    }

    /**
     * 暂停所有定时任务
     *
     * @param scheduler
     */
    public static void pauseAllJobs(Scheduler scheduler) {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            LOG.error("暂停所有定时任务失败,{}", e);
            e.printStackTrace();
        }
    }

    /**
     * 检查job是否存在
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @return
     */
    public static boolean isExistJob(Scheduler scheduler, String jobName, String jobGroup) {
        final JobKey jobKey = getJobKey(jobName, jobGroup);
        return isExistJob(scheduler, jobKey);
    }

    public static boolean isExistJob(Scheduler scheduler, JobKey jobKey) {
        boolean isExists = false;
        try {
            isExists = scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            LOG.error("获取任务{}是否存在失败,{}", jobKey.toString(), e.getMessage());
            e.printStackTrace();
        }
        return isExists;
    }

    /**
     * 检查trigger是否存在
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @return
     */
    public static boolean isExistTrigger(Scheduler scheduler, String jobName, String jobGroup) {
        final TriggerKey triggerKey = getTriggerKey(jobName, jobGroup);
        return isExistTrigger(scheduler, triggerKey);
    }

    public static boolean isExistTrigger(Scheduler scheduler, TriggerKey triggerKey) {
        boolean isExists = false;
        try {
            isExists = scheduler.checkExists(triggerKey);
        } catch (SchedulerException e) {
            LOG.error("获取触发器{}是否存在失败,{}", triggerKey, e.getMessage());
            e.printStackTrace();
        }
        return isExists;
    }

    /**
     * 删除定时任务
     */
    public static boolean deleteScheduleJob(Scheduler scheduler, String jobName, String jobGroup) {
        final JobKey jobKey = getJobKey(jobName, jobGroup);
        return deleteScheduleJob(scheduler, jobKey);
    }

    /**
     * 删除定时任务
     *
     * @param scheduler
     * @param jobKey
     * @return
     */
    public static boolean deleteScheduleJob(Scheduler scheduler, JobKey jobKey) {
        boolean deleted = false;
        try {
            deleted = scheduler.deleteJob(jobKey);

        } catch (SchedulerException e) {
            LOG.error("暂停定时任务失败,{}", e);
            e.printStackTrace();
        }

//        run(scheduler, jobKey, new JobDataMap());
        return deleted;
    }

    /**
     * 重新执行任务。
     *
     * @param scheduler 调度器。
     * @param trigger   触发器。
     * @throws SchedulerException 调度器异常。
     */
    public static Date rescheduleJob(Scheduler scheduler, Trigger trigger) {
        Date date = null;
        try {
            date = scheduler.rescheduleJob(trigger.getKey(), trigger);
        } catch (SchedulerException e) {
            LOG.error("重新执行任务,{}", e);
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 立即执行任务
     *
     * @param scheduler
     * @param jobKey
     * @param dataMap
     */
    public static void run(Scheduler scheduler, JobKey jobKey, JobDataMap dataMap) {

        try {
            if (dataMap == null || dataMap.isEmpty()) {
                scheduler.triggerJob(jobKey);
            } else {
                scheduler.triggerJob(jobKey, dataMap);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一次性trigger
     *
     * @param triggerKey
     * @param startTime
     * @return
     */
    public static Trigger createSingleTrigger(TriggerKey triggerKey, Date startTime) {

        final SimpleScheduleBuilder simpleScheduleBuilder =
                SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .startAt(startTime)
                .withSchedule(simpleScheduleBuilder)
                .build();

        return trigger;
    }

    /**
     * 周期性trigger
     *
     * @param triggerKey
     * @param startTime
     * @param cronExpression
     * @return
     */
    public static Trigger createCronTrigger(TriggerKey triggerKey, Date startTime, String cronExpression) {
        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                .withMisfireHandlingInstructionDoNothing();

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger;
        if (startTime != null) {
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .startAt(startTime)
                    .withSchedule(scheduleBuilder).build();
        } else {
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();
        }

        return trigger;
    }

    /**
     * 创建jobDetail
     *
     * @param jobName
     * @param jobGroup
     * @param jobClass
     * @param jobDataMap
     * @param requestRecovery
     * @return
     */
    public static JobDetail createJobDetail(String jobName, String jobGroup, Class<? extends Job> jobClass, JobDataMap jobDataMap, boolean requestRecovery) {
        return createJobDetail(getJobKey(jobName, jobGroup), jobClass, jobDataMap, requestRecovery);
    }

    /**
     * 创建jobdetail
     *
     * @param jobKey
     * @param jobClass
     * @param jobDataMap
     * @param requestRecovery
     * @return
     */
    public static JobDetail createJobDetail(JobKey jobKey, Class<? extends Job> jobClass, JobDataMap jobDataMap, boolean requestRecovery) {
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobKey)
                .usingJobData(jobDataMap)
                .requestRecovery(requestRecovery)
                .build();

        return jobDetail;
    }
}

