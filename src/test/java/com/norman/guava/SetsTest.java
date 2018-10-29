package com.norman.guava;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/29 4:15 PM.
 */
public class SetsTest {

    public static void main(String[] args) {
        // 静态工厂方法
        Sets.newHashSet();
        Sets.newHashSet(1, 2, 3);
        Sets.newHashSetWithExpectedSize(10);
        Sets.newHashSet(Lists.newArrayList(1, 2, 3));

        Sets.newLinkedHashSet();
        Sets.newLinkedHashSetWithExpectedSize(10);
        Sets.newLinkedHashSet(Lists.newArrayList(1, 2, 3));

        Sets.newTreeSet();
        Sets.newTreeSet(Lists.newArrayList(1, 2, 3));
        Sets.newTreeSet(Ordering.natural());

        // 集合运算(返回SetView)
        final Sets.SetView<Integer> union = Sets.union(Sets.newHashSet(1, 2, 3), Sets.newHashSet(4, 5, 6));// 取并集[1,2,3,4,5,6]
        System.out.println(union);
        final Sets.SetView<Integer> intersection = Sets.intersection(Sets.newHashSet(1, 2, 3), Sets.newHashSet(3, 4, 5));// 取交集[3]
        System.out.println(intersection);
        final Sets.SetView<Integer> difference = Sets.difference(Sets.newHashSet(1, 2, 3), Sets.newHashSet(3, 4, 5));// 只在set1, 不在set2[1,2]
        System.out.println(difference);
        final Sets.SetView<Integer> symmetricDifference = Sets.symmetricDifference(Sets.newHashSet(1, 2, 3), Sets.newHashSet(3, 4, 5));// 交集取反[1,2,4,5]
        System.out.println(symmetricDifference);

        // 其他工具方法
        System.out.println(Sets.cartesianProduct(Lists.newArrayList(Sets.newHashSet(1, 2), Sets.newHashSet(3, 4)))); // 返回所有集合的笛卡尔积
        final Set<Set<Integer>> sets = Sets.powerSet(Sets.newHashSet(1, 2, 3));// 返回给定集合的所有子集

        sets.forEach(set -> System.out.println(set));

//        System.out.println(sets);


    }
}
