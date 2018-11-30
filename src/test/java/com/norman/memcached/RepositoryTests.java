package com.norman.memcached;

import com.norman.config.MemcachedRunner;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/11/22 4:39 PM.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTests {

    @Resource
    private MemcachedRunner memcachedRunner;

    @Test
    public void testSetGet()  {
        MemcachedClient memcachedClient = memcachedRunner.getClient();
        memcachedClient.set("testkey",1000,"666666");
        System.out.println("***********  "+memcachedClient.get("testkey").toString());
    }

}
