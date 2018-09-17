//package com.norman;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.phoenix.query.QueryServices;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Properties;
//
//
///**
// * Created by tianfei on 2018/7/18.
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class PhoenixTest {
//
//    @Test
//    public void crud() throws SQLException, ClassNotFoundException {
//        Statement stmt = null;
//        ResultSet rset = null;
//
//        log.info("start");
//        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
//
//        Properties props = new Properties();
//
//        props.setProperty(QueryServices.IS_NAMESPACE_MAPPING_ENABLED, Boolean.toString(true));
//
////        props.setProperty()
//        props.setProperty(QueryServices.IS_SYSTEM_TABLE_MAPPED_TO_NAMESPACE, Boolean.toString(true));
//
////        props.setProperty(QueryServices.SCHEMA_ATTRIB, "o2o");
//
////        Connection con = DriverManager.getConnection("jdbc:phoenix:localhost:2182", props);
//        Connection con = DriverManager.getConnection("jdbc:phoenix:10.41.64.117:2181", props);
////        stmt = con.createStatement();
//
////        stmt = con.createStatement();
////        final int i = stmt.executeUpdate("DELETE FROM user where id=1 ");
////
////        System.out.println("delete count = " + i);
//
////        stmt.executeUpdate("create table if not exists test2 (mykey integer not null primary key, mycolumn varchar)");
////        stmt.executeUpdate("upsert into user values (1,1,'Hello')");
////        stmt.executeUpdate("upsert into user values (2,2,'World!')");
////        con.commit();
//
////        PreparedStatement statement = con.prepareStatement("select u.mycolumn,a.address from MY_SCHEMA.user as u left join MY_SCHEMA.address as a on u.id = a.user_id");
////        PreparedStatement statement = con.prepareStatement("select * from my_table");
//        PreparedStatement statement = con.prepareStatement("select id from MCC_CUSTOMER limit 1");
////        PreparedStatement statement = con.prepareStatement("select u.mycolumn,a.address from user as u left join address as a on u.id = a.user_id");
//        rset = statement.executeQuery();
//        while (rset.next()) {
////            System.out.println(rset.getInt("id") + " " + rset.getInt("mykey") + " "+rset.getString("mycolumn"));
////            System.out.println(rset.getString("u.mycolumn") + " " +rset.getString("a.address"));
//            System.out.println(rset.getString("id"));
//        }
//
//        if(stmt != null){
//            stmt.close();
//        }
//
//        statement.close();
//        con.close();
//    }
//}
