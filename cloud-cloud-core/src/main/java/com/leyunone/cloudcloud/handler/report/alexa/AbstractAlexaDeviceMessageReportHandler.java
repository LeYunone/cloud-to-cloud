package com.leyunone.cloudcloud.handler.report.alexa;

import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaDeviceProperty;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaDeviceReportBean;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaEndpoint;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaHeader;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;
import com.leyunone.cloudcloud.handler.report.AbstractStatusCommonReportHandler;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/19 17:51
 */
public abstract class AbstractAlexaDeviceMessageReportHandler extends AbstractStatusCommonReportHandler {

    public AbstractAlexaDeviceMessageReportHandler(DeviceReportHandlerFactory factory, RestTemplate restTemplate, ThirdPartyConfigService thirdPartyConfigService) {
        super(factory, restTemplate, thirdPartyConfigService);
    }

    public void handler2(DeviceInfo deviceInfo, ThirdPartyCloudConfigInfo config, DeviceCloudInfo.ThirdMapping thirdMapping) {

    }

    protected AlexaDeviceReportBean buildReport(String deviceId, List<AlexaDeviceProperty> properties, String token) {
        return new AlexaDeviceReportBean(AlexaDeviceReportBean.Event.builder()
                .header(AlexaHeader.builder()
                        .namespace("Alexa")
                        .name("ChangeReport")
                        .messageId(UUID.randomUUID().toString())
                        .payloadVersion("3")
                        .build())
                .endpoint(AlexaEndpoint.builder()
                        .scope(AlexaEndpoint.Scope
                                .builder()
                                .token(token)
                                .type("BearerToken").build())
                        .endpointId(String.valueOf(deviceId))
                        .build())
                // 亚马逊还需要返回属性变更的原因
                .payload(new AlexaDeviceReportBean.Payload(AlexaDeviceReportBean.Change.builder()
                        .cause(AlexaDeviceReportBean.Cause.builder().type("PHYSICAL_INTERACTION").build())
                        .properties(properties).build()))
                .build()
                , AlexaDeviceReportBean.Context.builder()
                .properties(properties)
                .build());
    }
}
