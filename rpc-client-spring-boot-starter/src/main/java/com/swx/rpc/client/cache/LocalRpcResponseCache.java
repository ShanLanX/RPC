package com.swx.rpc.client.cache;

import com.swx.rpc.client.transport.RpcFuture;
import com.swx.rpc.core.common.RpcResponse;
import com.swx.rpc.core.protocol.MessageProtocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class LocalRpcResponseCache {
    private static Map<String, RpcFuture<MessageProtocol<RpcResponse>>> requestResponseCache = new ConcurrentHashMap<>();

    /**
     * 请求和响应的映射关系，用于处理异步请求
     * @param requestId
     * @param response
     */
    public static void add(String requestId,RpcFuture<MessageProtocol<RpcResponse>> response){
        requestResponseCache.put(requestId,response);
    }
    /**
     *
     */
    public static  void fillResponse(String requestId,MessageProtocol<RpcResponse> response){
        RpcFuture<MessageProtocol<RpcResponse>> future = requestResponseCache.get(requestId);
        // 设置响应
        future.setResponse(response);
        // 响应处理完毕 移除缓存
        requestResponseCache.remove(requestId);


    }







}
