package com.leyunone.cloudcloud.handler.report.xiaomi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.third.xiaomi.DeviceChanged;
import com.leyunone.cloudcloud.bean.third.xiaomi.XiaomiDevice;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;
import com.leyunone.cloudcloud.handler.report.AbstractDeviceMessageReportHandler;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

/**
 * @author LeYunone
 * @date 2023-12-28 19:48:33
 **/
@Slf4j
public abstract class AbstractXiaomiDeviceMessageReportHandler extends AbstractDeviceMessageReportHandler {


    public AbstractXiaomiDeviceMessageReportHandler(DeviceReportHandlerFactory factory, RestTemplate restTemplate, ThirdPartyConfigService thirdPartyConfigService) {
        super(factory, restTemplate, thirdPartyConfigService);
    }


    @Override
    public void handler1(String message, DeviceCloudInfo.ThirdMapping thirdMapping) {
        String clientId = thirdMapping.getClientId();
        ThirdPartyCloudConfigInfo config = thirdPartyConfigService.getConfig(clientId);
        String reportUrl = config.getReportUrl();
        XiaomiDevice device = new XiaomiDevice();
        device.setDid(getDeviceId(message));
        device.setSubscriptionId(thirdMapping.getThirdId());
        DeviceChanged deviceChanged = new DeviceChanged();
        deviceChanged.setRequestId(UUID.randomUUID().toString());
        deviceChanged.setTopic(getTopic());
        deviceChanged.setDevices(Collections.singletonList(device));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        JSONObject params = JSON.parseObject(JSONObject.toJSONString(deviceChanged));
        HttpEntity<JSONObject> entity = new HttpEntity<>(params, httpHeaders);
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(reportUrl, HttpMethod.POST, entity, String.class);
            log.debug("report xiaomi iot cloud,result {}", JSONObject.toJSONString(exchange));
        } catch (Exception e) {
            log.error("report xiaomi iot cloud fail,exception {}", e.getMessage());
        }
    }

    protected abstract String getDeviceId(String message);

    protected abstract String getTopic();
}
