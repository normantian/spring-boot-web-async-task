package com.norman.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by tianfei on 2018/7/11.
 *
 * AOP 顺序 around -> before -> method execute > around -> after -> after returning
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class LogAop {

    private static final String TRACE_ID = "TRACE_ID";

    // @RestController && @GetMapping
//    @Pointcut("@target(org.springframework.web.bind.annotation.RestController) " +
//            "&& @annotation(org.springframework.web.bind.annotation.GetMapping)")
//    public void pointCutAt() {
//    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void pointCutAt() {
    }

    @Before("pointCutAt()")
    public void before(){
        MDC.put(TRACE_ID, UUID.randomUUID().toString());
        log.info("------before------");
    }

    @Around("pointCutAt()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        log.info("-----around before-------");


        Object result = pjp.proceed();
        log.info("-----around after-------");

        return result;
    }

    @After("pointCutAt()")
    public void after(){
        log.info("------after------");
    }

    @AfterReturning(pointcut = "pointCutAt()", returning = "rvt")
    public void afterReturning(Object rvt){

        log.info("-----after returning------- return is "+ rvt);
        MDC.remove(TRACE_ID);

    }

}
