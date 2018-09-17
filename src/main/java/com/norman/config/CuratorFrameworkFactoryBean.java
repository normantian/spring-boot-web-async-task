package com.norman.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;

/**
 * Created by tianfei on 2018/9/13.
 */
//@Configuration
public class CuratorFrameworkFactoryBean extends AbstractFactoryBean<CuratorFramework> {

//    @Value("${zk.url}")
    private String zkUrl;

    private String nameSpace = "tianfei";

    private int baseSleepTime = 3000;

    private int maxTries = 4;

    /**
     * This abstract method declaration mirrors the method in the FactoryBean
     * interface, for a consistent offering of abstract template methods.
     *
     * @see FactoryBean#getObjectType()
     */
    @Nullable
    @Override
    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }

    /**
     * Template method that subclasses must override to construct
     * the object returned by this factory.
     * <p>Invoked on initialization of this FactoryBean in case of
     * a singleton; else, on each {@link #getObject()} call.
     *
     * @return the object returned by this factory
     * @throws Exception if an exception occurred during object creation
     * @see #getObject()
     */
    @Override
    protected CuratorFramework createInstance() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTime, maxTries);


        //CuratorFramework client= CuratorFrameworkFactory.newClient(zkUrl,retryPolicy);

        CuratorFramework curatorFramework =
                CuratorFrameworkFactory.builder().connectString(zkUrl)
                        .retryPolicy(retryPolicy).defaultData("unknown".getBytes(Charset.forName("utf-8"))).namespace(nameSpace).build();

        return curatorFramework;

    }
}

