package com.norman.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;

/**
 * Created by tianfei on 2018/8/9.
 */
public class RedisMessageListener implements MessageListener {
    /**
     * Callback for processing received objects through Redis.
     *
     * @param message message must not be {@literal null}.
     * @param pattern pattern matching the channel (if specified) - can be {@literal null}.
     */
    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        System.out.println("Message received: " + message.toString()  + " pattern:" + new String(pattern));
    }
}
