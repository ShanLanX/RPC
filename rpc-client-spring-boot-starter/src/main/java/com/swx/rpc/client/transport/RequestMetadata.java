package com.swx.rpc.client.transport;

import com.swx.rpc.core.common.RpcRequest;
import com.swx.rpc.core.protocol.MessageProtocol;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Data
@Builder
public class RequestMetadata implements Serializable {
    /**
     * 协议
     */
    private MessageProtocol<RpcRequest> protocol;
    /**
     * 地址
     */
    private String address;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 超时时间
     */
    private Integer timeout;
}
