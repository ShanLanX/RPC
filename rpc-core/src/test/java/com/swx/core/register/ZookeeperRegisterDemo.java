package com.swx.core.register;

import com.swx.core.balancer.RandomBalance;
import com.swx.core.common.ServiceInfo;
import com.swx.core.discovery.ZookeeperDiscoveryService;

public class ZookeeperRegisterDemo {
    public static void main(String[] args) throws Exception {
        ServiceInfo serviceInfo=new ServiceInfo();
        serviceInfo.setAddress("127.0.0.1");
        serviceInfo.setPort(10010);
        serviceInfo.setServiceName("zk1");
        ZookeeperRegistryService zookeeperRegistryService=new ZookeeperRegistryService("127.0.0.1:2181");
        zookeeperRegistryService.register(serviceInfo);
        ZookeeperDiscoveryService zookeeperDiscoveryService=new ZookeeperDiscoveryService("127.0.0.1:2181",new RandomBalance());
        ServiceInfo serviceInfo1=zookeeperDiscoveryService.discovery("zk1");
        System.out.println(serviceInfo1.getServiceName());
    }
}
