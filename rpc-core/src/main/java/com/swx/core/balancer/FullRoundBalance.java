package com.swx.core.balancer;

import com.swx.core.common.ServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class FullRoundBalance implements LoadBalance{
    private static Logger logger= LoggerFactory.getLogger(FullRoundBalance.class);
    //TODO 是否要将其修改为静态变量
    private int index;
    @Override
    public ServiceInfo chooseService(List<ServiceInfo> services) {
        if (index >= services.size()) {
            index = 0;
        }

        logger.info("选择第"+index+"号服务");

        return services.get(index++) ;
    }
}
