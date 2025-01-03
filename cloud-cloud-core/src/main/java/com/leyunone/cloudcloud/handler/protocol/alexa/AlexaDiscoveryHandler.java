package com.leyunone.cloudcloud.handler.protocol.alexa;

import com.leyunone.cloudcloud.bean.third.alexa.AlexaDevice;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaDiscoveryRequest;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaDiscoveryResponse;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaHeader;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.constant.AlexaActionConstants;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.convert.alexa.AlexaDeviceInfoConverter;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * :)
 * 发现设备动作
 *
 * @Author LeYunone
 * @Date 2024/1/31 10:53
 */
@Service
public class AlexaDiscoveryHandler extends AbstractStrategyAlexaHandler<AlexaDiscoveryResponse, AlexaDiscoveryRequest> {

    private final AlexaDeviceInfoConverter alexaDeviceInfoConverter;
    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected AlexaDiscoveryHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, AlexaDeviceInfoConverter alexaDeviceInfoConverter, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.alexaDeviceInfoConverter = alexaDeviceInfoConverter;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }


    @Override
    protected AlexaDiscoveryResponse action1(AlexaDiscoveryRequest alexaDiscoveryRequest, ActionContext context) {
        List<DeviceInfo> deviceInfos = deviceServiceHttpManager
                .getDeviceListByUserId(context.getAccessTokenInfo().getUser().getUserId(), context.getThirdPartyCloudConfigInfo());
        super.doRelationStore(deviceInfos,
                context.getAccessTokenInfo().getUser().getUserId(),
                context.getThirdPartyCloudConfigInfo().getClientId(), ThirdPartyCloudEnum.ALEXA, (thirdMapping -> {
                }));
        List<AlexaDevice> devices = alexaDeviceInfoConverter.convert(deviceInfos);
        AlexaDiscoveryResponse.Payload payload = new AlexaDiscoveryResponse.Payload();
        payload.setEndpoints(devices);
        return new AlexaDiscoveryResponse(AlexaDiscoveryResponse.Event.builder()
                .header(this.buildHeader(alexaDiscoveryRequest.getDirective().getHeader()))
                .payload(payload)
                .build());
    }

    @Override
    public AlexaHeader buildHeader(AlexaHeader header) {
        return AlexaHeader.builder()
                .messageId(header.getMessageId())
                .payloadVersion(header.getPayloadVersion())
                .name("Discover.Response")
                .namespace(header.getNamespace())
                .build();
    }

    @Override
    public String getKey() {
        return AlexaActionConstants.NAMESPACE_DISCOVERY;
    }
}
