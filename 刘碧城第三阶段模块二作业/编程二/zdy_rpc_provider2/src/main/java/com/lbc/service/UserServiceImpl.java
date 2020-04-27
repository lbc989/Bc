package com.lbc.service;

import com.lbc.config.ServiceConfig;
import com.lbc.handler.UserServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public String sayHello(String word) {
        System.out.println(ServiceConfig.getIP()+":"+ServiceConfig.getPORT()+" 服务端调用成功——>参数：" + word);
        return ServiceConfig.getIP()+":"+ServiceConfig.getPORT() + "机器建立连接成功！";
    }

    //hostName:ip地址  port:端口号
    public static void startServer(String hostName, int port) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                        pipeline.addLast(new UserServerHandler());

                    }
                });
        serverBootstrap.bind(hostName, port).sync();
    }
}
