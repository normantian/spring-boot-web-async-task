package com.norman.controller;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2020/6/18 10:40 AM.
 */

@RestController
@RequestMapping(value = "/demo")
public class DemoController {


    @GetMapping(value = "/assert")
    public Object doAssert(@RequestParam(name = "value", required = false) Integer value) {

        Assert.notNull(value, "value is null");
        Assert.isTrue(value > 10, "value less than 10");
        Assert.isTrue(value < 30, "value greater than 30");

        return value;
    }
}
