package com.leyunone.cloudcloud.handler.protocol.tmall;

import com.leyunone.cloudcloud.bean.third.tmall.TmallHeader;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.handler.protocol.AbstractStrategyProtocolHandler;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/16 16:47
 */
public abstract class AbstractStrategyTmallHandler<R, P> extends AbstractStrategyProtocolHandler<R, P> {

    protected AbstractStrategyTmallHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager) {
        super(factory, deviceManager);
    }

    protected TmallHeader buildHeader(TmallHeader header, String name) {
        return TmallHeader.builder()
                .messageId(header.getMessageId())
                .name(name)
                .namespace(header.getNamespace())
                .payLoadVersion(header.getPayLoadVersion())
                .build();
    }
}
