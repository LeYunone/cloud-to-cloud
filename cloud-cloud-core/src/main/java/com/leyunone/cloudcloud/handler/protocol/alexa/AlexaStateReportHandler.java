package com.leyunone.cloudcloud.handler.protocol.alexa;

import com.leyunone.cloudcloud.bean.third.alexa.*;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.constant.AlexaActionConstants;
import com.leyunone.cloudcloud.handler.convert.alexa.AlexaStatusConverter;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/19 9:22
 */
@Service
public class AlexaStateReportHandler extends AbstractStrategyAlexaHandler<AlexaStateReportResponse, AlexaStateReportRequest> {

    private final AlexaStatusConverter alexaStatusConverter;
    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected AlexaStateReportHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, AlexaStatusConverter alexaStatusConverter, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.alexaStatusConverter = alexaStatusConverter;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }


    @Override
    protected AlexaStateReportResponse action1(AlexaStateReportRequest alexaStateReportRequest, ActionContext context) {
        String deviceId = alexaStateReportRequest.getDirective().getEndpoint().getEndpointId();
        DeviceInfo deviceInfo = deviceServiceHttpManager.getDeviceStatusByDeviceId(context.getAccessTokenInfo().getUser().getUserId(),
                deviceId, context.getThirdPartyCloudConfigInfo());
        List<AlexaDeviceProperty> properties = alexaStatusConverter.convert(deviceInfo);
        return new AlexaStateReportResponse(AlexaStateReportResponse.Event.builder()
                .header(
                        AlexaHeader.builder()
                                .namespace("Alexa")
                                .name("StateReport")
                                .messageId(alexaStateReportRequest.getDirective().getHeader().getMessageId())
                                .correlationToken(alexaStateReportRequest.getDirective().getHeader().getCorrelationToken())
                                .payloadVersion(alexaStateReportRequest.getDirective().getHeader().getPayloadVersion())
                                .build()
                )
                .endpoint(AlexaEndpoint.builder()
                        .scope(alexaStateReportRequest.getDirective().getEndpoint().getScope())
                        .endpointId(alexaStateReportRequest.getDirective().getEndpoint().getEndpointId())
                        .build()
                ).build()
                , AlexaStateReportResponse.Context.builder()
                .properties(properties)
                .build());
    }

    @Override
    public String getKey() {
        return AlexaActionConstants.NAMESPACE_STATE_REPORT;
    }
}
