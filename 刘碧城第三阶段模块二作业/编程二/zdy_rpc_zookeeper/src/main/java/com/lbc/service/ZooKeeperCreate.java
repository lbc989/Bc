package com.lbc.service;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 创建临时节点
 */
public class ZooKeeperCreate implements Watcher {
    private static final String BASE_SERVICE = "/zookeeper";
    private static final String SERVICE_NAME = "/server";

    private static String data;
    private static int port;
    private static String ip;

    //countDownLatch这个类使⼀个线程等待,主要不让main⽅法结束
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    public ZooKeeperCreate() {}

    public ZooKeeperCreate(String ip, int port) throws InterruptedException, IOException {
        this.data = ip + ":" + port;
        this.port = port;
        this.ip = ip;
        //创建连接
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new ZooKeeperCreate());
        countDownLatch.await();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //当连接创建了，服务端发送给客户端SyncConnected事件
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
        //开始服务注册
        doCreate();
    }

    private void doCreate() {
        String base = BASE_SERVICE + SERVICE_NAME;
        String path = base + "/" + ip + ":" + port;
        try {
            //开始服务注册
            Stat exists = zooKeeper.exists(base, false);
            //判断服务端节点是否存在，不存在则创建
            if (exists == null) {
                System.out.println("**************服务端节点为空，路径为：" + base);
                zooKeeper.create(base, "service_list".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            if (null != zooKeeper.exists(path, false)) {
                zooKeeper.delete(path, -1);
            }
            zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("**************服务注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
