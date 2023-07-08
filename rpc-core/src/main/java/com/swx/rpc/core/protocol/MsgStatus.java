package com.swx.rpc.core.protocol;

import lombok.Getter;

public enum MsgStatus {
    SUCCESS((byte) 1),
    FAIL((byte) 0);
    @Getter
    private byte status;

    MsgStatus(byte status){
        this.status=status;
    }
    public static boolean isSuccess(byte status){
        return MsgStatus.SUCCESS.getStatus()==status;
    }


}
