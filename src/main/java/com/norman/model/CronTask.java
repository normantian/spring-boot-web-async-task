package com.norman.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version 1.0.0
 * @description </br>
 * @auther tianfei
 * @date 2018/9/26 下午11:44.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CronTask extends SimpleTask {

    /**
     * cron 表达式
     */
    private String cronExpression;

}
