package com.swx.rpc.core.register;

import com.swx.rpc.core.balancer.RandomBalance;
import com.swx.rpc.core.common.ServiceInfo;
import com.swx.rpc.core.discovery.NacosDiscoveryService;

public class NacosRegisterDemo {
    public static void main(String[] args) throws Exception {
        ServiceInfo serviceInfo=new ServiceInfo();
        serviceInfo.setAddress("127.0.0.1");
        serviceInfo.setPort(10010);
        serviceInfo.setServiceName("nacos1");
//         服务注册
        NacosRegistryService nacosRegistryService=new NacosRegistryService();
        nacosRegistryService.register(serviceInfo);
        // 服务发现
        NacosDiscoveryService nacosDiscoveryService=new NacosDiscoveryService(new RandomBalance());
        ServiceInfo serviceInfo1=nacosDiscoveryService.discovery("nacos1");
        System.out.println(serviceInfo1.getServiceName());




    }
}
