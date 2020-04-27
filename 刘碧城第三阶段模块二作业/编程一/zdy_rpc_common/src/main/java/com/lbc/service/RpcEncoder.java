package com.lbc.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 */
public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> clazz;
    private Serializer serializer;

    public RpcEncoder(Class<?> clazz, Serializer serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }

    /**
     * 编码
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {
        if (null != clazz && clazz.isInstance(msg)) {
            byte[] bytes = serializer.serialize(msg);
            byteBuf.writeInt(bytes.length);//写入int类型的数据长度
            byteBuf.writeBytes(bytes);
        }
    }
}
