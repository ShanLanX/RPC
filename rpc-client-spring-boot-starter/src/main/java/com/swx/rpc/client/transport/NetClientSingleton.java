package com.swx.rpc.client.transport;
// 单例模式
public class NetClientSingleton {
    private static volatile NetClientTransport netClientTransport;
    public static NetClientTransport getNetClient(String type){
        if(netClientTransport==null){
            synchronized (NetClientSingleton.class){
                if(netClientTransport==null)
                {
                    switch (type){
                        case "Netty":{
                            netClientTransport=new NettyNetClientTransport();
                            break;
                        }
                        default:
                            netClientTransport=new NettyNetClientTransport();
                      }
                }
            }

        }
        return netClientTransport;

    }
}
