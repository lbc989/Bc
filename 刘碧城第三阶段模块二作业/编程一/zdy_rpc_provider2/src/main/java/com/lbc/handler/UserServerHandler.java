package com.lbc.handler;

import com.lbc.config.ServiceConfig;
import com.lbc.service.RpcRequest;
import com.lbc.service.UserServiceImpl;
import com.lbc.service.ZooKeeperUpdate;
import com.lbc.utils.ApplicationContextUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class UserServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        long startTime = System.currentTimeMillis();
        //将msg强制类型转换为RpcRequest
        RpcRequest rpcRequest = (RpcRequest) msg;
        //类名
        String className = rpcRequest.getClassName();
        //方法名
        String methodName = rpcRequest.getMethodName();
        //参数类型
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        //入参
        Object[] parameters = rpcRequest.getParameters();
        // 1、根据类名获取对象
        UserServiceImpl bean = (UserServiceImpl) ApplicationContextUtils.getBean(className);
        // 2、获取方法
        Method method = bean.getClass().getMethod(methodName, parameterTypes);
        // 3、反射调用并返回数据
        Object result = method.invoke(bean, parameters);
        long endTime = System.currentTimeMillis();
        //更新时间
        new Thread(new ZooKeeperUpdate(ServiceConfig.getIP(),ServiceConfig.getPORT(),
                String.valueOf(endTime) +":"+String.valueOf(endTime-startTime))).start();
        ctx.writeAndFlush(result);
    }
}
