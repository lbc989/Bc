package com.lbc.config;

import org.springframework.stereotype.Component;

/**
 * 服务器配置
 */
@Component
public class ServiceConfig {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 8992;

    public static String getIP() {
        return IP;
    }

    public static int getPORT() {
        return PORT;
    }
}
