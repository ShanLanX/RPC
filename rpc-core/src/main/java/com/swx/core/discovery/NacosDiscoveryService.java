package com.swx.core.discovery;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.swx.core.balancer.LoadBalance;
import com.swx.core.common.ServiceInfo;
import com.swx.core.exception.ErrorEnum;
import com.swx.core.exception.RpcException;
import com.swx.core.utils.NacosUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NacosDiscoveryService implements DiscoveryService{
    private NamingService namingService;
    private LoadBalance loadBalance;
    public NacosDiscoveryService( LoadBalance loadBalance){
        this.loadBalance=loadBalance;
        namingService= NacosUtil.getNamingService();

    }
    @Override
    public ServiceInfo discovery(String serviceName) throws Exception {
        List<ServiceInfo> services=getAllInstances(serviceName);
        return loadBalance.chooseService(services);

    }

    /**
     * 根据服务名获取服务下的所有实例
     * @return
     */

    public List<ServiceInfo> getAllInstances(String serviceName) throws NacosException {
        // 获取到所有服务
        List<Instance> allInstances=namingService.getAllInstances(serviceName);
        List<ServiceInfo> services=new ArrayList<>();
        for(Instance instance:allInstances){
            ServiceInfo serviceInfo=new ServiceInfo();
            serviceInfo.setServiceName(instance.getServiceName());
            serviceInfo.setAddress(instance.getIp());
            serviceInfo.setPort(instance.getPort());
            services.add(serviceInfo);

        }
        return services;
    }
}
