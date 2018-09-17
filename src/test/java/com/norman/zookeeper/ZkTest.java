package com.norman.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * Created by tianfei on 2018/9/13.
 */
public class ZkTest {

    private static ZooKeeper zk = null;

    /**
     *
     * @param zkHost
     * @param timeOut
     * @throws Exception
     */
    public static void createConnection(String zkHost, int timeOut) {
        try {
            zk = new ZooKeeper(zkHost, timeOut, new Watcher() {
                // 监控所有被触发的事件
                public void process(WatchedEvent event) {
                    System.out.println("连接成功,已经触发了" + event.getType() + "事件！");
                }
            });
        } catch (IOException e) {
            System.out.println("连接失败，请检查zookeeper的地址是否正确");
            e.printStackTrace();
        }
    }

    /**
     * 创建节点和创建子节点的区别在于nodePath 创建节点 nodePath 为 "/nodeFather" 创建其子节点时 nodePath
     * 为"/nodeFather/nodeSon"
     *  1.CreateMode 取值
     *  PERSISTENT：持久化，这个目录节点存储的数据不会丢失
     *  PERSISTENT_SEQUENTIAL：顺序自动编号的目录节点，这种目录节点会根据当前已近存在的节点数自动加 1，然后返回给客户端已经成功创建的目录节点名；
     *  EPHEMERAL：临时目录节点，一旦创建这个节点的客户端与服务器端口也就是 session过期超时，这种节点会被自动删除
     *  EPHEMERAL_SEQUENTIAL：临时自动编号节点
     * @param nodePath
     * @param nodeData
     */
    public static void creatNode(String nodePath, String nodeData) {
        try {
            zk.create(nodePath, nodeData.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("创建节点" + nodePath + "成功，节点内容为：" + nodeData);
        } catch (KeeperException e) {
            System.out.println("创建节点已经存在，创建失败。");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * nodePath 为需要读取节点的路径
     *
     * @param nodePath
     */
    public static void readNode(String nodePath) {
        try {
            System.out.println(nodePath + "节点内容为" + new String(zk.getData(nodePath, false, null)));
        } catch (KeeperException e) {
            System.out.println("读取的节点" + nodePath + "节点不存在!!");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询当前节点下的子节点个数
     * @param path
     */
    public static void getChild(String path) {

        List<String> list;
        try {
            list = zk.getChildren(path, false);
            if(list.isEmpty()){
                System.out.println(path+"中没有子节点");
            }else{
                System.out.println(path+"中存在子节点");
                for(String child:list){
                    System.out.println("子节点为："+child);
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断当前路径节点是否存在
     * @param nodePath
     * @return Stat为zookeeper的一个对象
     */
    public static Stat isExists(String nodePath) {
        Stat stat=null;
        try {
            stat=zk.exists(nodePath, true);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return stat;
    }


    /**
     * 此处的nodePath也可以是子节点也可以是父亲节点路径
     *
     * @param nodePath
     * @param modifyNodeData
     *            更新后的数据
     */
    public static void updateNode(String nodePath, String modifyNodeData) {
        try {
            zk.setData(nodePath, modifyNodeData.getBytes(), -1);
            System.out.println("更新节点" + nodePath + "的数据为" + modifyNodeData);
        } catch (KeeperException e) {
            System.out.println("更新的节点" + nodePath + "节点不存在!!");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点
     *
     * @param nodePath
     *            删除节点的路径，可以是父亲节点也可以是子节点
     *
     */
    public static void deleteNode(String nodePath) {
        try {
            zk.delete(nodePath, -1);
            System.out.println("删除" + nodePath + "节点成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            System.out.println("删除失败，删除的节点不存在或没有删除子节点直接删除父节点，没有节点被删除");
            e.printStackTrace();
        }

    }

    /**
     * 关闭节点
     */
    public static void closeConnection() {
        try {
            zk.close();
            System.out.println("关闭连接");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * main测试使用
     *
     * @param args
     */
    public static void main(String[] args) {
        String nodePath = "/abel";
        String sonNodePath = "/abel/son";
        String zkHost = "localhost:2181";
        int timeOut = 1000;
        try {
            createConnection(zkHost, timeOut);
            creatNode(nodePath, "我是第一个节点");
            readNode(nodePath);
            if(isExists(nodePath)!=null) {
                System.out.println(nodePath+"节点存在");
            }else{
                System.out.println(nodePath+"节点不存在");
            }
//            updateNode(nodePath, "我不想做第一个节点了，我想变化一下");
            creatNode(sonNodePath, "我是子节点");
//            readNode(sonNodePath);
//            getChild(nodePath);
//            updateNode(sonNodePath, "我是子节点，我想改变一下自己");
//            deleteNode(sonNodePath);
//            deleteNode(nodePath);
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
