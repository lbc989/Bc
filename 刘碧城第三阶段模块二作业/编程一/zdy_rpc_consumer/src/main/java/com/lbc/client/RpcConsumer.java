package com.lbc.client;

import com.lbc.service.JSONSerializer;
import com.lbc.service.RpcEncoder;
import com.lbc.service.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcConsumer {

    //创建线程池对象
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static UserClientHandler userClientHandler;

    //1.创建一个代理对象
    public Object createProxy(final Class<?> serviceClass, final RpcRequest rpcRequest, final String host, final int port) {

        //借助JDK动态代理生成代理对象并返回结果
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //（1）调用初始化netty客户端的方法
                if(null == userClientHandler) {
                    initClient(host, port);
                }
                //（2）设置参数
                userClientHandler.setPara(rpcRequest);
                //（3）去服务端请求数据
                return executor.submit(userClientHandler).get();
            }
        });
    }


    //初始化netty客户端
    public static void initClient(String host, int port) throws InterruptedException {
        userClientHandler = new UserClientHandler();

        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(userClientHandler);
                    }
                });
        bootstrap.connect(host, port).sync();
    }

}
