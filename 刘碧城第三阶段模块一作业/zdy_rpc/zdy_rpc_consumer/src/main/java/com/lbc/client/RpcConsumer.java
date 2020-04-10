package com.lbc.client;


import com.alibaba.fastjson.JSONObject;
import com.lbc.Serializer.JSONSerializer;
import com.lbc.Serializer.RpcDecoder;
import com.lbc.Serializer.RpcEncoder;
import com.lbc.pojo.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcConsumer {


    private static ExecutorService executor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //providerName: UserService#sayHello



    @SuppressWarnings("unchecked")
    public static <T> T create(Object target){

        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(), new InvocationHandler(){

            @Override
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {

                RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.setClassName(target.getClass().getName());
                rpcRequest.setMethodName(method.getName());
                rpcRequest.setObjects(args);
                rpcRequest.setTypes(method.getParameterTypes());
                // System.out.println(JSONObject.toJSONString(rpcRequest));
                // ResultHandler resultHandler = new ResultHandler("");
                EventLoopGroup group = new NioEventLoopGroup();
                try {
                    Bootstrap b = new Bootstrap();
                    b.group(group)
                            .channel(NioSocketChannel.class)
                            .option(ChannelOption.TCP_NODELAY, true)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {
                                    ChannelPipeline pipeline = ch.pipeline();
                                    pipeline.addLast(new RpcEncoder(RpcRequest.class,new JSONSerializer()));
                                    pipeline.addLast(new RpcDecoder(RpcRequest.class,new JSONSerializer()));
                                    // pipeline.addLast("encoder", new ObjectEncoder());
                                    // pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                    // pipeline.addLast("handler",resultHandler);
                                }
                            });

                    ChannelFuture future = b.connect("localhost", 8080).sync();
                    future.channel().writeAndFlush(rpcRequest).sync();
                    future.channel().closeFuture().sync();
                } finally {
                    group.shutdownGracefully();
                }
                return "success ";
                // return target.toString();
            }
        });
    }
}
