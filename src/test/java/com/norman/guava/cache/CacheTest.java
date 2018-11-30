package com.norman.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.graph.Graph;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/30 10:38 AM.
 */
public class CacheTest {

    public static void main(String[] args) throws Exception {
        LoadingCache<String, Graph> graphs = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, Graph>() {
                            public Graph load(String key) throws Exception {
//                                return createExpensiveGraph(key);
                                return null;
                            }
                        });


        try {
            String key = "";
            System.out.println(graphs.get(key));
        } catch (ExecutionException e) {
            throw new Exception(e.getCause());
        }
    }
}
