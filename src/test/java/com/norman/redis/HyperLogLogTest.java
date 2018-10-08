package com.norman.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/8 上午10:04.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HyperLogLogTest {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testCount(){
        final HyperLogLogOperations<String, String> opsForHyperLogLog = stringRedisTemplate.opsForHyperLogLog();

        opsForHyperLogLog.delete("codehole");

        //100000 差了 277 个，按百分比是 0.277%
        for (int i = 0; i < 100000; i++) {
            opsForHyperLogLog.add("codehole","user"+i);
//            long total = opsForHyperLogLog.size("codehole");
//            if(total != i+1){
//                System.out.printf("%d %d\n", total, i + 1);
//                break;
//            }
        }

        long total = opsForHyperLogLog.size("codehole");
        System.out.printf("%d %d\n", 100000, total);

    }
}
