package com.norman.guava;

import com.google.common.base.Optional;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/15 下午2:51.
 */
public class OptionalTest {

    public static void main(String[] args) {
        Integer i = 100;
        Optional<Integer> possible = Optional.fromNullable(i);
        //若Optional包含的T实例不为null，则返回true；若T实例为null，返回false
        boolean present = possible.isPresent();
        System.out.println(present);
        //返回Optional包含的T实例，该T实例必须不为空；否则，抛出一个IllegalStateException异常
        Integer t = possible.get();
        System.out.println(t);

        String string = "sdf";
        final Optional<String> of = Optional.of(string);//将一个T的实例转换为Optional对象(T不可以为空)
        System.out.println(of.get());
        final Optional<Object> absent = Optional.absent();//获得一个Optional对象，其内部包含了空值
        System.out.println(absent.isPresent());
        //将一个T的实例转换为Optional对象，T的实例可以不为空，也可以为空
        Optional<String> str = Optional.fromNullable(null);
        System.out.println(str);
        //Optional.fromNullable(null) 等同于 Optional.absent()

        //若Optional包含的T实例不为null, 返回T; 否则, 返回参数输入的T实例
        System.out.println(str.or("defaultValue"));
        System.out.println(str.orNull());



    }
}
