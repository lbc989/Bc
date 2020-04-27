package com.lbc.service;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * 更新节点时间
 */
public class ZooKeeperUpdate implements Watcher , Runnable {
    private static final String BASE_SERVICE = "/zookeeper";
    private static final String SERVICE_NAME = "/server";

    private static String data;
    private static String ip;
    private static int port;
    private static String value;

    private static ZooKeeper zooKeeper;

    public ZooKeeperUpdate() {}

    public ZooKeeperUpdate(String ip, int port, String updateTime) throws InterruptedException, IOException {
        this.data = ip + ":" + port;
        this.ip = ip;
        this.port = port;
        this.value = updateTime;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
            String base = BASE_SERVICE + SERVICE_NAME;
            String path = base + "/"+ip+":" + port;
            //时间更新到最后
            Stat stat = zooKeeper.setData(path,  value.getBytes(), -1);
            //5秒之间有没有再次调用
            Thread.sleep(5000);
            //取节点内容
            String data = new String(zooKeeper.getData(path, false, null));
            //相等则代表该时间点没有新的请求
            if(data.equals(value)){
                //更新时间为0
                zooKeeper.setData(path,  "0:0".getBytes(), -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            //创建连接
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new ZooKeeperUpdate());
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
