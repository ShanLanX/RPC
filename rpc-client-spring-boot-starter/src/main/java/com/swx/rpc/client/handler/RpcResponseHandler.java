package com.swx.rpc.client.handler;

import com.swx.rpc.client.cache.LocalRpcResponseCache;
import com.swx.rpc.core.common.RpcResponse;
import com.swx.rpc.core.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcResponseHandler extends SimpleChannelInboundHandler<MessageProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol<RpcResponse> rpcResponseMessageProtocol) throws Exception {
        String requestId=rpcResponseMessageProtocol.getHeader().getRequestId();
        // 设置响应
        LocalRpcResponseCache.fillResponse(requestId,rpcResponseMessageProtocol);
    }
}
