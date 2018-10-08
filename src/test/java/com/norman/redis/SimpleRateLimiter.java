package com.norman.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/8 下午5:09.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleRateLimiter {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() throws Exception{
        // 一分钟内只允许最多回复 5 个帖子
        for(int i=0;i<20;i++) {
            System.out.println(isActionAllowed("laoqian", "reply", 10, 5));
            TimeUnit.SECONDS.sleep(1);
        }

        TimeUnit.SECONDS.sleep(20);
        System.out.println(isActionAllowed("laoqian", "reply", 20, 5));
    }

    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount){
        String key = String.format("hist:%s:%s", userId, actionKey);

        final List<Object> objects = stringRedisTemplate.executePipelined(
                new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        connection.openPipeline();
                        boolean isPipelined = connection.isPipelined();

                        try {
                            if (isPipelined) {
//                                final ZSetOperations<String, String> zsetOps = stringRedisTemplate.opsForZSet();

                                long nowTs = System.currentTimeMillis();
                                // value 和 score 都使用毫秒时间戳
//                                zsetOps.add(key, "" + nowTs, nowTs);
                                connection.zAdd(key.getBytes(), nowTs, (nowTs+"").getBytes());

                                //移除时间窗口之前的行为记录，剩下的都是时间窗口内的
                                connection.zRemRangeByScore(key.getBytes(), 0 , nowTs - period * 1000);
//                                zsetOps.removeRangeByScore(key, 0, nowTs - period * 1000);
                                // 移除时间窗口之前的行为记录，剩下的都是时间窗口内的
//                                count = zsetOps.zCard(key);
                                connection.zCard(key.getBytes());

                                //设置 zset 过期时间，避免冷用户持续占用内存
                                //过期时间应该等于时间窗口的长度，再多宽限 1s
                                connection.expire(key.getBytes(), period + 1);


                            }

                        } catch (Exception e) {

                        } finally {
//                            connection.exec();
//                            connection.closePipeline();
                        }
                        return null;
                    }
                });
        System.out.println(objects);
        return (Long)(objects.get(2)) <= maxCount;
    }

//    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) {
//        String key = String.format("hist:%s:%s", userId, actionKey);
//        long nowTs = System.currentTimeMillis();
//        Pipeline pipe = jedis.pipelined();
//        pipe.multi();
//        pipe.zadd(key, nowTs, "" + nowTs);
//        pipe.zremrangeByScore(key, 0, nowTs - period * 1000);
//        Response<Long> count = pipe.zcard(key);
//        pipe.expire(key, period + 1);
//        pipe.exec();
//        pipe.close();
//        return count.get() <= maxCount;
//    }
}
