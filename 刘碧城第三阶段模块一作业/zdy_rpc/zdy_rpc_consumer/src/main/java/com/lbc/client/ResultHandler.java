package com.lbc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.InvocationTargetException;

public class ResultHandler extends ChannelInboundHandlerAdapter {

    public ResultHandler(Object o) throws Exception {
        this.response = (String) o;
    }

    private String response;
    
    public String getResponse() {
    return response;  
}


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client接收到服务器返回的消息:" + msg);
    }  
      
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exception is general");  
    }  
}
