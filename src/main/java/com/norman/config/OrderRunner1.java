package com.norman.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner 接口的 Component 会在所有 Spring Beans 都初始化之后，
 * SpringApplication.run() 之前执行，
 * 非常适合在应用程序启动之初进行一些数据初始化的工作。
 * 并且@Order()里面的值越小启动越早。
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/11/22 4:17 PM.
 */
@Component
@Order(2)
public class OrderRunner1 implements CommandLineRunner {
    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("The OrderRunner1 start to initialize ...");
    }
}
