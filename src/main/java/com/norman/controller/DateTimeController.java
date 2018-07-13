package com.norman.controller;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

/**
 * Created by tianfei on 2018/7/11.
 */
@RestController
@Slf4j
@RequestMapping("/datetime")
public class DateTimeController {

    private ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"));

    @GetMapping("/dt")
    public long getDateTime(@RequestParam(value = "dateTime") @DateTimeFormat(iso = DATE) DateTime dateTime){
        return dateTime.toDate().getTime();
    }


    @GetMapping
    public String retrieveTime() {
        return threadLocal.get().format(new Date());
    }


}
