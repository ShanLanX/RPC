package com.swx.rpc.core.protocol;

import com.swx.rpc.core.serialization.SerializationType;

import java.util.UUID;

public class MessageHeaderFactory {
    public static MessageHeader getMessageHeader(String serialization){
        MessageHeader messageHeader=new MessageHeader();
        messageHeader.setMagic(ProtocolConstants.MAGIC);
        messageHeader.setVersion(ProtocolConstants.VERSION);
        messageHeader.setRequestId(UUID.randomUUID().toString().replaceAll("-",""));
        messageHeader.setMsgType(MsgType.REQUEST.getType());
        messageHeader.setSerialization(SerializationType.parseByName(serialization).getType());
        return messageHeader;
    }
}
