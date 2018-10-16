package com.norman.guava;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/15 下午6:18.
 */
public class CollectionsTest {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        List theseElements = Lists.newArrayList("alpha", "beta", "gamma"); //可以直接初始化的静态构造方法
        List exactly100 = Lists.newArrayListWithCapacity(100); //更具可读性的工厂方法
        List approx100 = Lists.newArrayListWithExpectedSize(100); //更具可读性的工厂方法
        Set set = Sets.newHashSet();
        Set approx100Set = Sets.newHashSetWithExpectedSize(100);

        list = Lists.newArrayList("11", "22", "33", "22");
        final int frequency = Collections.frequency(list, "22");
        System.out.println(frequency);

        System.out.println(Iterators.frequency(list.iterator(), "22"));

        final Iterator<String> limit = Iterators.limit(list.iterator(), 3);
        limit.forEachRemaining(s -> {
            System.out.println(s);
        });


    }
}
