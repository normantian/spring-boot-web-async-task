package com.norman.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by tianfei on 2018/7/18.
 * @author tianfei
 */
@Configuration
@Slf4j
public class HBaseConfig {


    @Bean("hbaseConfig")
    public org.apache.hadoop.conf.Configuration hbaseConfig(){
        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();

//        config.set("hbase.zookeeper.quorum",
//                "localhost");
        config.set("hbase.zookeeper.property.clientPort", "2182");
        config.set("hbase.cluster.distributed", "true");

        return config;
    }

    @Bean
    public Connection hbaseConnection() {

        Connection conn = null;
        try {
            conn = ConnectionFactory.createConnection(hbaseConfig());
        } catch (IOException ex) {
            log.error("failed to get hbase connection");
        }
        return conn;
    }


}
