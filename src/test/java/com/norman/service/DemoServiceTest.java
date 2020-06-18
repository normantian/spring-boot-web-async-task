package com.norman.service;

import com.norman.BaseSpringBootTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2020/6/18 9:38 AM.
 */
@Slf4j
public class DemoServiceTest extends BaseSpringBootTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void execute01() throws Exception {
        long now = System.currentTimeMillis();
        log.info("[task01][开始执行]");

        demoService.execute01();
        demoService.execute02();

        log.info("[task01][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);

        //串行执行 [task01][结束执行，消耗时长 15011 毫秒]
    }

    @Test
    public void execute02() throws Exception {

        long now = System.currentTimeMillis();
        log.info("[task01][开始执行]");

        demoService.execute01Async();
        demoService.execute02Async();

        log.info("[task01][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);

//        DemoService 的两个方法，异步执行，所以主线程只消耗 39毫秒左右。注意，实际这两个方法，并没有执行完成。
//        DemoService 的两个方法，都在异步线程池中执行。
    }

    @Test
    public void task03() throws ExecutionException, InterruptedException {
        long now = System.currentTimeMillis();
        log.info("[task03][开始执行]");

        // 执行任务
        Future<Integer> execute01Result = demoService.execute01AsyncWithFuture();
        Future<Integer> execute02Result = demoService.execute02AsyncWithFuture();
        // 阻塞等待结果
        execute01Result.get();
        execute02Result.get();

        log.info("[task03][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);

        //[task03][结束执行，消耗时长 10011 毫秒]  执行时间由消耗最长的异步调用逻辑所决定。
    }


    @Test
    public void zhaoDaoNvPengYou() throws Exception {
    }

}