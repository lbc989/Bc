package com.lbc.demo.service.Impl;

import com.lbc.mvcframework.annotation.MyService;
import com.lbc.demo.service.IDemoService;
@MyService
public class DemoServiceImpl implements IDemoService {
    @Override
    public String get(String name) {
        System.out.println("service实现类中的name：" + name);
        return name;
    }
}
