package com.norman.hbase;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by tianfei on 2018/7/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EnableTable {
    @Autowired
    @Qualifier("hbaseConfig")
    private org.apache.hadoop.conf.Configuration hbaseConfig;
    Connection hbaseClient;

    private static final String TABLE_NAME = "emp";

    @Before
    public void setUp(){
        try {
            hbaseClient = ConnectionFactory.createConnection(hbaseConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws IOException {
        if(!hbaseClient.isClosed()){
            hbaseClient.close();
            System.out.println("hbase client closed");
        }
    }


    @Test
    public void enableTable() throws IOException {

        Connection hbaseClient = ConnectionFactory.createConnection(hbaseConfig);


        // Instantiating HBaseAdmin class
        final Admin admin = hbaseClient.getAdmin();

        // Verifying weather the table is disabled
        final TableName tableName = TableName.valueOf(TABLE_NAME);

        Boolean bool = admin.isTableEnabled(tableName);
        System.out.println(TABLE_NAME + " enable is " + bool);

        // Disabling the table using HBaseAdmin object
        if(!bool){
            admin.enableTable(tableName);
            System.out.println("Table Enabled");
        }

    }

    @Test
    public void disableTable() throws Exception {
        Connection hbaseClient = ConnectionFactory.createConnection(hbaseConfig);
        // Instantiating HBaseAdmin class
        final Admin admin = hbaseClient.getAdmin();

        // Verifying weather the table is disabled
        final TableName tableName = TableName.valueOf(TABLE_NAME);
        Boolean enable = admin.isTableDisabled(tableName);
        System.out.println(TABLE_NAME + " enable is " + enable);

        // Disabling the table using HBaseAdmin object
        if(!enable){
            admin.disableTable(tableName);
            System.out.println("Table disabled");
        }
    }

}
