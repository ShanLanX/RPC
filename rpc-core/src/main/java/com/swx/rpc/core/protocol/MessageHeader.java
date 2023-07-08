package com.swx.rpc.core.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息头部
 */
@Data
public class MessageHeader implements Serializable {
    /**
     * 魔数
     */
    private short magic;
    /**
     * 版本号
     */
    private byte version;
    /**
     * 序列化算法
     */
    private byte serialization;

    /**
     * 报文类型
     */
    private byte msgType;

    /**
     * 状态
     */
    private  byte msgStatus;
    /**
     * 消息ID
     */
    // TODO 获取修改为messageID更为合适
    private String requestId;
    /**
     * 消息长度
     */
    private int msgLen;


}
