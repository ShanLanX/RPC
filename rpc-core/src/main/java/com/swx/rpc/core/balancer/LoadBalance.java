package com.swx.rpc.core.balancer;

import com.swx.rpc.core.common.ServiceInfo;

import java.util.List;

public interface LoadBalance {
    ServiceInfo chooseService(List<ServiceInfo> services);
}
