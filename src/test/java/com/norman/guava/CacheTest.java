package com.norman.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/9 上午9:17.
 */
public class CacheTest {

    static class UserService {

        private final Cache<String, String> cache;

        public UserService() {
            /**
             * 5秒自动过期
             */
            cache = CacheBuilder.newBuilder()
                    .expireAfterWrite(5, TimeUnit.SECONDS).build();

        }

        public String getUserName(String id) throws Exception {
            return cache.get(id, () -> {
                System.out.println("method invoke2");
                //这里执行查询数据库，等其他复杂的逻辑
                return "User:" + id;
            });
        }

        public static void main(String[] args) throws Exception {
            UserService us = new UserService();
            for (int i = 0; i < 20; i++) {
                System.out.println(us.getUserName("1001"));
                TimeUnit.SECONDS.sleep(1);
            }
        }

    }

    static class UserService2 {

        private final LoadingCache<String, String> cache;

        public UserService2() {
            /*** 5秒自动过期		 */
            cache = CacheBuilder.newBuilder()
                    .expireAfterWrite(5, TimeUnit.SECONDS)
                    .build(new CacheLoader<String, String>() {

                public String load(String id) throws Exception {
                    System.out.println("method inovke");
                    //这里执行查询数据库，等其他复杂的逻辑
                    return "User:" + id;
                }
            });

        }

        public String getUserName(String id) throws Exception {
            return cache.get(id);
        }

        public static void main(String[] args) throws Exception {
            UserService2 us = new UserService2();
            for (int i = 0; i < 20; i++) {
                System.out.println(us.getUserName("1001"));
                TimeUnit.SECONDS.sleep(1);
            }
        }

    }

}
