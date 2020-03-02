/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.lagou.edu.MyAnnotation.beans;


import com.lagou.edu.MyAnnotation.MyService;
import com.lagou.edu.MyAnnotation.utils.Constants;
import com.lagou.edu.MyAnnotation.utils.ReflectUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * Created by jychai on 17/6/30.
 */
public class AnnotationBean implements BeanFactoryPostProcessor, BeanPostProcessor, ApplicationContextAware {


    private String annotationPackage;

    private ApplicationContext applicationContext;

    public String getAnnotationPackage() {
        return annotationPackage;
    }

    public void setAnnotationPackage(String annotationPackage) {
        this.annotationPackage = annotationPackage;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {
        if (annotationPackage == null || annotationPackage.length() == 0) {
            return;
        }

        if (configurableListableBeanFactory instanceof BeanDefinitionRegistry) {
            try {
                // init scanner
                Class<?> scannerClass = ReflectUtils.forName("org.springframework.context.annotation.ClassPathBeanDefinitionScanner");
                Object scanner = scannerClass.getConstructor(new Class<?>[] {BeanDefinitionRegistry.class, boolean.class}).newInstance(new Object[] {(BeanDefinitionRegistry) configurableListableBeanFactory, true});
                // add filter
                Class<?> filterClass = ReflectUtils.forName("org.springframework.core.type.filter.AnnotationTypeFilter");
                Object filter = filterClass.getConstructor(Class.class).newInstance(MyService.class);
                Method addIncludeFilter = scannerClass.getMethod("addIncludeFilter", ReflectUtils.forName("org.springframework.core.type.filter.TypeFilter"));
                addIncludeFilter.invoke(scanner, filter);
                // scan packages
                String[] packages = Constants.COMMA_SPLIT_PATTERN.split(annotationPackage);
                Method scan = scannerClass.getMethod("scan", new Class<?>[]{String[].class});
                scan.invoke(scanner, new Object[] {packages});
            } catch (Throwable e) {

            }
        }

    }

    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {

        MyService service = o.getClass().getAnnotation(MyService.class);
        if (service != null) {
            ServiceBean<Object> serviceBean = new ServiceBean(service);
            serviceBean.setRef(o);
            if (applicationContext != null) {
                serviceBean.setApplicationContext(applicationContext);
                try {
                    serviceBean.afterPropertiesSet();
                } catch (RuntimeException e) {
                    throw (RuntimeException) e;
                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }

            }
        }
        return o;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
