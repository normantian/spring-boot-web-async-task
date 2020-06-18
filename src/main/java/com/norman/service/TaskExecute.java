package com.norman.service;

import com.norman.model.ResponseMsg;
import com.norman.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2019/11/11 9:19 AM.
 */
@Component
@Slf4j
public class TaskExecute {
    private static final Random random = new Random();

    //默认随机结果的长度
    private static final int DEFAULT_STR_LEN = 10;

    //用于生成随机结果
    private static final String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Autowired
    private TaskQueue taskQueue;

    /**
     * 初始化启动
     */
    @PostConstruct
    public void init() {

        log.info("开始持续处理任务");

        new Thread(this::execute).start();


    }

    /**
     * 持续处理
     * 返回执行结果
     */
    private void execute() {

        while (true) {

            try {

                //取出任务
                Task task;

                synchronized (taskQueue) {

                    task = taskQueue.take();

                }

                if (task != null) {

                    //设置返回结果
                    String randomStr = getRandomStr(DEFAULT_STR_LEN);

                    ResponseMsg<String> responseMsg = new ResponseMsg<String>(0, "success", randomStr);

                    log.info("返回结果:{}", responseMsg);

                    task.getTaskResult().setResult(responseMsg);
                }

                int time = random.nextInt(10);

                log.info("处理间隔：{}秒", time);

                Thread.sleep(time * 1000L);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }

    /**
     * 获取长度为len的随机串
     *
     * @param len
     * @return
     */
    private String getRandomStr(int len) {

        int maxInd = str.length();

        StringBuilder sb = new StringBuilder();

        int ind;

        for (int i = 0; i < len; i++) {

            ind = random.nextInt(maxInd);

            sb.append(str.charAt(ind));

        }

        return String.valueOf(sb);

    }


}
