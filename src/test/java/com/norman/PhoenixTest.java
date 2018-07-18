package com.norman;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by tianfei on 2018/7/18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PhoenixTest {

    @Test
    public void crud() throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet rset = null;

        log.info("start");
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");

        Connection con = DriverManager.getConnection("jdbc:phoenix:localhost:2182");
//        stmt = con.createStatement();

        stmt = con.createStatement();
        stmt.executeUpdate("DELETE FROM test2 where mykey=2 ");
//        stmt.executeUpdate("create table if not exists test2 (mykey integer not null primary key, mycolumn varchar)");
//        stmt.executeUpdate("upsert into test2 values (1,'Hello')");
//        stmt.executeUpdate("upsert into test2 values (2,'World!')");
        con.commit();

        PreparedStatement statement = con.prepareStatement("select * from test2");
        rset = statement.executeQuery();
        while (rset.next()) {
            System.out.println(rset.getInt("mykey") + " " + rset.getString("mycolumn"));
        }

        stmt.close();

        statement.close();
        con.close();
    }
}
