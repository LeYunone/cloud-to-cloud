package com.leyunone.cloudcloud.handler.report;

import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;
import com.leyunone.cloudcloud.strategy.AbstractStrategyAutoRegisterComponent;
import org.springframework.web.client.RestTemplate;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public abstract class AbstractDeviceMessageReportHandler extends AbstractStrategyAutoRegisterComponent implements DeviceMessageReportHandler {

    protected RestTemplate restTemplate;

    public AbstractDeviceMessageReportHandler(DeviceReportHandlerFactory factory,RestTemplate restTemplate) {
        super(factory);
    }

    @Override
    public void handler(String message, DeviceCloudInfo.ThirdMapping thirdMapping) {
        //冷缺
        if(cooling(message)){
            handler1(message,thirdMapping);
        }
    }

    /**
     * 实际处理
     * @param message
     * @param thirdMapping
     */
    public abstract void handler1(String message,DeviceCloudInfo.ThirdMapping thirdMapping);

    protected boolean cooling(String message){
        return Boolean.TRUE;
    }

    /**
     * third cloud
     * @return third cloud
     */
    public abstract ThirdPartyCloudEnum getCloud();

}
