package com.norman.zookeeper;

import com.norman.service.ZkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianfei on 2018/9/13.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ZooKeeperTest {

    @Autowired
    CuratorFramework zkClient;


    @Autowired
    ZkService zkService;

    String nodePath = "test";

    @Test
    public void createNode() throws Exception {
        zkService.createNode(nodePath, "I'm a test node");
        zkService.createNode("/test2/2", "I'm a test node2");
    }

    @Test
    public void createPersistentSequentialNode() throws Exception {
//        zkService.createEphemeralNode("/tempNode","temp");
        zkService.createNode(CreateMode.PERSISTENT_SEQUENTIAL, "sequence", "001");
    }

    @Test
    public void createTempNode() throws Exception {
//        zkService.createEphemeralNode("/tempNode","temp");
        zkService.createNode(CreateMode.EPHEMERAL, "temp/tempNode3", "temp1");

        System.out.println("create temp node");

        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void listChildren() throws Exception {
        final List<String> list = zkService.listChildren("/");
        list.forEach(System.out::println);
    }

    @Test
    public void getNode() throws Exception {
        final String node = zkService.getNode(nodePath);

        System.out.println("get test node from zk : " + node);
    }

    @Test
    public void updateNode() throws Exception {
        zkService.updateNode(nodePath, "node test 2");

        final String node = zkService.getNode(nodePath);
        System.out.println("get test node after change : " + node);

    }

    @Test
    public void lock() throws Exception {


        final ExecutorService executorService =
                Executors.newFixedThreadPool(10);


        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    zkService.interProcessMutex("lock_task", "task"+index);
                    countDownLatch.countDown();
                    System.out.println("finish " + index);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });
        }

        countDownLatch.await();
        executorService.shutdown();
        log.info("all finished.");
//        try {
//            while (!executorService.awaitTermination(1, TimeUnit.SECONDS)){
////                log.info("still running");
//            }
//            log.info("all finished.");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }

    @Test
    public void lockWithTimeout() throws Exception {


        final ExecutorService executorService =
                Executors.newFixedThreadPool(10);


        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    zkService.interProcessMutex("lock_task", "task"+index, 5, TimeUnit.SECONDS);
                    countDownLatch.countDown();
//                    System.out.println("finish " + index);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });
        }

        countDownLatch.await();
        executorService.shutdown();
        log.info("all finished.");
//        try {
//            while (!executorService.awaitTermination(1, TimeUnit.SECONDS)){
////                log.info("still running");
//            }
//            log.info("all finished.");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }

}
