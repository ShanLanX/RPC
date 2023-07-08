package com.swx.rpc.server.store;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存服务 减少开销
 */
public class LocalServerCache {
    // TODO 可以使用redis来缓存
    private static final Map<String, Object> serverCacheMap = new HashMap<>();

    public static void store(String serverName, Object server) {
        serverCacheMap.merge(serverName, server, (Object oldObj, Object newObj) -> newObj);
    }

    public static Object get(String serverName) {
        return serverCacheMap.get(serverName);
    }

    public static Map<String, Object> getAll(){
        return null;
    }
}
