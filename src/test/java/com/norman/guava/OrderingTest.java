package com.norman.guava;

import com.google.common.base.Defaults;
import com.google.common.collect.Ordering;

import java.util.Arrays;
import java.util.List;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/15 下午3:56.
 */
public class OrderingTest {
    /**
     * reverse();                            //返回与当前Ordering相反的排序
     * nullsFirst();                         //返回一个将null放在non-null元素之前的Ordering，其他的和原始的Ordering一样
     * nullsLast();                          //返回一个将null放在non-null元素之后的Ordering，其他的和原始的Ordering一样
     * compound(Comparator);                 //返回一个使用Comparator的Ordering，Comparator作为第二排序元素
     * lexicographical();                    //返回一个按照字典元素迭代的Ordering
     * onResultOf(Function);                 //将function应用在各个元素上之后, 在使用原始ordering进行排序
     * greatestOf(Iterable iterable, int k); //返回指定的前k个可迭代的最大的元素，按照当前Ordering从最大到最小的顺序
     * leastOf(Iterable iterable, int k);    //返回指定的前k个可迭代的最小的元素，按照当前Ordering从最小到最大的顺序
     * isOrdered(Iterable);                  //是否有序(前面的元素可以大于或等于后面的元素)，Iterable不能少于2个元素
     * isStrictlyOrdered(Iterable);          //是否严格有序(前面的元素必须大于后面的元素)，Iterable不能少于两个元素
     * sortedCopy(Iterable);                 //返回指定的元素作为一个列表的排序副本
     *
     * @param args
     */
    public static void main(String[] args) {
        final List<String> list = Arrays.asList("test", "123", "abc", "acc", null);

        final Ordering<Comparable> natural = Ordering.natural().nullsFirst();

        // 使用Comparable类型的自然顺序， 例如：整数从小到大，字符串是按字典顺序;
        list.sort(natural);
        System.out.println(list);

        list.sort(Ordering.usingToString().nullsLast());
        System.out.println(list);

        list.sort(natural.nullsFirst());
        System.out.println(list);

        System.out.println(Defaults.defaultValue(int.class));
        System.out.println(Defaults.defaultValue(char.class));

    }
}
