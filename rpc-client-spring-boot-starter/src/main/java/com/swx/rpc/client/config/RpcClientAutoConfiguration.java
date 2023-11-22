package com.swx.rpc.client.config;

import com.swx.rpc.core.balancer.FullRoundBalance;
import com.swx.rpc.core.balancer.LoadBalance;
import com.swx.rpc.core.balancer.RandomBalance;
import com.swx.rpc.core.discovery.DiscoveryService;
import com.swx.rpc.core.discovery.NacosDiscoveryService;
import com.swx.rpc.core.discovery.ZookeeperDiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RpcClientProperties.class)
public class RpcClientAutoConfiguration {
    @Autowired
    private RpcClientProperties rpcClientProperties;

    @Bean
    @ConditionalOnMissingBean
    public LoadBalance loadBalance(){
        String balance= rpcClientProperties.getBalance();
        // 默认使用轮询的方法
        if(balance==null)
            return new RandomBalance();
        switch (balance){
            case "randomBalance":{
                return new RandomBalance();
            }
            case "fullRoundBalance":{
                return new FullRoundBalance();
            }
            default:
                return new RandomBalance();
        }
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({RpcClientProperties.class, LoadBalance.class})
    public DiscoveryService discoveryService(@Autowired LoadBalance loadBalance){
        String discoveryAddr= rpcClientProperties.getDisconveryAddr();
        String discoveryType=rpcClientProperties.getDiscoveryType();
        switch (discoveryType){
            case "NACOS":{
                // TODO 缺少动态配置地址的方法
                return new NacosDiscoveryService(loadBalance);
            }
            default:{
                return new ZookeeperDiscoveryService(discoveryAddr,loadBalance);
            }

        }

    }






}
