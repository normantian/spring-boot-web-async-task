package com.norman.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2020/6/18 9:33 AM.
 */
@Slf4j
@Service
public class DemoService {

    public Integer execute01() {
        log.info("[execute01]");
        sleep(10);
        return 1;
    }

    @Async
    public Integer execute01Async() {
        return this.execute01();
    }

    public Integer execute02() {
        log.info("[execute02]");
        sleep(5);
        return 2;
    }

    @Async
    public Integer execute02Async() {
        return this.execute02();
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public Future<Integer> execute01AsyncWithFuture() {
        return AsyncResult.forValue(this.execute01());
    }

    @Async
    public Future<Integer> execute02AsyncWithFuture() {
        return AsyncResult.forValue(this.execute02());
    }


    @Async
    public Integer zhaoDaoNvPengYou(Integer a, Integer b) {
        throw new RuntimeException("程序员不需要女朋友");
    }

}
