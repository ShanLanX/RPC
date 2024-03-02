package com.swx.rpc.core.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcResponse  implements Serializable {
    /**
     * 响应数据
     */
    private Object data;
    /**
     * 响应状态
     */
    private String message;


}
