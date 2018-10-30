package com.norman.guava;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.PeekingIterator;

import java.util.List;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/30 10:30 AM.
 */
public class PeekingIteratorTest {

    /**
     * Iterators提供一个Iterators.peekingIterator(Iterator)方法，来把Iterator包装为PeekingIterator，这是Iterator的子类，它能让你提前查看下一次调用next()返回的元素
     * 注意：Iterators.peekingIterator返回的PeekingIterator不支持在peek()操作之后调用remove()方法。
     *
     * @param args
     */
    public static void main(String[] args) {
        //复制一个List，并去除连续的重复元素。

        List<String> source = Lists.newArrayList("a","c","c","a","d","c");

        source.sort(Ordering.natural());

        List<String> result = Lists.newArrayList();
        PeekingIterator<String> iter = Iterators.peekingIterator(source.iterator());
        while (iter.hasNext()) {
            String current = iter.next();
            while (iter.hasNext() && iter.peek().equals(current)) {
                // skip this duplicate element
                iter.next();
            }
            result.add(current);
        }

        System.out.println(result);
    }
}
