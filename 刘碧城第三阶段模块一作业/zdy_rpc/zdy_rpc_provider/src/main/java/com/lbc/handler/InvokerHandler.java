package com.lbc.handler;


import com.lbc.pojo.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class InvokerHandler extends ChannelInboundHandlerAdapter {
	public static ConcurrentHashMap<String, Object> classMap = new ConcurrentHashMap<String,Object>();
	@Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest rpcRequest = (RpcRequest)msg;
        Object claszz = null;
        //用于记录反射获得类，这样可以提高性能
		if(!classMap.containsKey(rpcRequest.getClassName())){
			try {
				claszz = Class.forName(rpcRequest.getClassName()).newInstance();
				classMap.put(rpcRequest.getClassName(), claszz);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else {
			claszz = classMap.get(rpcRequest.getClassName());
		}
		Method method = claszz.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getTypes());
        Object result = method.invoke(claszz, rpcRequest.getObjects());
        ctx.write(result);
        ctx.flush();  
        ctx.close();
    }  
	@Override  
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	     cause.printStackTrace();  
	     ctx.close();  
	}  

}
