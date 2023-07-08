package com.swx.rpc.core.protocol;

import lombok.Getter;

public enum MsgType {
    REQUEST((byte) 0),
    RESPONSE((byte) 1);
    @Getter
    private byte type;
    MsgType(byte type){
        this.type=type;
    }
    public static MsgType findByType(byte type){
        for(MsgType msgType:MsgType.values()){
            if(msgType.getType()==type)
                return msgType;
        }
        return null;
    }

}
