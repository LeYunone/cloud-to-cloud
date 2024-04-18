package com.leyunone.cloudcloud.handler.report.google;

import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.dao.ThirdPartyClientRepository;
import com.leyunone.cloudcloud.dao.entity.ThirdPartyClientDO;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;
import com.leyunone.cloudcloud.handler.report.AbstractStatusCommonReportHandler;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import org.springframework.web.client.RestTemplate;


/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/19 17:51
 */
public abstract class AbstractGoogleDeviceMessageReportHandler extends AbstractStatusCommonReportHandler {


    public AbstractGoogleDeviceMessageReportHandler(DeviceReportHandlerFactory factory, RestTemplate restTemplate, ThirdPartyConfigService thirdPartyConfigService) {
        super(factory, restTemplate, thirdPartyConfigService);
    }

    @Override
    public void handler2(DeviceInfo deviceInfo, ThirdPartyCloudConfigInfo config, DeviceCloudInfo.ThirdMapping thirdMapping) {

    }
}
