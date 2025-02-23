package com.leyunone.cloudcloud.handler.protocol.xiaomi;

import com.leyunone.cloudcloud.bean.enums.XiaomiIntent;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.third.xiaomi.GetDevices;
import com.leyunone.cloudcloud.bean.third.xiaomi.XiaomiDevice;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.convert.xiaomi.XiaomiDeviceInfoConverter;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LeYunone
 * @date 2023-12-25 15:51:33
 **/
@Service
public class GetDevicesHandler extends AbstractStrategyXiaomiHandler<GetDevices, GetDevices> {

    private final XiaomiDeviceInfoConverter xiaomiDeviceInfoConverter;
    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected GetDevicesHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, XiaomiDeviceInfoConverter xiaomiDeviceInfoConverter, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.xiaomiDeviceInfoConverter = xiaomiDeviceInfoConverter;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }


    @Override
    protected GetDevices action1(GetDevices getDevices, ActionContext context) {
        String userId = context.getAccessTokenInfo().getUser().getUserId();
        List<DeviceInfo> deviceShadowModels = deviceServiceHttpManager.getDeviceListByUserId(context);
        super.doRelationStore(deviceShadowModels, userId, context.getAccessTokenInfo().getClientId(), ThirdPartyCloudEnum.XIAOMI, (m) -> {

        });
        List<XiaomiDevice> xiaomiDevices = xiaomiDeviceInfoConverter.convert(deviceShadowModels);
        //赋值
        getDevices.setDevices(xiaomiDevices);
        return getDevices;
    }

    @Override
    protected String getKey() {
        return XiaomiIntent.GET_DEVICES.name();
    }
}
