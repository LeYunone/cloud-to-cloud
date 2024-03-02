package com.leyunone.cloudcloud.handler.protocol.google;

import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.handler.protocol.AbstractStrategyProtocolHandler;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/23 10:06
 */
public abstract class AbstractStrategyGoogleoHandler<R,P> extends AbstractStrategyProtocolHandler<R,P> {

    protected AbstractStrategyGoogleoHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager) {
        super(factory, deviceManager);
    }

}
