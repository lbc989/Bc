package com.lagou.edu.MyAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})   //该注解用于方法
@Retention(RetentionPolicy.RUNTIME)   //在运行期间保留注解
public @interface MyTransactional {

}
