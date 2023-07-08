package com.swx.rpc.core.coder;

import com.swx.rpc.core.common.RpcRequest;
import com.swx.rpc.core.common.RpcResponse;
import com.swx.rpc.core.protocol.MessageHeader;
import com.swx.rpc.core.protocol.MessageProtocol;
import com.swx.rpc.core.protocol.MsgType;
import com.swx.rpc.core.protocol.ProtocolConstants;
import com.swx.rpc.core.serialization.RpcSerialization;
import com.swx.rpc.core.serialization.SerializationFactory;
import com.swx.rpc.core.serialization.SerializationType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.jboss.netty.util.CharsetUtil;

import java.util.List;

public class RpcDecoder<T> extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < ProtocolConstants.HEADER_TOTAL_LEN) {
            // 可读的数据小于请求头总的大小 直接丢弃
            return;
        }
        // 标记 ByteBuf 读指针位置
        byteBuf.markReaderIndex();

        // 魔数
        short magic=byteBuf.readShort();
        if(magic!=ProtocolConstants.MAGIC){
            throw new IllegalArgumentException("magic number is illegal "+magic);
        }

        byte version=byteBuf.readByte();
        byte serialization=byteBuf.readByte();
        byte msgType=byteBuf.readByte();
        byte status=byteBuf.readByte();
        CharSequence requestId=byteBuf.readCharSequence(ProtocolConstants.REQ_LEN, CharsetUtil.UTF_8);

        int dataLen=byteBuf.readInt();
        if(byteBuf.readableBytes()<dataLen){
            // 重置读指针位置
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data=new byte[dataLen];
        byteBuf.readBytes(data);
        MsgType msgTypeEnum=MsgType.findByType(msgType);
        if(msgTypeEnum==null){
            return;
        }

        MessageHeader messageHeader=new MessageHeader();
        messageHeader.setMagic(magic);
        messageHeader.setVersion(version);
        messageHeader.setSerialization(serialization);
        messageHeader.setMsgStatus(status);
        messageHeader.setRequestId(String.valueOf(requestId));
        messageHeader.setMsgType(msgType);
        messageHeader.setMsgLen(dataLen);

        // 序列化方法
        RpcSerialization rpcSerialization= SerializationFactory.getSerialization(SerializationType.parseByType(messageHeader.getSerialization()));
        switch (msgTypeEnum){
            case REQUEST:{
                RpcRequest request = rpcSerialization.deserialize(data, RpcRequest.class);
                if (request != null) {
                    MessageProtocol<RpcRequest> protocol = new MessageProtocol<>();
                    protocol.setHeader(messageHeader);
                    protocol.setBody(request);
                    list.add(protocol);
                }
                break;

            }
            case RESPONSE:{
                RpcResponse response = rpcSerialization.deserialize(data, RpcResponse.class);
                if (response != null) {
                    MessageProtocol<RpcResponse> protocol = new MessageProtocol<>();
                    protocol.setHeader(messageHeader);
                    protocol.setBody(response);
                    list.add(protocol);
                }
                break;

            }
            default:{

            }
        }







    }
}
