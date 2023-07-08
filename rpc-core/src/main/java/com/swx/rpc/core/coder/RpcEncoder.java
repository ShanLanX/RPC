package com.swx.rpc.core.coder;

import com.swx.rpc.core.protocol.MessageHeader;
import com.swx.rpc.core.protocol.MessageProtocol;
import com.swx.rpc.core.serialization.RpcSerialization;
import com.swx.rpc.core.serialization.SerializationFactory;
import com.swx.rpc.core.serialization.SerializationType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.jboss.netty.util.CharsetUtil;

public class RpcEncoder<T> extends MessageToByteEncoder<MessageProtocol<T>> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol<T> messageProtocol, ByteBuf byteBuf) throws Exception {
        MessageHeader messageHeader=messageProtocol.getHeader();
        // 写入魔数
        byteBuf.writeShort(messageHeader.getMagic());

        // 写入协议版本号
        byteBuf.writeByte(messageHeader.getVersion());

        // 写入序列化算法
        byteBuf.writeByte(messageHeader.getSerialization());

        // 写入报文类型
        byteBuf.writeByte(messageHeader.getMsgType());

        // 写入状态
        byteBuf.writeByte(messageHeader.getMsgStatus());

        // 写入消息ID
        byteBuf.writeCharSequence(messageHeader.getRequestId(),CharsetUtil.UTF_8);

        RpcSerialization serialization= SerializationFactory.getSerialization(SerializationType.parseByType(messageHeader.getSerialization()));
        byte[] body=serialization.serialize(messageProtocol.getBody());

        // 写入长度 字节数组的大小
        byteBuf.writeInt(body.length);

        // 写入body
        byteBuf.writeBytes(body);

    }
}
