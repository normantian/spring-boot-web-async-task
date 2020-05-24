package com.norman.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.nio.charset.Charset;

/**
 * Created by tianfei on 2018/9/12.
 */
//@Configuration
public class ZooKeeperConfig {

    @Value("${zk.url}")
    private String zkUrl;

    private String nameSpace = "tianfei";

    private int baseSleepTime = 3000;

    private int maxTries = 4;

    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework getCuratorFramework(){

//        try {
//           return new CuratorFrameworkFactoryBean().createInstance();
//        } catch (Exception ex){
//            throw new RuntimeException("Zookeeper init failed");
//        }
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTime, maxTries);


        //CuratorFramework client= CuratorFrameworkFactory.newClient(zkUrl,retryPolicy);

        CuratorFramework curatorFramework =
                CuratorFrameworkFactory.builder().connectString(zkUrl)
                        .sessionTimeoutMs(5000)
                        .retryPolicy(retryPolicy).defaultData("unknown".getBytes(Charset.forName("utf-8"))).namespace(nameSpace).build();



//        curatorFramework.start();

        return curatorFramework;
    }

//    @Bean
//    public ZkAutoCloseConfig zkAutoCloseConfig(@Autowired CuratorFramework curatorFramework){
//        return new ZkAutoCloseConfig(curatorFramework);
//    }
}
