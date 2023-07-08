package com.swx.rpc.core.utils;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.swx.rpc.core.exception.ErrorEnum;
import com.swx.rpc.core.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
@Slf4j
public class NacosUtil {
    private static final String DEFAULT_NACOS_ADDRESS="127.0.0.1:8848";
    private static final NamingService namingService;
    private static final Set<String> serviceNames;
    static {
        serviceNames=new HashSet<>();
        namingService=getNacosNamingService();
        // TODO 可以根据配置文件动态配置hostname和port

    }

    /**
     * 获取namingService
     * @return
     */
    private static NamingService getNacosNamingService() {
        // 通过工厂方法创建一个namingService
        try {
            return NamingFactory.createNamingService(DEFAULT_NACOS_ADDRESS);
        } catch (NacosException e) {
            log.error("connect to nacos [{}] fail", DEFAULT_NACOS_ADDRESS);
            throw new RpcException(ErrorEnum.FAILED_TO_CONNECT_TO_REGISTRY.getMessage());
        }
    }
    public static NamingService getNamingService(){
        return namingService;

    }




}
