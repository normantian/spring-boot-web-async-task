package com.norman.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianfei on 2018/8/9.
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {
    @Autowired
    StringRedisTemplate stringRedisTemplate;



//    @Bean
//    RedisPublisherImpl redisPublisher() {
//        new RedisPubSubAsyncCommandsImpl()
//        return new RedisPublisherImpl(redisTemplate, topic());
//    }

    @Bean
    List<ChannelTopic> topics() {
        final ArrayList<ChannelTopic> channelTopics = new ArrayList<>();

        channelTopics.add(new ChannelTopic("pubsub:queue"));
        channelTopics.add(new ChannelTopic("pubsub:queue2"));
        return channelTopics;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageListener());
    }

    @Bean
    RedisMessageListenerContainer redisContainer(@Autowired RedisConnectionFactory redisConnectionFactory) {
        final RedisMessageListenerContainer container =
                new RedisMessageListenerContainer();

//        container.setConnectionFactory(stringRedisTemplate.getConnectionFactory());
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListener(), topics());
        return container;
    }

}
