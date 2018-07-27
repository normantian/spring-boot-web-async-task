package com.norman.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by tianfei on 2018/7/27.
 * @author tianfei
 */
@RestController
@Slf4j
@RequestMapping("/hello")
public class HelloController {

    private MessageSource messageSource;

    @Autowired
    public HelloController(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @GetMapping
    public String hello(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/message", locale);
        return bundle.getString("hello");
    }
    @GetMapping("/2")
    public String hello2() {
        Locale locale = LocaleContextHolder.getLocale();
//        locale = null;
//        locale = Objects.requireNonNull(locale, "local is error");
        return messageSource.getMessage("hello", null, locale);
    }

}
