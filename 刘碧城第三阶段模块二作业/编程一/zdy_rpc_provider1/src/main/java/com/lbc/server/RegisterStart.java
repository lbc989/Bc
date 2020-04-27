package com.lbc.server;

import com.lbc.config.ServiceConfig;
import com.lbc.service.ZooKeeperCreate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 服务注册
 */
@Component
@Order(value=2)
public class RegisterStart implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("**************RegisterStart 开始执行**************");
        new ZooKeeperCreate(ServiceConfig.getIP(),ServiceConfig.getPORT());
        System.out.println("**************RegisterStart 执行完成**************");
    }
}
