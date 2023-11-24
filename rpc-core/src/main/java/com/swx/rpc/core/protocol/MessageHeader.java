package com.swx.rpc.core.protocol;

import com.swx.rpc.core.serialization.SerializationType;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * 消息头部
 */
@Data
public class MessageHeader implements Serializable {

    /*
    +---------------------------------------------------------------+
    | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
    +---------------------------------------------------------------+
    | 状态 1byte |        消息 ID 32byte     |      数据长度 4byte     |
    +---------------------------------------------------------------+
    */
    /**
     *  魔数
     */
    private short magic;

    /**
     *  协议版本号
     */
    private byte version;

    /**
     *  序列化算法
     */
    private byte serialization;

    /**
     *  报文类型
     */
    private byte msgType;

    /**
     *  状态
     */
    private byte MsgStatus;

    /**
     *  消息 ID
     */
    private String requestId;

    /**
     *  数据长度
     */
    private int msgLen;


    public static MessageHeader build(String serialization){
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setMagic(ProtocolConstants.MAGIC);
        messageHeader.setVersion(ProtocolConstants.VERSION);
        messageHeader.setRequestId(UUID.randomUUID().toString().replaceAll("-",""));
        messageHeader.setMsgType(MsgType.REQUEST.getType());
        messageHeader.setSerialization(SerializationType.parseByName(serialization).getType());
        return messageHeader;
    }
}
