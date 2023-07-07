package com.swx.core.protocol;

import java.io.Serializable;

public class MessageProtocol<T> implements Serializable {
    /**
     *  消息头
     */
    private MessageHeader header;

    /**
     *  消息体
     */
    private T body;
}
