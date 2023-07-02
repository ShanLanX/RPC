package com.swx.core.balancer;

import com.swx.core.common.ServiceInfo;

import java.util.List;

public interface LoadBalance {
    ServiceInfo chooseService(List<ServiceInfo> services);
}
