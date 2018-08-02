package com.norman.controller;

import com.norman.service.WebAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by tianfei on 2018/7/3.
 * Spring Boot 提供的 WebAsyncTask 的异步编程 API。
 * 相比 @Async 注解，WebAsyncTask 提供更加健全的 超时处理 和 异常处理 支持
 */
@RestController
@Slf4j
public class WebAsyncController {

    private final WebAsyncService asyncService;
    private final static String ERROR_MESSAGE = "Task error";
    private final static String TIME_MESSAGE = "Task timeout";

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor executor;


    @Autowired
    public WebAsyncController(WebAsyncService asyncService) {
        this.asyncService = asyncService;
    }

    /**
     * http://localhost:8080/completion
     * 正常异步任务
     *
     * @return
     */
    @GetMapping("/completion")
    public WebAsyncTask<String> asyncTaskCompletion() {
        // 打印处理线程名
        log.info(String.format("请求处理线程：%s", Thread.currentThread().getName()));

        // 模拟开启一个异步任务，超时时间为10s
        WebAsyncTask<String> asyncTask = new WebAsyncTask<>(10 * 1000L, () -> {
            log.info(String.format("异步工作线程：%s", Thread.currentThread().getName()));
            // 任务处理时间5s，不超时
            Thread.sleep(5 * 1000L);
            return asyncService.generateUUID();
        });

        // 任务执行完成时调用该方法
        // WebAsyncTask.onCompletion(Runnable) ：在当前任务执行结束以后，无论是执行成功还是异常中止，onCompletion的回调最终都会被调用
        asyncTask.onCompletion(() -> log.info("任务执行完成"));
        log.info("继续处理其他事情");
        return asyncTask;
    }

    /**
     * 抛出异常异步任务
     * http://localhost:8080/exception
     * WebAsyncTask.onError(Callable<?>) ：当异步任务抛出异常的时候，onError()方法即会被调用
     *
     * @return
     */
    @GetMapping("/exception")
    public WebAsyncTask<String> asyncTaskException() {
        // 打印处理线程名
        log.info(String.format("请求处理线程：%s", Thread.currentThread().getName()));

        // 模拟开启一个异步任务，超时时间为10s
        WebAsyncTask<String> asyncTask = new WebAsyncTask<>(10 * 1000L, () -> {
            log.info(String.format("异步工作线程：%s", Thread.currentThread().getName()));
            // 任务处理时间5s，不超时
            TimeUnit.SECONDS.sleep(5L);

            throw new Exception(ERROR_MESSAGE);
        });

        // 任务执行完成时调用该方法
        asyncTask.onCompletion(() -> log.info("任务执行完成"));
        asyncTask.onError(() -> {
            log.info("任务执行异常");
            return ERROR_MESSAGE;
        });

        log.info("继续处理其他事情");
        return asyncTask;
    }

    /**
     * http://localhost:8080/timeout
     * timeout 异常
     * WebAsyncTask.onTimeout(Callable<?>) ：当异步任务发生超时的时候，onTimeout()方法即会被调用
     *
     * @return
     */
    @GetMapping("/timeout")
    public WebAsyncTask<String> asyncTaskTimeout() {
        // 打印处理线程名
        log.info(String.format("请求处理线程：%s", Thread.currentThread().getName()));

        // 模拟开启一个异步任务，超时时间为10s
        WebAsyncTask<String> asyncTask = new WebAsyncTask<>(10 * 1000L, () -> {
            log.info(String.format("异步工作线程：%s", Thread.currentThread().getName()));
            // 任务处理时间15s，超时
            Thread.sleep(15 * 1000L);
            TimeUnit.SECONDS.sleep(15);
            return TIME_MESSAGE;
        });

        // 任务执行完成时调用该方法
        asyncTask.onCompletion(() -> log.info("任务执行完成"));
        asyncTask.onTimeout(() -> {
            log.info("任务执行超时");
            return TIME_MESSAGE;
        });

        log.info("继续处理其他事情");
        return asyncTask;
    }

    /**
     * http://localhost:8080/threadPool
     * thread pool
     * @return
     */
    @GetMapping("/threadPool")
    public WebAsyncTask<String> asyncTaskThreadPool() {
        return new WebAsyncTask<>(10 * 1000L, executor,
                () -> {
                    log.info(String.format("异步工作线程：%s", Thread.currentThread().getName()));
                    return asyncService.generateUUID();
                });
    }

}
