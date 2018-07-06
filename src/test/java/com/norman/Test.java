package com.norman;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianfei on 2018/7/5.
 */
public class Test {

    private static int size = 1000000;
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size);
    public static void main(String[] args){
//        for (int i = 0; i < size; i++) {
//            bloomFilter.put(i);
//        }
//        long startTime = System.nanoTime(); // 获取开始时间
//        //判断这一百万个数中是否包含29999这个数
//        if (bloomFilter.mightContain(29999)) {
//            System.out.println("命中了");
//        }
//        long endTime = System.nanoTime();   // 获取结束时间
//        System.out.println("程序运行时间： " + (endTime - startTime) + "纳秒");

        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }
        List<Integer> list = new ArrayList(1000);
        //故意取10000个不在过滤器里的值，看看有多少个会被认为在过滤器里
        for (int i = size + 10000; i < size + 20000; i++) {
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("误判的数量：" + list.size());
    }
}
