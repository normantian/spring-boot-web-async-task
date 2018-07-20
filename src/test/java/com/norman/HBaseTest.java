package com.norman;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by tianfei on 2018/7/18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class HBaseTest {

//
//    @Autowired
//    @Qualifier("hbaseConfig")
//    org.apache.hadoop.conf.Configuration configuration;


    @Autowired
    @Qualifier("hbaseConfig")
    private org.apache.hadoop.conf.Configuration hbaseConfig;

    private static final String TABLE_NAME = "contacts";

    private Connection hbaseClient;


    @Before
    public void setup(){

        try {
            hbaseClient = ConnectionFactory.createConnection(hbaseConfig);
        } catch (IOException ex) {
            System.out.println("failed to get hbase connection");
            System.exit(0);
        }
    }



    @Test
    public void createTable() throws IOException {
//        System.out.println(hbaseClient);
//        System.out.println(configuration);

        final Admin admin = hbaseClient.getAdmin();

        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
        // ... with two column families
        tableDescriptor.addFamily(new HColumnDescriptor("name"));
        tableDescriptor.addFamily(new HColumnDescriptor("contactinfo"));
        admin.createTable(tableDescriptor);

        if (!hbaseClient.isClosed()) {
            hbaseClient.close();
        }

    }

    @Test
    public void deleteTable() throws IOException {

        final Admin admin = hbaseClient.getAdmin();
        final TableName tableName = TableName.valueOf(TABLE_NAME);

        admin.disableTable(tableName);
        admin.deleteTable(tableName);

    }

    @Test
    public void deleteById() throws IOException{
        Delete delete = new Delete(Bytes.toBytes("2"));
        try (Table table = hbaseClient.getTable(TableName.valueOf(TABLE_NAME))) {
            table.delete(delete);
        }
    }

    @Test
    public void insert() throws IOException {

        try (Table table = hbaseClient.getTable(TableName.valueOf(TABLE_NAME))) {
            for (Integer i = 10; i < 14; i++) {
                Put person = new Put(Bytes.toBytes(i.toString()));


                person.addColumn(Bytes.toBytes("name"), Bytes.toBytes("first"), Bytes.toBytes("norman" + i));
                person.addColumn(Bytes.toBytes("name"), Bytes.toBytes("last"), Bytes.toBytes("tian" + i));

                person.addColumn(Bytes.toBytes("contactinfo"), Bytes.toBytes("email"), Bytes.toBytes("normantian@hotmail.com"));


                table.put(person);


            }

        }
    }

    @Test
    public void search() {
        byte[] contactFamily = Bytes.toBytes("contactinfo");
        byte[] emailQualifier = Bytes.toBytes("email");

        byte[] nameFamily = Bytes.toBytes("name");
        byte[] firstNameQualifier = Bytes.toBytes("first");
        byte[] lastNameQualifier = Bytes.toBytes("last");

        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                nameFamily,
                firstNameQualifier,
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("norman10"));

        Scan scan = new Scan();
        scan.setFilter(filter);

        try (Table table = hbaseClient.getTable(TableName.valueOf(TABLE_NAME));
             ResultScanner results = table.getScanner(scan)) {

            for (Result result : results) {
                String id = new String(result.getRow());
                String firstName = new String(result.getValue(nameFamily, firstNameQualifier));
                String lastName = new String(result.getValue(nameFamily, lastNameQualifier));
                String email = new String(result.getValue(contactFamily, emailQualifier));
                System.out.println("RowKey: " + id + " - " + firstName + " " + lastName + " - " + email);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("finish");

    }

}
