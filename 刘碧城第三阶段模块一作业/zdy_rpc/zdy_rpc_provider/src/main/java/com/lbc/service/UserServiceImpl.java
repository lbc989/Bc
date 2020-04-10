package com.lbc.service;


import com.lbc.Serializer.JSONSerializer;
import com.lbc.Serializer.RpcDecoder;
import com.lbc.Serializer.RpcEncoder;
import com.lbc.handler.*;
import com.lbc.pojo.RpcRequest;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    public String sayHello(String word) {
        System.out.println("success "+word);
        return "success "+word;
    }

    public static void startServer(String hostName,int port) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // pipeline.addLast("encoder", new ObjectEncoder());
                        // pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        pipeline.addLast(new RpcEncoder(RpcRequest.class,new JSONSerializer()));
                        pipeline.addLast(new RpcDecoder(RpcRequest.class,new JSONSerializer()));
                        pipeline.addLast(new InvokerHandler());
                    }
                });

        bootstrap.bind(hostName, port).sync();
        System.out.println("Server start listen at " + port );


    }
}
