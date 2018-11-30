package com.norman.config;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/11/22 4:36 PM.
 */
@Component
@Slf4j
public class MemcachedRunner implements CommandLineRunner {

    @Resource
    private  MemcacheSource memcacheSource;

    private MemcachedClient client = null;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

        try {
            System.out.println("init MemcachedClient...");
            client = new MemcachedClient(new InetSocketAddress(memcacheSource.getIp(),memcacheSource.getPort()));
        } catch (IOException e) {
            log.error("init MemcachedClient failed ",e);
        }

    }

    public MemcachedClient getClient() {
        return client;
    }
}
