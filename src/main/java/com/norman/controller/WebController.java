package com.norman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by tianfei on 2018/7/9.
 * @author tianfei
 */
@RestController
//@RequestMapping(value = "/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/")
public class WebController {

    @Value("${server.port}")
    private int port;

    @Autowired
    private Environment environment;

    @GetMapping(value = "/ip")
    public String getIp() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();

        //获得本机IP
        String ip=addr.getHostAddress();

        //获得本机名称
        String address=addr.getHostName();

        return ip + ":" + port + " " + address;
    }

    @GetMapping("/env")
    public String getEnv(){
        return environment.getActiveProfiles()[0];
    }

}
