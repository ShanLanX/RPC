package com.swx.rpc.client.transport;
// 工厂模式
public class NetClientTransportFactroy {
    public static NetClientTransport getNetClient(String type){
        switch (type){
            case "Netty":{
                return new NettyNetClientTransport();
            }
            default:
                return new NettyNetClientTransport();
        }


    }


}
