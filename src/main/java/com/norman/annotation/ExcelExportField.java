package com.norman.annotation;

import java.lang.annotation.*;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2020/5/23 11:14 PM.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
@Inherited
public @interface ExcelExportField {

    int cellIndex() default -1;


    String type() default "";
}
