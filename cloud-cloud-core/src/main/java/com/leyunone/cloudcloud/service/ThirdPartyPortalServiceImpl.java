package com.leyunone.cloudcloud.service;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.action.AbstractCloudCloudHandler;
import com.leyunone.cloudcloud.handler.factory.CloudCloudHandlerFactory;
import org.springframework.stereotype.Service;

/**
 * :)
 *  服务调度入口
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/23
 */
@Service
public class ThirdPartyPortalServiceImpl implements ThirdPartyPortalService {

    private final CloudCloudHandlerFactory cloudCloudHandlerFactory;

    public ThirdPartyPortalServiceImpl(CloudCloudHandlerFactory cloudCloudHandlerFactory) {
        this.cloudCloudHandlerFactory = cloudCloudHandlerFactory;
    }

    @Override
    public String portal(String request, ThirdPartyCloudEnum cloud) {
        AbstractCloudCloudHandler strategy = cloudCloudHandlerFactory.getStrategy(cloud.name(), AbstractCloudCloudHandler.class);
        return strategy.action(request);
    }
}
