package com.norman.service;

import com.norman.model.ResponseMsg;
import com.norman.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2019/11/11 9:06 AM.
 */
@Component
@Slf4j
public class TaskQueue {

    private static final int QUEUE_LENGTH = 10;

    private BlockingQueue<Task> queue = new LinkedBlockingDeque<>(QUEUE_LENGTH);

    private int taskId = 0;

    /**
     * 加入任务
     *
     * @param deferredResult
     */
    public void put(DeferredResult<ResponseMsg<String>> deferredResult) {

        taskId++;

        log.info("任务加入队列，id为：{}", taskId);

        queue.offer(new Task(taskId, deferredResult));

    }

    /**
     * 获取任务
     *
     * @return
     * @throws InterruptedException
     */
    public Task take() throws InterruptedException {

        Task task = queue.poll();

        if (task != null) {
            log.info("获得任务:{}", task);
        }

        return task;

    }
}
