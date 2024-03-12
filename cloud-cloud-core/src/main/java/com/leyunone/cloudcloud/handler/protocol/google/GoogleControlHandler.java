package com.leyunone.cloudcloud.handler.protocol.google;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.google.GoogleControlRequest;
import com.leyunone.cloudcloud.bean.google.GoogleControlResponse;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.constant.GoogleActionConstants;
import com.leyunone.cloudcloud.handler.convert.google.GoogleControlConvert;
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
 * @Date 2024/2/28 9:43
 */
@Service
public class GoogleControlHandler extends AbstractStrategyGoogleoHandler<GoogleControlResponse, GoogleControlRequest> {

    private final GoogleControlConvert googleControlConvert;
    private final GoogleStatusConvert googleStatusConvert;
    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected GoogleControlHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, GoogleControlConvert googleControlConvert, GoogleStatusConvert googleStatusConvert, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.googleControlConvert = googleControlConvert;
        this.googleStatusConvert = googleStatusConvert;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }

    @Override
    protected GoogleControlResponse action1(GoogleControlRequest googleControlRequest, ActionContext context) {
        List<DeviceFunctionDTO> commands = googleControlConvert.convert(googleControlRequest);
        List<DeviceInfo> devices = deviceServiceHttpManager.commands(context.getAccessTokenInfo().getUser().getUserId(), commands, context.getThirdPartyCloudConfigInfo());
        Map<String, Map<String,Object>> statusMap = googleStatusConvert.convert(devices);
        /**
         * 所有设备状态都单个返回，不考虑控制时分的组
         */
        List<GoogleControlResponse.Command> responseCommands = devices.stream().map(device -> GoogleControlResponse.Command.builder()
                .ids(CollectionUtil.newArrayList(String.valueOf(device.getDeviceId())))
                .states(statusMap.get(String.valueOf(device.getDeviceId())))
                .status(statusMap.containsKey(String.valueOf(device.getDeviceId())) ? "SUCCESS" : "ERROR")
                .build()).collect(Collectors.toList());

        return new GoogleControlResponse(googleControlRequest.getRequestId(), GoogleControlResponse.Payload.builder().commands(responseCommands).build());
    }

    @Override
    protected String getKey() {
        return GoogleActionConstants.NAMESPACE_CONTROL;
    }
}
