package com.swx.rpc.core.balancer;

import com.swx.rpc.core.common.ServiceInfo;

import java.util.List;
import java.util.Random;

public class RandomBalance implements LoadBalance{
    @Override
    public ServiceInfo chooseService(List<ServiceInfo> services) {
        Random random=new Random();
        int choose=random.nextInt(services.size());
        return services.get(choose);
    }
}
