package com.swx.core.balancer;

import com.swx.core.common.ServiceInfo;

import java.util.List;
// TODO 权重负载均衡 可能需要修改服务端代码
public class WeightBalance implements LoadBalance{
    @Override
    public ServiceInfo chooseService(List<ServiceInfo> services) {
        return null;
    }
}
