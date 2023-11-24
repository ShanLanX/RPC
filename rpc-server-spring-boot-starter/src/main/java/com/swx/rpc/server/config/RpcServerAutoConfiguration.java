package com.swx.rpc.server.config;

import com.swx.rpc.core.exception.ErrorEnum;
import com.swx.rpc.core.register.NacosRegistryService;
import com.swx.rpc.core.register.RegistryService;
import com.swx.rpc.core.register.ZookeeperRegistryService;
import com.swx.rpc.server.RpcServerProvider;
import com.swx.rpc.server.transport.NettyRpcServer;
import com.swx.rpc.server.transport.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcServerAutoConfiguration {
    @Autowired
    RpcServerProperties rpcServerProperties;

    /**
     * 配置注册中心
     *
     * @return
     */

    @Bean
    @ConditionalOnMissingBean
    public RegistryService registryService() {
        return new ZookeeperRegistryService(rpcServerProperties.getRegistryAddr());
    }

    @Bean
    @ConditionalOnMissingBean(RpcServer.class)
    public RpcServer RpcServer() {
        return new NettyRpcServer();
    }

    @Bean
    @ConditionalOnMissingBean(RpcServerProvider.class)
    public RpcServerProvider rpcServerProvider(@Autowired RegistryService registryService,
                                               @Autowired RpcServer rpcServer,
                                               @Autowired RpcServerProperties rpcServerProperties){
        return new RpcServerProvider(registryService,rpcServerProperties,rpcServer);
    }







}
