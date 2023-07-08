package com.swx.rpc.core.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.swx.rpc.core.common.ServiceInfo;
import com.swx.rpc.core.utils.NacosUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NacosRegistryService implements RegistryService{
    private NamingService namingService;
    public NacosRegistryService(){
        namingService= NacosUtil.getNamingService();
    }

    @Override
    public void register(ServiceInfo serviceInfo) throws NacosException {

        namingService.registerInstance(serviceInfo.getServiceName(),serviceInfo.getAddress(), serviceInfo.getPort());


    }



    @Override
    public void unRegister(ServiceInfo serviceInfo) throws NacosException {
        namingService.deregisterInstance(serviceInfo.getServiceName(),serviceInfo.getAddress(), serviceInfo.getPort());

    }

    @Override
    public void destroy() {




    }



}
