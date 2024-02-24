package com.leyunone.cloudcloud.handler.protocol.baidu;

import com.leyunone.cloudcloud.bean.baidu.BaiduHeader;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.handler.factory.StrategyFactory;
import com.leyunone.cloudcloud.handler.protocol.AbstractStrategyProtocolHandler;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;

import java.util.UUID;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public abstract class AbstractStrategyBaiduHandler <R,P> extends AbstractStrategyProtocolHandler<R,P> {

    protected AbstractStrategyBaiduHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager) {
        super(factory, deviceManager);
    }

    protected BaiduHeader buildHeader(String namespace, String name){
        return BaiduHeader.builder()
                .messageId(UUID.randomUUID().toString())
                .name(name)
                .namespace(namespace)
                .payloadVersion("1")
                .build();
    }
}
