package com.swx.core.common;

public class ServiceUtil {
    /**
     * 获取服务的key
     */
    public static String serviceKey(String serviceName,String version){
        return String.join("-",serviceName,version);
    }
}
