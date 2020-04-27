package com.lbc.until;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidUtils {
    private static DruidDataSource druidDataSource = new DruidDataSource();

    public DruidUtils(String driverClassName, String url, String username, String password) {
        druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }
}
