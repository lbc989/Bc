/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.lagou.edu.MyAnnotation.schema;


import com.lagou.edu.MyAnnotation.beans.AnnotationBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by jychai on 17/6/28.
 */
public class AnnotationNamespaceHandler extends NamespaceHandlerSupport {


    public void init() {
        registerBeanDefinitionParser("annotation", new AnnotationBeanDefinitionParser(AnnotationBean.class, true));
    }
}
