package com.swx.rpc.client.proxy;

import com.swx.rpc.client.config.RpcClientProperties;
import com.swx.rpc.core.discovery.DiscoveryService;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ClientStubProxyFactory {
    private Map<Class<?>,Object> objectCache=new HashMap<>();


    // T为被代理的对象 执行T中的任何方法都会由句柄中的invoker增强

    //This method returns a proxy instance of the given class, with the given version, using the given discovery service and RPC client properties
public <T> T getProxy(Class<T> clazz, String version, DiscoveryService discoveryService, RpcClientProperties rpcClientProperties){
        //Compute the proxy instance if it is not already present in the object cache
        return (T)objectCache.computeIfAbsent(clazz,clz->
             //Create a new proxy instance using the given class, classloader, class array and ClientStubInvocationHandler
             Proxy.newProxyInstance(clz.getClassLoader(),new Class[]{clz},new ClientStubInvocationHandler(discoveryService,rpcClientProperties,clz,version))
        );
    }
}
