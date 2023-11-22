package com.swx.rpc.client.transport;

import com.swx.rpc.client.cache.LocalRpcResponseCache;
import com.swx.rpc.client.handler.RpcResponseHandler;
import com.swx.rpc.core.coder.RpcDecoder;
import com.swx.rpc.core.coder.RpcEncoder;
import com.swx.rpc.core.common.RpcRequest;
import com.swx.rpc.core.common.RpcResponse;
import com.swx.rpc.core.protocol.MessageProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyNetClientTransport implements NetClientTransport{
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;
    private final RpcResponseHandler handler;

    public NettyNetClientTransport() {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup(4);
        // 返回响应
        handler = new RpcResponseHandler();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()

                                // 解码 是入站操作 将二进制解码成消息
                                .addLast(new RpcDecoder())
                                // 接收响应 入站操作
                                .addLast(handler)
                                // 编码 是出站操作 将消息编写二进制
                                .addLast(new RpcEncoder<>());
                    }
                });
    }
    @Override
    public MessageProtocol<RpcResponse> sendRequest(RequestMetadata metadata) throws Exception {
        MessageProtocol<RpcRequest> protocol = metadata.getProtocol();
        RpcFuture<MessageProtocol<RpcResponse>> future = new RpcFuture<>();
        String requestId=protocol.getHeader().getRequestId();
        // 保存请求id和request的映射关系
        LocalRpcResponseCache.add(requestId,future);
        // 使用TCP连接
        ChannelFuture channelFuture = bootstrap.connect(metadata.getAddress(), metadata.getPort()).sync();
        channelFuture.addListener(a->{
            if(channelFuture.isSuccess()){
                log.info("connect {} on port {} success!",metadata.getAddress(),metadata.getPort());
            }
            else {
                log.info("connect {} on port {} failed!",metadata.getAddress(),metadata.getPort());
                channelFuture.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }

        });
        // 写入数据
        channelFuture.channel().writeAndFlush(protocol);
        return metadata.getTimeout() != null ? future.get(metadata.getTimeout(), TimeUnit.MILLISECONDS) : future.get();









    }

}
