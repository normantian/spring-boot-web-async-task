package com.norman.guava;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

import static com.google.common.base.Preconditions.*;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/15 下午3:30.
 */
public class PreconditionsTest {

    public boolean someMethod(int arg1, String arg2, String arg3, Object arg4) {
        if (arg1 > 0 && StringUtils.isNotEmpty(arg2) && StringUtils.isNotEmpty(arg3) && arg4 != null) {
            return false; // maybe需要将具体的错误类型告诉调用方
        }
        // ...
        return false;

    }
    // 检查boolean是否为真
    // 失败时抛出 IllegalArgumentException

    public boolean someMethod2(int arg1, String arg2, String arg3, Object arg4) {

        // 检查boolean是否为真
        // 失败时抛出 IllegalArgumentException
        checkArgument(arg1 > 0, "arg1 is error");
        // 检查value是否为null
        // 失败时抛出 NullPointerException
        checkNotNull(arg2, "arg2 is not null");
        // 检查对象的一些状态, 不依赖方法参数(相比checkArgument, 在某些情况下更有语义...)
        // 失败时抛出 IllegalStateException
        checkState(Arrays.asList("hello","world").contains(arg3),"arg3 is error");

        // 检查index是否在合法范围[0, size)(不包含size)
        // 失败时抛出 IndexOutOfBoundsException
//        Preconditions.checkElementIndex(arg1, 10, "arg1 index out of bounds");

        // 检查位置是否在合法范围[0, size](包含size)
        // 失败时抛出 IndexOutOfBoundsException
        checkPositionIndex(arg1, 10, "arg5 index out of bounds");

        // 检查[start, end)是一个长度为size的集合合法的子集范围
        // 失败时抛出 IndexOutOfBoundsException
        checkPositionIndexes(3, arg1, 10);

        return true;
    }

    public static void main(String[] args) {
        PreconditionsTest test = new PreconditionsTest();
//        test.someMethod2(0, "string", "string2", "hello");
//        test.someMethod2(1, null, "string2", "hello");
//        test.someMethod2(1, "string1", "string2", "hello");
//        test.someMethod2(10, "string1", "hello", "hello");
//        test.someMethod2(10, "string1", "hello", "hello");
        test.someMethod2(2, "string1", "hello", "hello");
    }

}
