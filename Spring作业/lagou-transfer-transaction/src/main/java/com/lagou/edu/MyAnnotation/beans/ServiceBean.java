/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.lagou.edu.MyAnnotation.beans;


import com.lagou.edu.MyAnnotation.MyService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * Created by jychai on 17/6/28.
 */
public class ServiceBean<T> implements InitializingBean, ApplicationContextAware {

    private MyService myService;

    private ApplicationContext applicationContext;

    public ServiceBean(MyService myService) {
        this.myService = myService;
    }

    private T ref;

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println(myService.value());

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
