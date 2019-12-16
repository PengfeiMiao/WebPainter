package com.example.test.annotation;

import java.lang.annotation.*;

/**
 * 数据库对象不映射注解
 *
 * @author hml
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoColumn {

}
