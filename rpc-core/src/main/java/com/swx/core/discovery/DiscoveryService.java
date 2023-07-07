package com.swx.core.discovery;

import com.swx.core.common.ServiceInfo;

/**
 * 服务发现
 */
public interface DiscoveryService {
    ServiceInfo discovery(String serviceName) throws Exception;

}
