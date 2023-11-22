package com.swx.rpc.client.transport;

import com.swx.rpc.core.common.RpcResponse;
import com.swx.rpc.core.protocol.MessageProtocol;

public interface NetClientTransport {
    MessageProtocol<RpcResponse> sendRequest(RequestMetadata metadata) throws Exception;

}
