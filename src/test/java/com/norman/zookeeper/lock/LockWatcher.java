package com.norman.zookeeper.lock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * Created by tianfei on 2018/9/13.
 */
public class LockWatcher implements Watcher {

    CountDownLatch countDownLatch;

    String lockId;

    LockWatcher(CountDownLatch countDownLatch, String lockId) {
        this.countDownLatch = countDownLatch;
        this.lockId = lockId;

    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getType() == Event.EventType.NodeDeleted){

            System.out.println(Thread.currentThread().getName() + " " + event.getPath() + "被删除了,"+ lockId +"可以去执行我的逻辑了。。。。");
            countDownLatch.countDown();
        }
    }
}
