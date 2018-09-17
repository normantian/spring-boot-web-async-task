package com.norman.service;

import com.norman.config.ZkAutoCloseConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianfei on 2018/9/13.
 */
@Service
public class ZkService {

    @Autowired
    CuratorFramework zkClient;

//    ZkAutoCloseConfig zkclient;

    /**
     * 如果没有设置节点属性，节点创建模式默认为持久化节点，内容默认为空
     *
     * @param nodePath
     * @param nodeData
     * @throws Exception
     */
    public void createNode(String nodePath, String nodeData) throws Exception {
//        String path=lockpath+"/";
        nodePath = checkPath(nodePath);

        try (ZkAutoCloseConfig zkAutoCloseConfig = new ZkAutoCloseConfig(zkClient)) {

            if (CuratorFrameworkState.STARTED != zkClient.getState()) {
                zkAutoCloseConfig.start();
            }
            final String namespace = zkClient.getNamespace();
            System.out.println("namespace = " + namespace);

            if (null == zkClient.checkExists().forPath(nodePath)) {
                zkClient.create().forPath(nodePath, nodeData.getBytes());
            }
        }

    }

    public void createEphemeralNode(String nodePath, String nodeData) throws Exception {
        nodePath = checkPath(nodePath);

        if (null == zkClient.checkExists().forPath(nodePath)) {
            zkClient.create().withMode(CreateMode.EPHEMERAL).forPath(nodePath, nodeData.getBytes());
        }
    }

    public void createNode(CreateMode mode, String nodePath, String nodeData) throws Exception {
        nodePath = checkPath(nodePath);

        if (zkClient.checkExists().forPath(nodePath) == null) {
            final String path = zkClient.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(mode).forPath(nodePath, nodeData.getBytes());
            System.out.println("node path: " + path);
        }
    }

    public List<String> listChildren(String nodePath) throws Exception {
        final List<String> children = zkClient.getChildren().forPath(nodePath);

        return children;
    }

    public String getNode(String nodePath) throws Exception {

        nodePath = checkPath(nodePath);
        final byte[] bytes = zkClient.getData().forPath(nodePath);
        return new String(bytes, Charset.forName("utf-8"));

    }

    public void updateNode(String nodePath, String modifyNodeData) throws Exception {
        nodePath = checkPath(nodePath);

        if (null != zkClient.checkExists().forPath(nodePath)) {
            zkClient.setData().forPath(nodePath, modifyNodeData.getBytes());
        }
    }



    public void interProcessMutex(String lockName, String taskName, long timeout, TimeUnit timeUnit) throws Exception {

        Random random = new Random();

        InterProcessMutex mutex = new InterProcessMutex(zkClient, "/" + lockName);

        if(mutex.acquire(timeout, timeUnit)){
            try {
                Thread.sleep(random.nextInt(3)*1000);
                System.out.println("do job " + taskName + " done");
            } finally {
                mutex.release();
            }
        } else {
            //超时失败
            System.out.println(lockName + " get lock failed timeout");
        }
    }

    public void interProcessMutex(String lockName, String taskName) throws Exception {

        Random random = new Random();

        InterProcessMutex mutex = new InterProcessMutex(zkClient, "/" + lockName);

        mutex.acquire();

        try{
            Thread.sleep(random.nextInt(6)*1000);
            System.out.println("do job "+lockName+ " done");
        }finally {
            mutex.release();
        }
    }

    private String checkPath(String nodePath) {
        if (nodePath.charAt(0) != 47) {
            nodePath = "/" + nodePath;
        }
        return nodePath;
    }
}
