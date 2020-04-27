package com.lbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMysqlApplication {
    public static void main(String[] args) {
        System.out.println("**************服务开始启动**************");
        SpringApplication.run(SpringMysqlApplication.class, args);
        System.out.println("**************服务启动完成**************");
    }
}
