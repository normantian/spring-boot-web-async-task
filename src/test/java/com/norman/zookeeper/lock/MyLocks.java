package com.norman.zookeeper.lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tianfei on 2018/9/13.
 */
public class MyLocks {

    private static final String ROOT_LOCKS = "/LOCKS";//根节点
    private ZooKeeper zooKeeper;
    private int sessionTimeOut;//会话超时时间
    private String lockId;//记录锁节点Id
    private final static byte[] data = {1, 2};
    private CountDownLatch c = new CountDownLatch(1);
    private AtomicInteger retry = new AtomicInteger(0);

    public MyLocks() {
        zooKeeper = ZkClient.getInstance();
        sessionTimeOut = ZkClient.sessionTimeOut;
    }

    //获取锁
    public boolean lock() {
        try {
            //在ROOT_LOCKS下创建多个连续临时序列化节点
            lockId = zooKeeper.create(ROOT_LOCKS + "/", data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "->成功创建节点：" + lockId + "，开始竞争锁！");
            //获取ROOT_LOCKS子节点
            List<String> children = zooKeeper.getChildren(ROOT_LOCKS, true);
            TreeSet<String> set = new TreeSet<>();
            //排序
            children.forEach(e -> {
                set.add(ROOT_LOCKS + "/" + e);
            });

            String first = set.first();
            if (lockId.equals(first)) {//当前id如果在zk中是最小的序列直接返回释放锁
                System.out.println(Thread.currentThread().getName() + "->" + "first 成功获得锁，节点是：" + lockId);
                c.countDown();
                return true;
            }
            //获取当前节点之前的所有节点集合
            SortedSet<String> lessThanLockid = set.headSet(lockId);
            System.out.println(lessThanLockid.toString());
            if (!lessThanLockid.isEmpty()) {//如果是空直接返回  但是此时
                String prevLockId = lessThanLockid.last();//获取当前节点之前的序列zk节点
                zooKeeper.exists(prevLockId, new LockWatcher(c, lockId));//给之前的这个节点绑定一个事件 （当直接的节点删除后释放锁）
                //一直等下去
                c.await();

                //超时5秒，不超过重试5次
//                boolean b = c.await(sessionTimeOut, TimeUnit.MILLISECONDS);//等待5秒
//                if (!b) {//尝试连接
//                    int index = retry.incrementAndGet();
//                    if (index >= 5) {
//                        throw new RuntimeException("连接超时。。。");
//                    }
//                    lock();
//                }
                System.out.println(Thread.currentThread().getName() + "成功获取锁" + lockId);
            }
            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    //释放锁
    public boolean unlock() {
        System.out.println(Thread.currentThread().getName() + "=》开始释放锁"+lockId);
        try {
//            TimeUnit.SECONDS.sleep(new Random().nextInt(7) + 1);//故意是的连接超时
            zooKeeper.delete(lockId, -1);
            System.out.println("节点" + lockId + "成功释放锁。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        CountDownLatch c = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                MyLocks lock = new MyLocks();
                try {

//                    while (!lock.lock()) {
//                        Thread.sleep(100);
//                    }
//                    System.out.println(Thread.currentThread().getName() + " business process ...");
//                    Thread.sleep(500);
//                    c.countDown();
                    lock.lock();
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (lock != null) {
                        lock.unlock();
                        c.countDown();
                    }
                }
            }).start();
        }
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("全部结束");

    }
}
