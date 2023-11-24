package com.swx.rpc.client.proxy;

import com.swx.rpc.client.config.RpcClientProperties;
import com.swx.rpc.client.transport.NetClientTransportFactroy;
import com.swx.rpc.client.transport.RequestMetadata;
import com.swx.rpc.core.common.RpcRequest;
import com.swx.rpc.core.common.RpcResponse;
import com.swx.rpc.core.common.ServiceInfo;
import com.swx.rpc.core.common.ServiceUtil;
import com.swx.rpc.core.discovery.DiscoveryService;
import com.swx.rpc.core.exception.ResourceNotFoundException;
import com.swx.rpc.core.exception.RpcException;
import com.swx.rpc.core.protocol.MessageHeader;
import com.swx.rpc.core.protocol.MessageProtocol;
import com.swx.rpc.core.protocol.MsgStatus;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
@Slf4j
public class ClientStubInvocationHandler implements InvocationHandler {

    private DiscoveryService discoveryService;
    private RpcClientProperties rpcClientProperties;
    private Class<?> clazz;

    private String version;
    public ClientStubInvocationHandler(DiscoveryService discoveryService, RpcClientProperties rpcClientProperties,Class<?> clazz,String version ){
        this.discoveryService=discoveryService;
        this.rpcClientProperties=rpcClientProperties;
        this.clazz=clazz;
        this.version=version;


    }
    // 行为控制
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 获取服务信息 clazz是需要调用的实例对应的类
        ServiceInfo serviceInfo=discoveryService.discovery(ServiceUtil.serviceKey(this.clazz.getName(),this.version));
        if(serviceInfo==null) {
            throw new ResourceNotFoundException("404");
        }
            // 设置请求头
            MessageProtocol<RpcRequest> messageProtocol=new MessageProtocol<>();
//            messageProtocol.setHeader(MessageHeader.buildHeader(rpcClientProperties.getSerialization()));
            MessageHeader messageHeader=MessageHeader.build(rpcClientProperties.getSerialization());
            messageProtocol.setHeader(messageHeader);
            // 设置请求体
            RpcRequest rpcRequest=new RpcRequest();
            rpcRequest.setMethod(method.getName());
            rpcRequest.setServiceKey(ServiceUtil.serviceKey(this.clazz.getName(),this.version));
            rpcRequest.setParameters(args);
            rpcRequest.setParameterTypes(method.getParameterTypes());
            messageProtocol.setBody(rpcRequest);

            // 发送网络请求 拿到结果
            MessageProtocol<RpcResponse> responseMessageProtocol= NetClientTransportFactroy.getNetClient("Netty").
                    sendRequest(RequestMetadata.builder().protocol(messageProtocol).address(serviceInfo.getAddress()).
                            port(serviceInfo.getPort()).timeout(rpcClientProperties.getTimeout()).build());
            if(responseMessageProtocol==null){
                log.error("请求超时");
                throw new RpcException("rpc调用结果失败，请求超时"+rpcClientProperties.getTimeout());
            }
            if(!MsgStatus.isSuccess(responseMessageProtocol.getHeader().getMsgStatus())){
                log.error("rpc调用失败，message:{}",responseMessageProtocol.getBody().getMessage());
                throw new RpcException(responseMessageProtocol.getBody().getMessage());

            }
        return responseMessageProtocol.getBody().getData();

        }

}
