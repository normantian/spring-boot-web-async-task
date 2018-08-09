package com.norman.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by tianfei on 2018/8/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubSubTest {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void pubSubTest() {
        this.pub();
        this.pub2();
    }

    public void pub() {
        String channel = "pubsub:queue";
        stringRedisTemplate.convertAndSend(channel, "from testData");
    }

    public void pub2() {
        String channel = "pubsub:queue2";
        stringRedisTemplate.convertAndSend(channel, "test2 data");
    }
}
