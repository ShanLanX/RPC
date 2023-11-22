package com.swx.rpc.core.common;

public class ServiceUtil {
    /**
     * 获取服务的key 服务的名称（类名）+版本号
     */
    public static String serviceKey(String serviceName,String version){
        return String.join("-",serviceName,version);
    }
}
