package com.norman.redis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Created by tianfei on 2018/9/12.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZSetTest {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    final String zKey = "zset_test";


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Before
    public void init(){
        stringRedisTemplate.delete(zKey);
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.SECOND, 10);
        int second10later = (int) (cal1.getTimeInMillis() / 1000);

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.SECOND, 20);
        int second20later = (int) (cal2.getTimeInMillis() / 1000);

        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.SECOND, 30);
        int second30later = (int) (cal3.getTimeInMillis() / 1000);

        Calendar cal4 = Calendar.getInstance();
        cal4.add(Calendar.SECOND, 40);
        int second40later = (int) (cal4.getTimeInMillis() / 1000);

        Calendar cal5 = Calendar.getInstance();
        cal5.add(Calendar.SECOND, 50);
        int second50later = (int) (cal5.getTimeInMillis() / 1000);

        final ZSetOperations<String, String> zsetOps = stringRedisTemplate.opsForZSet();
        zsetOps.add(zKey,"e",second50later );
        zsetOps.add(zKey,"a",second10later );
        zsetOps.add(zKey,"a1",second10later );
        zsetOps.add(zKey,"c",second30later );
        zsetOps.add(zKey,"c2",second30later );
        zsetOps.add(zKey,"b",second20later );
        zsetOps.add(zKey,"d",second40later );

        System.out.println(sdf.format(new Date()) + " add finished.");
    }
    @Test
    public void test() throws InterruptedException {
        final ZSetOperations<String, String> zsetOps = stringRedisTemplate.opsForZSet();
//        zsetOps.remove(zKey, "d");

        while (true){
            final Set<String> set = zsetOps.rangeByScore(zKey, 0, System.currentTimeMillis() / 1000);

            if(set.isEmpty()){
                Thread.sleep(500);
                continue;
            }

            for (String msg : set){
                if(zsetOps.remove(zKey,msg) > 0){
                    handleMsg(msg);
                }
            }


            if(zsetOps.zCard(zKey).longValue() <= 0L){
                System.out.println("finished");
                return;
            }
            Thread.sleep(1000);

        }

    }
    public void handleMsg(String msg){
        System.out.println(sdf.format(new Date()) + " handle " + msg);
    }
}
