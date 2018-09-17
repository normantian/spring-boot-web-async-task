package com.norman.zookeeper.lock;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * Created by tianfei on 2018/9/13.
 */
public class ZkClient {

    public static final int sessionTimeOut = 1000;

    public static ZooKeeper getInstance() {
        final CountDownLatch c = new CountDownLatch(1);
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(ZooKeeperConstant.CONNECTION_URL, sessionTimeOut, (e) -> {
                if (e.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    c.countDown();
                }
            });
            c.await();
            return zooKeeper;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
