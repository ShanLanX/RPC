package com.swx.rpc.server.config;

import com.swx.rpc.core.exception.ErrorEnum;
import com.swx.rpc.core.register.NacosRegistryService;
import com.swx.rpc.core.register.RegistryService;
import com.swx.rpc.core.register.ZookeeperRegistryService;
import com.swx.rpc.server.transport.NettyRpcServer;
import com.swx.rpc.server.transport.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
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
        switch (rpcServerProperties.getRegistryType()) {
            case "ZOOKEEPER": {
                return new ZookeeperRegistryService(rpcServerProperties.getRegistryAddr());

            }
            case "NACOS": {
                return new NacosRegistryService();

            }
            default: {
                throw new RuntimeException("找不到指定的注册中心");
            }
        }

    }

    /**
     * 配置网络通信
     */
    @Bean
    @ConditionalOnMissingBean
    public RpcServer RpcServer() {
        return new NettyRpcServer();
    }

    /**
     * 配置provider
     */
    // TODO 配置provider







}
