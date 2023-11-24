package com.swx.rpc.client.processor;


import com.swx.rpc.client.annotation.RpcAutowired;
import com.swx.rpc.client.config.RpcClientProperties;
import com.swx.rpc.client.proxy.ClientStubProxyFactory;
import com.swx.rpc.core.discovery.DiscoveryService;
import org.codehaus.jackson.map.util.ClassUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class RpcClientProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {
    private ClientStubProxyFactory clientStubProxyFactory;
    private DiscoveryService discoveryService;
    private RpcClientProperties rpcClientProperties;
    private ApplicationContext applicationContext;
    public RpcClientProcessor(ClientStubProxyFactory clientStubProxyFactory,DiscoveryService discoveryService,RpcClientProperties rpcClientProperties){
        this.clientStubProxyFactory=clientStubProxyFactory;
        this.discoveryService=discoveryService;
        this.rpcClientProperties=rpcClientProperties;

    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        for(String beanName:configurableListableBeanFactory.getBeanDefinitionNames()){
            BeanDefinition beanDefinition=configurableListableBeanFactory.getBeanDefinition(beanName);
            String beanClassName=beanDefinition.getBeanClassName();
            if(beanClassName!=null){
                Class<?> clazz= ClassUtils.resolveClassName(beanClassName,this.getClass().getClassLoader());
                ReflectionUtils.doWithFields(clazz,field -> {
                    RpcAutowired rpcAutowired= AnnotationUtils.getAnnotation(field,RpcAutowired.class);
                if(rpcAutowired!=null){
                    Object bean=applicationContext.getBean(clazz);
                    field.setAccessible(true);
                    //  修改为代理对象
                    ReflectionUtils.setField(field,bean,clientStubProxyFactory.getProxy(field.getType(), rpcAutowired.version(), discoveryService,rpcClientProperties));

                } }

                );

            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;

    }
}
