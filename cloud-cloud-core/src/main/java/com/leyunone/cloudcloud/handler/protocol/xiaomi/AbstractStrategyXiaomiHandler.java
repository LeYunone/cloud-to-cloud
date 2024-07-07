package com.leyunone.cloudcloud.handler.protocol.xiaomi;

import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.handler.protocol.AbstractStrategyProtocolHandler;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;

/**
 * @author LeYunone
 * @date 2023-12-13 14:19:08
 **/
public abstract class AbstractStrategyXiaomiHandler<R, P> extends AbstractStrategyProtocolHandler<R, P> {

    protected AbstractStrategyXiaomiHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager) {
        super(factory, deviceManager);
    }

}
