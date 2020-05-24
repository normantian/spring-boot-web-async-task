package com.norman.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface ExcelExport {
    String desc() default "";

    int sheetIndex() default 0;

    String templateFileName() default "";
}
