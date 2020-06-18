package com.norman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@RestController
@EnableAsync  // 开启 @Async 的支持
public class SpringBootWebAsyncTaskApplication {

	private ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"));


	public static void main(String[] args) {
		System.out.println("The service to start.");
		SpringApplication.run(SpringBootWebAsyncTaskApplication.class, args);
		System.out.println("The service has started.");
	}

	@GetMapping("/")
	public String retrieveTime() {
		return threadLocal.get().format(new Date());
	}
}
