package com.swx.core.serialization;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

public class FastJsonSerialization implements RpcSerialization {
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) throws IOException {
        return JSON.parseObject(new String(data),cls);
    }
}
