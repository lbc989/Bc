package com.lbc.service;

import org.I0Itec.zkclient.ZkClient;

public class ZkclientUpdate implements Runnable {

    private static final String BASE_SERVICE = "/zookeeper";
    private static final String SERVICE_NAME = "/server";

    private static ZkClient zkClient;
    private static int port;
    private static String ip;
    private static long time;

    public ZkclientUpdate(String ip, int port) {
        this.port = port;
        this.ip = ip;
    }

    @Override
    public void run() {
        //创建连接
        zkClient = new ZkClient("127.0.0.1:2181");
        zkClient.setZkSerializer(new MyZkSerializer());
        String base = BASE_SERVICE + SERVICE_NAME;
        String path = base + "/child-" + port;
        //更新
        zkClient.writeData(path, ip + ":" + port + ":" + time);
    }
}
