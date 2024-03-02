package com.swx.rpc.server;

import com.swx.rpc.core.common.ServiceInfo;
import com.swx.rpc.core.common.ServiceUtil;
import com.swx.rpc.core.register.RegistryService;
import com.swx.rpc.server.annotation.RpcService;
import com.swx.rpc.server.config.RpcServerProperties;
import com.swx.rpc.server.store.LocalServerCache;
import com.swx.rpc.server.transport.RpcServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class RpcServerProvider implements BeanPostProcessor, CommandLineRunner {
    private RegistryService registryService;

    private RpcServerProperties properties;

    private RpcServer rpcServer;

    public RpcServerProvider(RegistryService registryService, RpcServerProperties properties, RpcServer rpcServer) {
        this.registryService = registryService;
        this.properties = properties;
        this.rpcServer = rpcServer;
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> rpcServer.start(properties.getPort())).start();
        log.info(" rpc server :{} start, appName :{} , port :{}", rpcServer, properties.getAppName(), properties.getPort());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                // 关闭之后把服务从ZK上清除
                registryService.destroy();
                log.info("清除所有服务");
            }catch (Exception ex){
                log.error("", ex);
            }

        }));

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        if(rpcService!=null){
            String serviceName=rpcService.interfaceType().getName();
            String version=rpcService.version();
            LocalServerCache.store(ServiceUtil.serviceKey(serviceName, version), bean);

            ServiceInfo serviceInfo=new ServiceInfo();
            serviceInfo.setServiceName(ServiceUtil.serviceKey(serviceName,version));
            serviceInfo.setPort(properties.getPort());
            try {
                serviceInfo.setAddress(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            serviceInfo.setApplicationName(properties.getAppName());

            // 服务注册
            try {
                registryService.register(serviceInfo);
                log.info("服务注册成功");
            } catch (Exception e) {
                log.error("服务注册出错：{}",e.getStackTrace());
            }



        }

        return bean;
    }



}
