package com.leyunone.cloudcloud.handler.protocol.alexa;

import com.leyunone.cloudcloud.bean.alexa.AlexaDeviceProperty;
import com.leyunone.cloudcloud.bean.alexa.AlexaEndpoint;
import com.leyunone.cloudcloud.bean.alexa.AlexaStateReportRequest;
import com.leyunone.cloudcloud.bean.alexa.AlexaStateReportResponse;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
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
public class AlexaStateReportHandler extends AbstractStrategyAlexaHandler<AlexaStateReportResponse,AlexaStateReportRequest > {

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
                Long.parseLong(deviceId), context.getThirdPartyCloudConfigInfo());
        List<AlexaDeviceProperty> properties = alexaStatusConverter.convert(deviceInfo);
        return new AlexaStateReportResponse(AlexaStateReportResponse.Event.builder()
                .header(alexaStateReportRequest.getDirective().getHeader())
                .endpoint(AlexaEndpoint.builder().endpointId(deviceId).build())
                .build()
                , AlexaStateReportResponse.Context.builder()
                .properties(properties)
                .build());
    }
}
