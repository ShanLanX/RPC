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
    public static MessageHeader build(String serialization){
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setMagic(ProtocolConstants.MAGIC);
        messageHeader.setVersion(ProtocolConstants.VERSION);
        // 设置请求的唯一id
        messageHeader.setRequestId(UUID.randomUUID().toString().replaceAll("-",""));
        messageHeader.setMsgType(MsgType.REQUEST.getType());
        messageHeader.setSerialization(SerializationType.parseByName(serialization).getType());
        return messageHeader;

    }


}
