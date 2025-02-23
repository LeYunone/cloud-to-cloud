package com.leyunone.cloudcloud.handler.report.google;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.homegraph.v1.HomeGraphService;
import com.google.api.services.homegraph.v1.model.ReportStateAndNotificationDevice;
import com.google.api.services.homegraph.v1.model.ReportStateAndNotificationRequest;
import com.google.api.services.homegraph.v1.model.ReportStateAndNotificationResponse;
import com.google.api.services.homegraph.v1.model.StateAndNotificationPayload;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.leyunone.cloudcloud.bean.enums.ReportTypeEnum;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.convert.google.GoogleStatusConvert;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/28 18:02
 */
@Service
public class GoogleDeviceStatusReportHandler extends AbstractGoogleDeviceMessageReportHandler {


    private static GoogleCredentials credentials;
    private static HomeGraphService homeGraphService;

    static {
        /**
         * 先拿到Google-api的访问token 
         */
        try {
            GoogleDeviceStatusReportHandler.credentials = GoogleCredentials.getApplicationDefault()
                    .createScoped(CollectionUtil.newArrayList("https://www.googleapis.com/auth/homegraph"));
            // 创建面向Google-homegraph的api令牌
            GoogleDeviceStatusReportHandler.homeGraphService =
                    new HomeGraphService.Builder(
                            GoogleNetHttpTransport.newTrustedTransport(),
                            GsonFactory.getDefaultInstance(),
                            new HttpCredentialsAdapter(GoogleDeviceStatusReportHandler.credentials))
                            .setApplicationName("HomeGraphExample/1.0")
                            .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final GoogleStatusConvert googleStatusConvert;
    private final Logger logger = LoggerFactory.getLogger(GoogleDeviceStatusReportHandler.class);

    public GoogleDeviceStatusReportHandler(DeviceReportHandlerFactory factory, RestTemplate restTemplate, ThirdPartyConfigService thirdPartyConfigService, GoogleStatusConvert googleStatusConvert) {
        super(factory, restTemplate, thirdPartyConfigService);
        this.googleStatusConvert = googleStatusConvert;
    }


    @Override
    public void handler2(DeviceInfo deviceInfo, ThirdPartyCloudConfigInfo config, DeviceCloudInfo.ThirdMapping thirdMapping) {
        if (ObjectUtil.isNull(GoogleDeviceStatusReportHandler.credentials) || ObjectUtil.isNull(GoogleDeviceStatusReportHandler.homeGraphService))
            return;
        try {
            Map<String, Object> states = (Map) googleStatusConvert.convert(CollectionUtil.newArrayList(deviceInfo));

            // 构建请求体
            ReportStateAndNotificationRequest request =
                    new ReportStateAndNotificationRequest()
                            .setRequestId(UUID.randomUUID().toString())
                            .setAgentUserId(String.valueOf(thirdMapping.getUserId()))
                            .setPayload(
                                    new StateAndNotificationPayload()
                                            .setDevices(
                                                    new ReportStateAndNotificationDevice()
                                                            .setStates(states)));
            Object o = JSONObject.toJSONString(request);
            HomeGraphService.Devices.ReportStateAndNotification reportStateAndNotification = GoogleDeviceStatusReportHandler.homeGraphService.devices().reportStateAndNotification(request);
            ReportStateAndNotificationResponse execute = reportStateAndNotification.execute();
            logger.debug("google device online report response:{}", execute);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ThirdPartyCloudEnum getCloud() {
        return ThirdPartyCloudEnum.GOOGLE;
    }


    @Override
    public ReportTypeEnum type() {
        return ReportTypeEnum.STATUS;
    }

}
