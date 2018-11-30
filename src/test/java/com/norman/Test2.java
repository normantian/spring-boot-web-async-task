package com.norman;

import org.apache.commons.codec.digest.Md5Crypt;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/11/26 5:12 PM.
 */
public class Test2 {

    private static final String[] SALT_DATA_SOURCE = new String[]{"$1$a1Bef7829d235ebc", "$1$bade123512d8a3f5", "$1$5d8a9e1b8d8a3ce1", "$1$e41ad6e6fb731d9e", "$1$b91ad4ef8bd8fead", "$1$3ade5fedb64a9538", "$1$987ade91bde913ad", "$1$98de12bf83adf931"};

    public static String getSaltValue(String userName) {
        return SALT_DATA_SOURCE[Math.abs(userName.hashCode() % SALT_DATA_SOURCE.length)];
    }

    public static void main(String[] args) {
        String userId = "tianfei";

        String password = "1234qwer";
        System.out.println(Md5Crypt.md5Crypt(password.getBytes(), getSaltValue(userId)));
    }
}
