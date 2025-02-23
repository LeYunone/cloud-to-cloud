package com.leyunone.cloudcloud.handler.protocol.xiaomi;

import com.leyunone.cloudcloud.bean.XiaomiResultCode;
import com.leyunone.cloudcloud.bean.enums.XiaomiIntent;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.third.xiaomi.GetDeviceStatus;
import com.leyunone.cloudcloud.bean.third.xiaomi.XiaomiDevice;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LeYunone
 * @date 2023-12-28 16:56:45
 **/
@Service
public class GetDeviceStatusHandler extends AbstractStrategyXiaomiHandler<GetDeviceStatus.Response, GetDeviceStatus.Request> {

    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected GetDeviceStatusHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }

    @Override
    protected GetDeviceStatus.Response action1(GetDeviceStatus.Request getDeviceStatus, ActionContext context) {
        String userId = context.getAccessTokenInfo().getUser().getUserId();
        List<DeviceInfo> deviceShadowModels = deviceServiceHttpManager.getDevicesStatusByDeviceIds(context, getDeviceStatus.getDevices());
        Map<String, DeviceInfo> deviceShadowModelMap = CollectionFunctionUtils.mapTo(deviceShadowModels, DeviceInfo::getDeviceId);
        List<XiaomiDevice> xiaomiDevices = getDeviceStatus.getDevices()
                .stream()
                .map(d -> {
                    XiaomiDevice xiaomiDevice = new XiaomiDevice();
                    xiaomiDevice.setDid(d.toString());
                    DeviceInfo deviceShadowModel = deviceShadowModelMap.get(d);
                    if (null == deviceShadowModel) {
                        xiaomiDevice.setStatus(XiaomiResultCode.DEVICE_NOT_FOUND.getCode());
                        xiaomiDevice.setDescription(XiaomiResultCode.DEVICE_NOT_FOUND.getMessage());
                    } else {
                        xiaomiDevice.setName(deviceShadowModel.getDeviceName());
                        xiaomiDevice.setOnline(deviceShadowModel.isOnline());
                    }
                    return xiaomiDevice;
                })
                .collect(Collectors.toList());
        GetDeviceStatus.Response response = new GetDeviceStatus.Response();
        response.setRequestId(getDeviceStatus.getRequestId());
        response.setIntent(getDeviceStatus.getIntent());
        response.setDevices(xiaomiDevices);
        return response;
    }

    @Override
    protected String getKey() {
        return XiaomiIntent.GET_DEVICE_STATUS.name();
    }

}
