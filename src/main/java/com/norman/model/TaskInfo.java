package com.norman.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tianfei on 2018/9/17.
 */
@Data
public class TaskInfo implements Serializable {

    private int id = 0;

    /**任务名称*/
    private String jobName;

    /**任务分组*/
    private String jobGroup;

    /**任务描述*/
    private String jobDescription;

    /**任务状态*/
    private String jobStatus;

    /**任务表达式*/
    private String cronExpression;

    private String createTime;

    private Date nextFireDate;

}
