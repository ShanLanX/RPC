package com.swx.core.register;

import com.swx.core.common.ServiceInfo;

/**
 * 服务注册
 */
public interface RegistryService {
    void register(ServiceInfo serviceInfo) throws Exception ;
    void unRegister (ServiceInfo serviceInfo) throws Exception;
    void destroy() throws  Exception;
}
