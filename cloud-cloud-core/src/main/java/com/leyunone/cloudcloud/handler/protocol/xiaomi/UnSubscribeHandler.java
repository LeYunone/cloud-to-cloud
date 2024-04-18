package com.leyunone.cloudcloud.handler.protocol.xiaomi;

import com.leyunone.cloudcloud.bean.XiaomiResultCode;
import com.leyunone.cloudcloud.bean.enums.XiaomiIntent;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.third.xiaomi.SubscribeDevice;
import com.leyunone.cloudcloud.bean.third.xiaomi.XiaomiDevice;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author LeYunone
 * @date 2023-12-28 16:16:07
**/
@Service
public class UnSubscribeHandler extends AbstractStrategyXiaomiHandler<SubscribeDevice,SubscribeDevice>{

    private final DeviceRelationManager deviceRelationManager;

    protected UnSubscribeHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, DeviceRelationManager deviceRelationManager) {
        super(factory, deviceManager);
        this.deviceRelationManager = deviceRelationManager;
    }

    @Override
    protected SubscribeDevice action1(SubscribeDevice subscribeDevice, ActionContext context) {
        String userId = context.getAccessTokenInfo().getUser().getUserId();
        List<String> deviceIds = subscribeDevice.getDevices().stream().map(XiaomiDevice::getDid).collect(Collectors.toList());
        List<DeviceCloudInfo> entities = deviceRelationManager.selectByDeviceIds(deviceIds);
        Map<String, DeviceCloudInfo> deviceEntityMap  = CollectionFunctionUtils.mapTo(entities, DeviceCloudInfo::getDeviceId);
        List<DeviceCloudInfo> deviceEntities = subscribeDevice
                .getDevices()
                .stream()
                .map(d -> {
                    String did = d.getDid();
                    DeviceCloudInfo.ThirdMapping thirdMapping = DeviceCloudInfo
                            .ThirdMapping
                            .builder()
                            .thirdId("")
                            .thirdPartyCloud(ThirdPartyCloudEnum.XIAOMI)
                            .userId(userId).build();
                    return DeviceCloudInfo.builder().deviceId(did).mapping(Collections.singletonList(thirdMapping)).build();
                })
                .collect(Collectors.toList());
        deviceRelationManager.updateDeviceMappingByCloudAndUserIdAndDeviceId(deviceEntities);
        List<XiaomiDevice> xiaomiDevices = subscribeDevice.getDevices().stream()
                .peek(d -> {
                    if (!deviceEntityMap.containsKey(d.getDid())) {
                        d.setStatus(XiaomiResultCode.DEVICE_NOT_FOUND.getCode());
                        d.setDescription(XiaomiResultCode.DEVICE_NOT_FOUND.getMessage());
                    } else {
                        d.setStatus(XiaomiResultCode.SUCCESS.getCode());
                    }
                })
                .collect(Collectors.toList());
        subscribeDevice.setDevices(xiaomiDevices);
        return subscribeDevice;
    }

    @Override
    protected String getKey() {
        return XiaomiIntent.UNSUBSCRIBE.name();
    }
}
