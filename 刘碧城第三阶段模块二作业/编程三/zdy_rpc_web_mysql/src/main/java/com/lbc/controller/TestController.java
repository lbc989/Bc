package com.lbc.controller;

import com.lbc.until.ConnectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private ConnectionUtils connectionUtils;

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody
    public String query() throws Exception {
        String result = null;
        // 获取连接
        Connection con = connectionUtils.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement("select database() as data_base_name");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            result = resultSet.getString("data_base_name");
        }
        resultSet.close();
        preparedStatement.close();
        return "当前访问的数据库名为：" + result;
    }
}
