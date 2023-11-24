package com.swx.rpc.provider.service;


import com.rrtv.rpc.server.annotation.RpcService;
import com.swx.rpc.api.service.HelloWorldService;
//import com.swx.rpc.server.annotation.RpcService;

@RpcService(interfaceType = HelloWorldService.class, version = "1.0")
public class HelloWorldImpl implements HelloWorldService {


    @Override
    public String sayHello(String name) {

            return String.format("您好，%s调用成功",name);
    }
}
