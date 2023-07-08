package com.swx.rpc.core.serialization;

import java.io.IOException;

public interface RpcSerialization {
    /**
     * 序列化
     * @param obj
     * @return
     * @param <T>
     * @throws IOException
     */
    // 定义两个泛型方法
    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] data,Class<T> cls) throws IOException;



}
