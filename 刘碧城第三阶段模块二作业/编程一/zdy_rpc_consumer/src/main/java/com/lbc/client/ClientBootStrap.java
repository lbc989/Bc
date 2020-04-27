package com.lbc.client;

import com.lbc.service.RpcRequest;
import com.lbc.service.UserService;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientBootStrap implements Watcher {
    private static final String BASE_SERVICE = "/zookeeper";
    private static final String SERVICE_NAME = "/server";

    private static ZooKeeper zooKeeper;
    private static String path;
    private static RpcRequest rpcRequest;
    //是否是第一次连接
    private static boolean isFirst = true;

    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
            //监听节点发生变化
            checkNodeChange(watchedEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        initParam();
        //获取zookeeper节点信息 创建连接
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 10000, new ClientBootStrap());
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 初始化参数
     */
    private static void initParam() {
        path = BASE_SERVICE + SERVICE_NAME;
        //初始化参数
        rpcRequest = initRpcRequest();
    }

    private static RpcRequest initRpcRequest() {
        RpcRequest rpcRequest = new RpcRequest();
        // 请求对象的ID
        rpcRequest.setRequestId(UUID.randomUUID().toString());
        // 类名
        rpcRequest.setClassName("userServiceImpl");
        // 方法名
        rpcRequest.setMethodName("sayHello");
        // 参数类型
        Class<?>[] parameterTypes = new Class[1];
        parameterTypes[0] = String.class;
        rpcRequest.setParameterTypes(parameterTypes);
        // 入参
        Object[] parameters = new Object[1];
        parameters[0] = "Are you OK?";
        rpcRequest.setParameters(parameters);
        return rpcRequest;
    }

    /**
     * 获取所有子节点列表
     *
     * @param watchedEvent
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void checkNodeChange(WatchedEvent watchedEvent) throws KeeperException, InterruptedException {
        //当连接创建了，服务端发送给客户端SyncConnected事件
        if (Event.KeeperState.SyncConnected == watchedEvent.getState() && isFirst) {
            isFirst = false;
            try {
                //获取所有子节点列表
                List<String> children = zooKeeper.getChildren(path, true);
                //开始连接
                doCon(children);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //⼦节点列表发⽣变化时，服务器会发出NodeChildrenChanged通知,但不会把变化情况告诉给客户端
        //需要客户端⾃⾏获取，且通知是⼀次性的，需反复注册监听
        if (Event.EventType.NodeChildrenChanged == watchedEvent.getType()) {
            //获取所有子节点信息
            try {
                List<String> children = zooKeeper.getChildren(path, true);
                //开始连接
                doCon(children);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doCon(List<String> children) throws InterruptedException, UnsupportedEncodingException, KeeperException {
        //子节点数据信息
        List<String[]> childrenData = getChildrenData(children);
        //与每个节点建立连接
        startConnection(childrenData);
    }

    /**
     * 获取节点数据信息
     *
     * @param children
     * @return List<String[]>
     * @throws KeeperException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     */
    private List<String[]> getChildrenData(List<String> children) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        List<String[]> result = new ArrayList<>();
        System.out.println("**************节点名称:" + children.toString() + "**************");
        //循环节点列表，获取节点信息
        for (String child : children) {
            String[] nodeName = child.split(":");
            String data = new String(zooKeeper.getData(path + "/" + child, true, null), "utf-8");
            if ("".equals(data)) {
                data = "0:0";
            }
            String[] nodeValue = data.split(":");
            //两台机器同时连接
            getAllNode(result,nodeName,nodeValue);
        }
        return result;
    }

    private void getAllNode(List<String[]> result, String[] nodeName, String[] nodeValue) {
        result.add(new String[]{nodeName[0], nodeName[1], nodeValue[0], nodeValue[1]});
    }

    private void startConnection(List<String[]> childrenData) throws InterruptedException {
        System.out.println("**************连接开始！**************");
        //循环节点信息
        for (String[] childData : childrenData) {
            String host = childData[0];
            String port = childData[1];
            RpcConsumer rpcConsumer = new RpcConsumer();
            UserService proxy = (UserService) rpcConsumer.createProxy(UserService.class, rpcRequest, host, Integer.valueOf(port));
        }
        System.out.println("**************连接结束！**************");
    }

}
