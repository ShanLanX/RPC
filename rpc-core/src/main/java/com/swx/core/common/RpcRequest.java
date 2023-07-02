package com.swx.core.common;

import lombok.Data;

@Data
public class RpcRequest {
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
