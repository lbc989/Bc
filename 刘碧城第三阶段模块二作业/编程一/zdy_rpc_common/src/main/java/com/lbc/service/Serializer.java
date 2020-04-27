package com.lbc.service;

import java.io.IOException;

/**
 * 序列化接口
 */
public interface Serializer {

    /**
     * 序列化
     */
    byte[] serialize(Object object) throws IOException;


    /**
     * 反序列化
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException;
}
