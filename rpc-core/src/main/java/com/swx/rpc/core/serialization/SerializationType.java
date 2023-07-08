package com.swx.rpc.core.serialization;

import lombok.Getter;

public enum SerializationType {
    HESSIAN((byte) 0),JSON((byte) 1) , FASTJSON((byte)2);
    @Getter
    private byte type;
    SerializationType(byte type){
        this.type=type;
    }
    public static SerializationType parseByName(String typeName){
        for(SerializationType serializationType:SerializationType.values()){
            // 忽略大小写
            if(serializationType.name().equalsIgnoreCase(typeName)){
                return serializationType;
            }
        }
        // 默认使用Hessian
        return HESSIAN;
    }
    public static SerializationType parseByType(byte type){
        for(SerializationType serializationType:SerializationType.values()){
                if(serializationType.getType()==type){
                return serializationType;
            }
        }
        return HESSIAN;
    }






}
