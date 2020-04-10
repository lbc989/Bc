package com.lbc.client;

import com.alibaba.fastjson.JSONObject;
import com.lbc.service.UserService;
import com.lbc.service.UserServiceImpl;

public class ClientBootStrap {


    public static void main(String[] args) throws InterruptedException {
        //{"className":"com.lbc.service.UserServiceImpl","methodName":"sayHello","objects":["are you ok?"],"types":["java.lang.String"]}
        UserService userService = new UserServiceImpl();
        userService = RpcConsumer.create(userService);

        while(true){
            Thread.sleep(2000);
            System.err.println(userService.sayHello("are you ok?"));

        }

    }
}
