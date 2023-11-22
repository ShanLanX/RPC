package com.swx.rpc.client.proxy;

import com.swx.rpc.client.config.RpcClientProperties;
import com.swx.rpc.core.discovery.DiscoveryService;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ClientStubProxyFactory {
    private Map<Class<?>,Object> objectCache=new HashMap<>();


    // T为被代理的对象 执行T中的任何方法都会由句柄中的invoker增强

    public <T> T getProxy(Class<T> clazz, String version, DiscoveryService discoveryService, RpcClientProperties rpcClientProperties){
        return (T)objectCache.computeIfAbsent(clazz,clz->
             Proxy.newProxyInstance(clz.getClassLoader(),new Class[]{clz},new ClientStubInvocationHandler(discoveryService,rpcClientProperties,clz,version))
        );
    }
}
