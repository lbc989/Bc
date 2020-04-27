package com.lbc.server;

import com.lbc.config.ServiceConfig;
import com.lbc.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 服务器启动
 */
@Component
@Order(value=1)
public class UserServerStart implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("**************UserServerStart 开始执行**************");
        UserServiceImpl.startServer(ServiceConfig.getIP(),ServiceConfig.getPORT());
        System.out.println("**************UserServerStart 执行完成**************");
    }
}
