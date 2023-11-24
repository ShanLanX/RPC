package com.swx.rpc.server.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 暴露服务的注解
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface RpcService {
    /**
     * 暴露服务接口的类型
     */

    Class<?> interfaceType() default Object.class;

    String version() default "1.0";


}
