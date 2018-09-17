package com.norman.zookeeper.example.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.utils.CloseableUtils;

import java.util.Collection;

/**
 * Created by tianfei on 2018/9/14.
 */
public class TransactionExamples {

    public static Collection<CuratorTransactionResult> transaction(CuratorFramework client) throws Exception {
        // this example shows how to use ZooKeeper's transactions

        CuratorOp createOp = client.transactionOp().create().forPath("/a", "some data".getBytes());
        CuratorOp setDataOp = client.transactionOp().setData().forPath("/another", "other data".getBytes());
        CuratorOp deleteOp = client.transactionOp().delete().forPath("/another");

        Collection<CuratorTransactionResult> results = client.transaction().forOperations(createOp, setDataOp, deleteOp);

        for (CuratorTransactionResult result : results) {
            System.out.println(result.getForPath() + " - " + result.getType());
        }

        return results;
    }

    public static void main(String[] args) {
        final CuratorFramework client = CreateClientExamples.createSimple("127.0.0.1:2181");
//        CuratorFramework client = CuratorFrameworkFactory.newClient(URL, new ExponentialBackoffRetry(1000, 3));
        try {
            client.start();
            TransactionExamples.transaction(client);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }

    }
}
