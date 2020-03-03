/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.lagou.edu.MyAnnotation.beans;


import com.lagou.edu.MyAnnotation.MyAutowired;
import com.lagou.edu.dao.AccountDao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;



@Component
public class AnnotationBean implements BeanFactoryPostProcessor, BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;




    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        {
            try {
                Object jdkDynamicProxyTargetObject = ProxyFactory.getTarget(bean);

                Class<?> clazz = jdkDynamicProxyTargetObject.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {//查找字段中含有依赖注入的字段 存在就进行注入
                    MyAutowired myAutowired = field.getAnnotation(MyAutowired.class);
                    if (myAutowired != null) {
                        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
                        field.setAccessible(true);
                        try {
                            field.set(bean,accountDao);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            System.out.println("Inject the " + field.getName() + "failed!!");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
