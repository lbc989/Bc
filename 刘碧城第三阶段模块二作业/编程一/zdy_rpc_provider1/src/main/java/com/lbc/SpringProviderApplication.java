package com.lbc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringProviderApplication {
    public static void main(String[] args) {
        System.out.println("**************springboot服务开始启动**************");
        SpringApplication.run(SpringProviderApplication.class, args);
        System.out.println("**************springboot服务启动完成**************");
    }
}
