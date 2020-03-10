package com.lbc.demo.controller;

import com.lbc.demo.service.IDemoService;
import com.lbc.mvcframework.annotation.MyAutoWired;
import com.lbc.mvcframework.annotation.MyController;
import com.lbc.mvcframework.annotation.MyRequestMapping;
import com.lbc.mvcframework.annotation.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpRequest;

@MyController
@MyRequestMapping("/demo")
public class DemoController {
    @MyAutoWired
    private IDemoService demoService;

    /**
     *
     * @param name
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @MyRequestMapping("/query")
    @Security("zhangsan")
    public String query(String name, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
       return demoService.get(name);
    }

    @MyRequestMapping("/query2")
    @Security("lisi")
    public String query2(String name, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return demoService.get(name);
    }

}
