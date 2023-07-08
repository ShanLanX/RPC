package com.swx.rpc.core.common;

import lombok.Data;

@Data
public class RpcResponse {
    /**
     * 响应数据
     */
    private Object data;
    /**
     * 响应状态
     */
    private String message;


}
