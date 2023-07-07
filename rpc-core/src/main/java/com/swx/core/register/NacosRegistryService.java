package com.swx.core.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.swx.core.common.ServiceInfo;
import com.swx.core.exception.ErrorEnum;
import com.swx.core.exception.RpcException;
import com.swx.core.utils.NacosUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
