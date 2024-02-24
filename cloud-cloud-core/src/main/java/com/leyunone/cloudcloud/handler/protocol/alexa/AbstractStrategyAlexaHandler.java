package com.leyunone.cloudcloud.handler.protocol.alexa;

import com.leyunone.cloudcloud.bean.alexa.AlexaHeader;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.handler.protocol.AbstractStrategyProtocolHandler;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/30 17:13
 */
public abstract class AbstractStrategyAlexaHandler<P, R> extends AbstractStrategyProtocolHandler<P, R> {

    protected AbstractStrategyAlexaHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager) {
        super(factory, deviceManager);
    }

    public AlexaHeader buildHeader(AlexaHeader header) {
        return AlexaHeader.builder()
                .namespace("Alexa")
                .name("Response")
                .messageId(header.getMessageId())
                .payloadVersion(header.getPayloadVersion())
                .build();
    }
}
