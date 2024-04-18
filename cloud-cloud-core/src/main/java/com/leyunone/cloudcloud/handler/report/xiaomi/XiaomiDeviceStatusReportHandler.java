package com.leyunone.cloudcloud.handler.report.xiaomi;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.enums.ReportTypeEnum;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author LeYunone
 * @date 2023-12-28 19:36:29
 **/
@Service
public class XiaomiDeviceStatusReportHandler extends AbstractXiaomiDeviceMessageReportHandler {


    public XiaomiDeviceStatusReportHandler(DeviceReportHandlerFactory factory, RestTemplate restTemplate, ThirdPartyConfigService thirdPartyConfigService) {
        super(factory, restTemplate, thirdPartyConfigService);
    }

    @Override
    protected String getDeviceId(String message) {
        return JSONObject.parseObject(message).getString("deviceId");
    }

    @Override
    protected String getTopic() {
        return "device-status-changed";
    }

    @Override
    public ThirdPartyCloudEnum getCloud() {
        return ThirdPartyCloudEnum.XIAOMI;
    }

    @Override
    public ReportTypeEnum type() {
        return ReportTypeEnum.STATUS;
    }
}
