package com.lbc.service;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

/**
 * JSON序列化
 */
public class JSONSerializer implements  Serializer {

    /**
     * 序列化
     */
    @Override
    public byte[] serialize(Object object) throws IOException {
        return JSON.toJSONBytes(object);
    }

    /**
     * 反序列化
     */
    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException {
        return JSON.parseObject(bytes, clazz);
    }
}
