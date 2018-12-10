package com.norman.config;

import lombok.Data;
import lombok.ToString;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/12/4 10:42 AM.
 */
@Data
@ToString
public class COSFileConfig {

    /**
     * appId
     */
    private Long appId;

    /**
     * secretId
     */
    private String secretId;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * bucketName
     */
    private String bucketName;

    /**
     * region
     */
    private String region;


    public String getBucket() {
        return bucketName + "-" + appId;
    }


}
