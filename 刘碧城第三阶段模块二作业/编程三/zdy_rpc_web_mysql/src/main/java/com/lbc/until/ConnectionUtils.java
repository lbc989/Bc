package com.lbc.until;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ConnectionUtils {
    public Connection getConnection() throws SQLException {
        // 从连接池拿连接
        return DruidUtils.getInstance().getConnection();
    }
}
