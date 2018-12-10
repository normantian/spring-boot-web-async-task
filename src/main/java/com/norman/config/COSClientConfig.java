package com.norman.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/12/4 10:48 AM.
 */
@Configuration
public class COSClientConfig {

    @Bean("cosFileConfig")
    @ConfigurationProperties(prefix = "cos")
    public COSFileConfig cosFileConfig() {
        return new COSFileConfig();
    }

    @Bean(destroyMethod = "shutdown")
    public COSClient cosClient(@Autowired COSFileConfig cosFileConfig) {
        System.out.println("===============config cos client: " + cosFileConfig.toString());

        return this.newClient(cosFileConfig);
//        return this.oldClient(cosFileConfig);
    }

//    public COSClient oldClient(COSFileConfig cosFileConfig){
//        ClientConfig clientConfig = new ClientConfig();
//        clientConfig.setRegion(cosFileConfig.getRegion());
//        Credentials cred = new Credentials(cosFileConfig.getAppId(), cosFileConfig.getSecretId(), cosFileConfig.getSecretKey());
//        // 3 生成cos客户端
//        COSClient cosclient = new COSClient(clientConfig, cred);
//        return cosclient;
//    }

    public COSClient newClient(COSFileConfig cosFileConfig) {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(cosFileConfig.getSecretId(), cosFileConfig.getSecretKey());
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
        //ap-beijing ap-beijing-1

        ClientConfig clientConfig = new ClientConfig(new Region(cosFileConfig.getRegion()));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式 "mybucket-1251668577"
//        String bucketName = cosFileConfig.getBucketName() + "-" + cosFileConfig.getAppId();

        return cosClient;
    }
}
