package com.leyunone.cloudcloud.handler.protocol.google;

import com.leyunone.cloudcloud.bean.google.GoogleDevice;
import com.leyunone.cloudcloud.bean.google.GoogleDiscoveryResponse;
import com.leyunone.cloudcloud.bean.google.GoogleStandardRequest;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.constant.GoogleActionConstants;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.convert.google.GoogleDeviceInfoConvert;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/23 10:36
 */
@Service
public class GoogleDiscoveryHandler extends AbstractStrategyGoogleoHandler<GoogleDiscoveryResponse,GoogleStandardRequest > {

    private final GoogleDeviceInfoConvert googleDeviceInfoConvert;
    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected GoogleDiscoveryHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, GoogleDeviceInfoConvert googleDeviceInfoConvert, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.googleDeviceInfoConvert = googleDeviceInfoConvert;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }

    @Override
    protected GoogleDiscoveryResponse action1(GoogleStandardRequest googleStandardRequest, ActionContext context) {
        List<DeviceInfo> deviceInfos = deviceServiceHttpManager.getDeviceListByUserId(context.getAccessTokenInfo().getUser().getUserId(),
                context.getThirdPartyCloudConfigInfo());
        List<GoogleDevice> convert = googleDeviceInfoConvert.convert(deviceInfos);
        super.doRelationStore(deviceInfos,
                context.getAccessTokenInfo().getUser().getUserId(),
                context.getThirdPartyCloudConfigInfo().getClientId(), ThirdPartyCloudEnum.GOOGLE, (thirdMapping -> {
                }));
        return new GoogleDiscoveryResponse(googleStandardRequest.getRequestId(),
                GoogleDiscoveryResponse.Payload.builder()
                        .agentUserId(String.valueOf(context.getAccessTokenInfo().getUser().getUserId()))
                        .devices(convert)
                        .build());
    }

    @Override
    protected String getKey() {
        return GoogleActionConstants.NAMESPACE_DISCOVERY;
    }
}
