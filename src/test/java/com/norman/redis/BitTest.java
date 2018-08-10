package com.norman.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.BitSet;

/**
 * Created by tianfei on 2018/8/10.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class BitTest {

    public static final String SETBIT = "setbit";
    //    @Autowired
//    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    public void bitTest() {
        final ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
//        stringRedisTemplate.delete("setbit");
//        opsForValue.setBit("setbit", 0, true);
//        opsForValue.setBit("setbit",1, true);

        final Boolean setbit = opsForValue.getBit(SETBIT, 0);
        System.out.println(setbit);

        final Object object = opsForValue.get(SETBIT);
        final byte[] bytes = this.toByteArray(object);
        BitSet bitSet = BitSet.valueOf(bytes);
        System.out.println(bitSet.cardinality());
        System.out.println(bitSet);


    }

    @Test
    public void bitTest2() {

        redisTemplate.opsForValue().setBit(SETBIT, 0L, true);
        redisTemplate.opsForValue().setBit(SETBIT, 1L, true);
        redisTemplate.opsForValue().setBit(SETBIT, 2L, true);
        redisTemplate.opsForValue().setBit(SETBIT, 3L, true);
        redisTemplate.opsForValue().setBit(SETBIT, 4L, true);
        redisTemplate.opsForValue().setBit(SETBIT, 2000L, true);
        redisTemplate.opsForValue().setBit(SETBIT, Integer.MAX_VALUE, true);
//        redisTemplate.opsForValue().setBit("setbit",0L,true);
        final BitSet setbit =
//                redisTemplate.execute(new RedisCallback<BitSet>() {
//            @Override
//            public BitSet doInRedis(RedisConnection connection) throws DataAccessException {
//                byte[] key = redisTemplate.getStringSerializer().serialize("setbit");
//                byte[] value = connection.get(key);
////                final Boolean bit = connection.getBit(key, 0L);
////                System.out.println(bit);
//
//                BitSet bitSet = BitSet.valueOf(value);
//                return bitSet;
//
//            }
//        });

        redisTemplate.execute((RedisConnection connection) -> {
            byte[] key = redisTemplate.getStringSerializer().serialize(SETBIT);
            byte[] value = connection.get(key);

            BitSet bitSet = BitSet.valueOf(value);
            return bitSet;
        } );

        System.out.println(setbit);
//        final boolean b = setbit.get(7);
//        System.out.println(b);

        System.out.println(setbit.cardinality());
    }

    @Test
    public void bitTest3() {
        RedisClient client = RedisClient.create(RedisURI.create("redis://127.0.0.1:6379"));
        final StatefulRedisConnection<String, String> connect = client.connect();

        final RedisCommands<String, String> sync = connect.sync();

        //sync.del("setbit");

        final Long test3 = sync.setbit("test3", 0, 1);
        System.out.println(test3);

        final Long test31 = sync.getbit("test3", 0);
        System.out.println(test31);

        final String test32 = sync.get("test3");
        System.out.println(test32);


//        BitSet bitSet = BitSet.valueOf(setbit.getBytes());
//        System.out.println(bitSet.cardinality());
//        System.out.println(bitSet);

        connect.close();
        client.shutdown();

    }

    public byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

}
