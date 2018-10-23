package com.norman.guava;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.primitives.Ints;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/15 下午4:07.
 */
public class ObjectTest {

    public static void main(String[] args) {
        System.out.println(Ints.compare(0,1));

        System.out.println(Objects.equal("test","test"));
        System.out.println(Objects.equal(null,"test"));

        System.out.println(Objects.equal(null,null));

        //ComparisonChain是一个lazy的比较过程， 当比较结果为0的时候， 即相等的时候， 会继续比较下去， 出现非0的情况， 就会忽略后面的比较
//        ComparisonChain.start()
//                .compare(this.aString, that.aString)
//                .compare(this.anInt, that.anInt)
//                .compare(this.anEnum, that.anEnum, Ordering.natural().nullsLast())
//                .result();
    }
}
