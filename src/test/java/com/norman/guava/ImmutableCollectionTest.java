package com.norman.guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/15 下午4:21.
 */
public class ImmutableCollectionTest {

    public static void main(String[] args) {
        //JDK自带的Collections.unmodifiableXXX实现的不是真正的不可变集合，当原始集合修改后，不可变集合也发生变化。

        /**
         * 它不安全: 如果有对象reference原始的被封装的集合类，这些方法返回的集合也就不是正真的不可改变
         * 效率低: 因为它返回的数据结构本质仍旧是原来的集合类，所以它的操作开销，包括并发下修改检查，hash table里的额外数据空间都和原来的集合是一样的。
         */
        List<String> lists = Lists.newArrayList("aa", "bb", "cc");


        List<String> unmodifiedLists = Collections.unmodifiableList(lists);

        System.out.println(lists.size() + " " + unmodifiedLists.size());

        lists.add("dd");
        System.out.println(lists.size() + " " + unmodifiedLists.size());

        Set<String> set = new HashSet<>();
        set.add("aa");
        set.add("bb");
        set.add("cc");

        ImmutableSet<String> imset = ImmutableSet.copyOf(set);
        System.out.println(set.size() + " " + imset.size());
        set.add("dd");
        System.out.println(set.size() + " " + imset.size());


        imset = ImmutableSet.of("aa", "bb", "cc");
        System.out.println(imset);

        final ImmutableSet<String> sets = ImmutableSet.<String>builder().add("aa", "bb", "cc").build();

        ImmutableMap.of("a", 1, "b", "2");

        testMutiSet();

    }

    /**
     * Multiset和Set的区别就是可以保存多个相同的对象。
     * Multiset占据了List和Set之间的一个灰色地带：允许重复，但是不保证顺序。
     */
    public static void testMutiSet() {
        Multiset<String> multiset = HashMultiset.create();

        multiset.setCount("aa", 2);
        multiset.add("bb", 3);
        multiset.add("bb");
        System.out.println(multiset.count("bb"));
        System.out.println(multiset.count("aa"));


        System.out.println(multiset.elementSet().stream().collect(Collectors.joining(", ")));
        System.out.println(multiset.entrySet().stream().map( e -> e.getElement()).collect(Collectors.joining(", ")));

        System.out.println(multiset.entrySet().size());
        System.out.println(multiset.elementSet().size());
    }
}
