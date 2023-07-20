package com.swx.rpc.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
// 从配置文件中读取属性
@ConfigurationProperties(prefix = "rpc.server")
public class RpcServerProperties {
    /**
     * 服务启动端口号
     */

    private Integer port=8090;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 注册中心类型
     */
    private String registryType="ZOOKEEPER";

    /**
     * 注册中心地址
     */
    private String registryAddr="127.0.0.1:2181";






}
