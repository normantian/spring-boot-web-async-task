//package com.norman.hbase;
//
//import org.apache.hadoop.hbase.HColumnDescriptor;
//import org.apache.hadoop.hbase.HTableDescriptor;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Admin;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.apache.hadoop.hbase.client.Delete;
//import org.apache.hadoop.hbase.client.Get;
//import org.apache.hadoop.hbase.client.HTable;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.client.Table;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.stream.Stream;
//
///**
// * Created by tianfei on 2018/7/20.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class HBaseTest2 {
//
//    @Autowired
//    @Qualifier("hbaseConfig")
//    private org.apache.hadoop.conf.Configuration hbaseConfig;
//
//    private static final String TABLE_NAME = "emp";
//
//    @Test
//    public void setup() throws IOException {
//
//        Connection hbaseClient = ConnectionFactory.createConnection(hbaseConfig);
//
//        final Admin admin = hbaseClient.getAdmin();
//
//
//        final HTableDescriptor tableDescriptor1 = admin.getTableDescriptor(TableName.valueOf(TABLE_NAME));
//
//        final boolean present = tableDescriptor1!=null;
//
////        final boolean present = Arrays.stream(admin.listTableNames())
////                .filter(t -> t.getNameAsString().equals(TABLE_NAME))
////                .findAny().isPresent();
//
//        if(!present){
//            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
//            // ... with two column families
//            tableDescriptor.addFamily(new HColumnDescriptor("personal"));
//            tableDescriptor.addFamily(new HColumnDescriptor("professional"));
//            admin.createTable(tableDescriptor);
//            System.out.println("create table " + TABLE_NAME);
//
//        }
//
//        if (!hbaseClient.isClosed()) {
//            hbaseClient.close();
//        }
//
//
//    }
//
//    //create 'emp' ,'personal','professional'
//    @Test
//    public void insertData() throws IOException {
//        // Instantiating Configuration class
//
//        // Instantiating HTable class
//
//        Table hTable = new HTable(hbaseConfig, "emp");
//
//        // Instantiating Put class
//        // accepts a row name.
//        Put p = new Put(Bytes.toBytes("row1"));
//
//        // adding values using add() method
//        // accepts column family name, qualifier/row name ,value
//        p.addColumn(Bytes.toBytes("personal"),
//                Bytes.toBytes("name"), Bytes.toBytes("raju"));
//
//        p.addColumn(Bytes.toBytes("personal"),
//                Bytes.toBytes("city"), Bytes.toBytes("hyderabad"));
//
//        p.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("designation"),
//                Bytes.toBytes("manager"));
//
//        p.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("salary"),
//                Bytes.toBytes("50000"));
//
//        // Saving the put Instance to the HTable.
//        hTable.put(p);
//        System.out.println("data inserted");
//
//        // closing HTable
//        hTable.close();
//    }
//
//    @Test
//    public void updateData() throws IOException {
//        // Instantiating Configuration class
////        Configuration config = HBaseConfiguration.create();
//
//        // Instantiating HTable class
//        HTable hTable = new HTable(hbaseConfig, "emp");
//
//        // Instantiating Put class
//        //accepts a row name
//        Put p = new Put(Bytes.toBytes("row1"));
//
//        // Updating a cell value
//        p.addColumn(Bytes.toBytes("personal"),
//                Bytes.toBytes("city"), Bytes.toBytes("Delih"));
//
//        // Saving the put Instance to the HTable.
//        hTable.put(p);
//        System.out.println("data Updated");
//
//        // closing HTable
//        hTable.close();
//    }
//
//    @org.junit.Test
//    public void retrieveData() throws IOException {
//        // Instantiating HTable class
//        HTable table = new HTable(hbaseConfig, TABLE_NAME);
//
//        // Instantiating Get class
//        Get g = new Get(Bytes.toBytes("row1"));
//
//        // Reading the data
//        Result result = table.get(g);
//
//        // Reading values from Result class object
//        byte[] value = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("name"));
//
//        byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("city"));
//
//        byte[] value3 = result.getValue(Bytes.toBytes("professional"), Bytes.toBytes("designation"));
//
//        byte[] value2 = result.getValue(Bytes.toBytes("professional"), Bytes.toBytes("salary"));
//
//        // Printing the values
//        String name = Bytes.toString(value);
//        String city = Bytes.toString(value1);
//        String salary = Bytes.toString(value2);
//        String designation = Bytes.toString(value3);
//
//        System.out.println("name: " + name + " city: " + city + "designation: " + designation + " salary: " + salary);
//    }
//
//    @Test
//    public void deleteData() throws IOException {
//        // Instantiating HTable class
//        HTable table = new HTable(hbaseConfig, "emp");
//
//        // Instantiating Delete class
//        Delete delete = new Delete(Bytes.toBytes("row1"));
//        delete.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("name"));
//        delete.addFamily(Bytes.toBytes("professional"));
//
//        // deleting the data
//        table.delete(delete);
//
//        // closing the HTable object
//        table.close();
//        System.out.println("data deleted.....");
//    }
//}
