package com.norman.model;

import lombok.Data;

/**
 * @version 1.0.0
 * @description </br>
 * @auther tianfei
 * @date 2018/9/26 下午11:44.
 */
@Data
public class SimpleTask {

    /**任务名称*/
    private String jobName;

    /**任务分组*/
    private String jobGroup;

    /** 延时*/
    private Long delay;



}
