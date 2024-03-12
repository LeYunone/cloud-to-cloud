package com.leyunone.cloudcloud.handler.protocol.google;

import com.leyunone.cloudcloud.bean.google.GoogleQueryRequest;
import com.leyunone.cloudcloud.bean.google.GoogleQueryResponse;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.constant.GoogleActionConstants;
import com.leyunone.cloudcloud.handler.convert.google.GoogleStatusConvert;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/27 18:02
 */
@Service
public class GoogleQueryHandler extends AbstractStrategyGoogleoHandler<GoogleQueryResponse, GoogleQueryRequest> {

    private final GoogleStatusConvert googleStatusConvert;
    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected GoogleQueryHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, GoogleStatusConvert googleStatusConvert, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.googleStatusConvert = googleStatusConvert;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }


    @Override
    protected GoogleQueryResponse action1(GoogleQueryRequest googleQueryRequest, ActionContext context) {
        List<DeviceInfo> devices = deviceServiceHttpManager.getDevicesStatusByDeviceIds(context.getAccessTokenInfo().getUser().getUserId()
                , googleQueryRequest.getInputs().get(0).getPayload().getDevices().stream().map(t -> Long.parseLong(t.getId()))
                        .collect(Collectors.toList()), context.getThirdPartyCloudConfigInfo());
        Map<String, Map<String,Object>> status = googleStatusConvert.convert(devices);
        status.keySet().forEach(key->{
            Map<String,Object> deviceStatus = status.get(key);
            deviceStatus.put("status","SUCCESS");
        });
        return new GoogleQueryResponse(googleQueryRequest.getRequestId(), GoogleQueryResponse.Payload.builder().devices(status).build());
    }

    @Override
    protected String getKey() {
        return GoogleActionConstants.NAMESPACE_QUERY;
    }
}
