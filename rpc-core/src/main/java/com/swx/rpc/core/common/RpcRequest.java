package com.swx.rpc.core.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcRequest implements Serializable {
    /**
     * 请求的服务名称 名称+版本号
     */
    private String serviceKey;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 请求的参数的值
     */
    private Object[] parameters;

}
