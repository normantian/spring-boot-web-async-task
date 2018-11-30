package com.norman.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/11/22 4:35 PM.
 */
@Component
@Data
@ConfigurationProperties(prefix = "memcache")
public class MemcacheSource {

    private String ip;

    private int port;
}
