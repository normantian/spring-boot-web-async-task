package com.norman.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * redis zset delay queue
 * Created by tianfei on 2018/9/17.
 */
@Slf4j
//@Component
//@ConditionalOnWebApplication
public class DelayQueueConsumer implements InitializingBean {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor executor;

    final String zKey = "zset_test";


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void afterPropertiesSet() throws Exception {
        // 模拟数据
        mockup();
    }

    private void mockup(){
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

        Calendar cal6 = Calendar.getInstance();
        cal5.add(Calendar.MINUTE, 2);
        int minute2later = (int) (cal6.getTimeInMillis() / 1000);

        final ZSetOperations<String, String> zsetOps = stringRedisTemplate.opsForZSet();
        zsetOps.add(zKey,"e",second50later );
        zsetOps.add(zKey,"a",second10later );
        zsetOps.add(zKey,"a1",second10later );
        zsetOps.add(zKey,"c",second30later );
        zsetOps.add(zKey,"c2",second30later );
        zsetOps.add(zKey,"b",second20later );
        zsetOps.add(zKey,"d",second40later );
        zsetOps.add(zKey,"norman",minute2later );

        log.info(sdf.format(new Date()) + " add finished.");
    }

    @PostConstruct
    public void init(){
        final ZSetOperations<String, String> zsetOps = stringRedisTemplate.opsForZSet();

        executor.execute(() -> {
            while (!Thread.interrupted()){
                final Set<String> set = zsetOps.rangeByScore(zKey, 0, System.currentTimeMillis() / 1000);

                if(set.isEmpty()){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                for (String msg : set){
                    if(zsetOps.remove(zKey,msg) > 0){
                        handleMsg(msg);
                    }
                }

                if(zsetOps.zCard(zKey).longValue() <= 0L){
                    log.info(zKey+" all finished");
//                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void handleMsg(String msg){
        log.info(sdf.format(new Date()) + " handle " + msg);
    }
}
