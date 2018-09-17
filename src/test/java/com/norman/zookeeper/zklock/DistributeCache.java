package com.norman.zookeeper.zklock;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianfei on 2018/9/13.
 */
public class DistributeCache {
    private static volatile List<String> msgCache = new ArrayList<String>();

    static class MsgConsumer extends Thread {
        @Override
        public void run() {
            while (!CollectionUtils.isEmpty(msgCache)) {

                String lock = ZookeeperLock.getInstance().getLock();
                if (CollectionUtils.isEmpty(msgCache)) {
                    return;
                }
                String msg = msgCache.get(0);
                System.out.println(Thread.currentThread().getName() + " consume msg: " + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                msgCache.remove(msg);
                ZookeeperLock.getInstance().releaseLock(lock);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            msgCache.add("msg" + i);
        }
        MsgConsumer consumer1 = new MsgConsumer();
        MsgConsumer consumer2 = new MsgConsumer();
        consumer1.start();
        consumer2.start();
    }
}
