package com.leyunone.cloudcloud.handler.protocol.xiaomi;

import com.leyunone.cloudcloud.bean.XiaomiResultCode;
import com.leyunone.cloudcloud.bean.enums.XiaomiIntent;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.third.xiaomi.GetProperties;
import com.leyunone.cloudcloud.bean.third.xiaomi.XiaomiProperties;
import com.leyunone.cloudcloud.handler.convert.xiaomi.XiaomiStatusConverter;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LeYunone
 * @date 2023-12-26 13:59:36
 **/
@Service
@Slf4j
public class GetPropertiesHandler extends AbstractStrategyXiaomiHandler<GetProperties, GetProperties> {

    private final XiaomiStatusConverter xiaomiStatusConverter;
    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected GetPropertiesHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, XiaomiStatusConverter xiaomiStatusConverter, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.xiaomiStatusConverter = xiaomiStatusConverter;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }


    @Override
    protected GetProperties action1(GetProperties getProperties, ActionContext context) {
        List<String> deviceIds = getProperties.getProperties()
                .stream()
                .map(XiaomiProperties::getDid)
                .distinct()
                .collect(Collectors.toList());
        List<DeviceInfo> devicesStatus = deviceServiceHttpManager.getDevicesStatusByDeviceIds(context, deviceIds);
        List<XiaomiProperties> statusProperties = xiaomiStatusConverter.convert(devicesStatus);
        Map<String, Map<String, XiaomiProperties>> groupStatusByDeviceId = statusProperties
                .stream()
                .collect(Collectors.groupingBy(XiaomiProperties::getDid, Collectors.toMap(k -> k.getSiid() + "_" + k.getPiid(), v -> v)));
        List<XiaomiProperties> xiaomiProperties = getProperties
                .getProperties()
                .stream()
                .map(p -> {
                    Map<String, XiaomiProperties> xp = groupStatusByDeviceId.get(p.getDid());
                    if (null == xp) {
                        p.setStatus(XiaomiResultCode.DEVICE_NOT_FOUND.getCode());
                        p.setDescription(XiaomiResultCode.DEVICE_NOT_FOUND.getMessage());
                        return p;
                    }
                    XiaomiProperties properties = xp.get(p.getSiid() + "_" + p.getPiid());
                    if (null == properties) {
                        p.setStatus(XiaomiResultCode.PROPERTY_NOT_FOUND.getCode());
                        p.setDescription(XiaomiResultCode.PROPERTY_NOT_FOUND.getMessage());
                        return p;
                    }
                    p.setValue(properties.getValue());
                    p.setStatus(XiaomiResultCode.SUCCESS.getCode());
                    return p;
                })
                .collect(Collectors.toList());
        getProperties.setProperties(xiaomiProperties);
        return getProperties;
    }

    @Override
    protected String getKey() {
        return XiaomiIntent.GET_PROPERTIES.name();
    }


}
