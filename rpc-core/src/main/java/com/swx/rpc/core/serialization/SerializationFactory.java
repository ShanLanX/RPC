package com.swx.rpc.core.serialization;

/**
 * 序列化工厂，用于返回序列化的方法
 */
public class SerializationFactory {
    public static RpcSerialization getSerialization(SerializationType type){
        switch (type){
            case JSON:{
                return new JsonSerialization();

            }
            case FASTJSON:{
                return new FastJsonSerialization();

            }
            case HESSIAN:{
                return new HessianSerialization();

            }
            default:{
                throw  new IllegalArgumentException("serialization type is illegal");
            }
        }
    }
}
