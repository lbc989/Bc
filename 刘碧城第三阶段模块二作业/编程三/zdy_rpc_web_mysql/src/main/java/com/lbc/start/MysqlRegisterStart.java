package com.lbc.start;

import com.google.gson.Gson;
import com.lbc.until.DruidUtils;
import com.lbc.until.MyZkSerializer;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MysqlRegisterStart implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Gson gson = new Gson();
        System.out.println("**************开始监听数据库**************");
        String path = "/zookeeper/mysql";
        Map<String, String> mysqlConfigData = new HashMap<>();
        mysqlConfigData.put("driverClassName", "com.mysql.jdbc.Driver");
        mysqlConfigData.put("url", "jdbc:mysql://localhost:3306/zookeeper");
        mysqlConfigData.put("username", "root");
        mysqlConfigData.put("password", "root");
        //创建连接
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        zkClient.setZkSerializer(new MyZkSerializer());
        //判断节点是否存在
        boolean exists = zkClient.exists(path);
        //不存在则创建默认配置
        if (!exists) {
            zkClient.createPersistent(path, gson.toJson(mysqlConfigData));
        }else{
            //存在则获取最新的数据库连接数据
            Object data = zkClient.readData(path);
            Map<String, String> map = gson.fromJson(data.toString(), Map.class);
            new DruidUtils(map.get("driverClassName"), map.get("url"), map.get("username"), map.get("password"));
        }

        //注册监听
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            public void handleDataChange(String path, Object data) throws Exception {
                System.out.println("**************该内容被更新**************");
                Map<String, String> map = gson.fromJson(data.toString(), Map.class);
                new DruidUtils(map.get("driverClassName"), map.get("url"), map.get("username"), map.get("password"));
            }

            public void handleDataDeleted(String s) throws Exception {
                System.out.println("**************该节点被删除**************");
            }
        });

    }


}
