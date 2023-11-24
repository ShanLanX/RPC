package com.swx.rpc.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Data

public class RpcClientProperties {
    /**
     * 负载均衡
     */
    private String balance;
    /**
     * 序列化
     */
    private String serialization;
    /**
     * 服务发现
     */
    private String discoveryAddr="127.0.0.1:2181";
    /**
     * 服务发现类型
     */
    private String discoveryType="ZOOKEEPER";
    /**
     * 超时时间
     */
    private Integer timeout;
}
