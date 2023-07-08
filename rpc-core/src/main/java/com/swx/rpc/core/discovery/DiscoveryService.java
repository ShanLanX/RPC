package com.swx.rpc.core.discovery;

import com.swx.rpc.core.common.ServiceInfo;

/**
 * 服务发现
 */
public interface DiscoveryService {
    ServiceInfo discovery(String serviceName) throws Exception;

}
