package com.leyunone.cloudcloud.handler.report;


import com.leyunone.cloudcloud.handler.factory.DeviceSyncHandlerFactory;
import com.leyunone.cloudcloud.strategy.AbstractStrategyAutoRegisterComponent;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/15 15:58
 */
public abstract class AbstractSyncInfoReportHandler extends AbstractStrategyAutoRegisterComponent implements DeviceSyncHandler{
    
    protected AbstractSyncInfoReportHandler(DeviceSyncHandlerFactory factory) {
        super(factory);
    }
}
