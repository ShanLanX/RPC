package com.swx.rpc.server.transport;


import com.swx.rpc.core.coder.RpcDecoder;
import com.swx.rpc.core.coder.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetAddress;

/**
 * 利用netty作为网络传输的服务端
 */
public class NettyRpcServer implements RpcServer{

    @Override
    public void start(int port) {
        // 接收来自客户端的请求
        EventLoopGroup boss = new NioEventLoopGroup();
        // 处理来自客户端的请求
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            String serverAddress= InetAddress.getLocalHost().getHostAddress();
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new RpcDecoder())
                                    .addLast(new RpcEncoder());

                        }
                    });
            // sync()表示进程会被阻塞
            ChannelFuture channelFuture=bootstrap.bind(serverAddress,port).sync();
            channelFuture.channel().closeFuture().sync();

        }
        catch (Exception e){

        }

    }
}
