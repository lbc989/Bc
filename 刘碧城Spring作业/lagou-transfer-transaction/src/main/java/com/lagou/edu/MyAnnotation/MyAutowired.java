package com.lagou.edu.MyAnnotation;



import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutowired {

}
