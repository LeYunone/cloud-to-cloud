package com.leyunone.cloudcloud.handler.protocol.xiaomi;

import com.leyunone.cloudcloud.bean.XiaomiResultCode;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.enums.XiaomiIntent;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.third.xiaomi.SetProperties;
import com.leyunone.cloudcloud.bean.third.xiaomi.XiaomiProperties;
import com.leyunone.cloudcloud.handler.convert.xiaomi.XiaomiControlConverter;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LeYunone
 * @date 2023-12-27 17:09:30
 **/
@Service
public class SetPropertiesHandler extends AbstractStrategyXiaomiHandler<SetProperties, SetProperties> {

    private final XiaomiControlConverter xiaomiControlConverter;
    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected SetPropertiesHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, XiaomiControlConverter xiaomiControlConverter, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.xiaomiControlConverter = xiaomiControlConverter;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }


    @Override
    protected SetProperties action1(SetProperties setProperties, ActionContext context) {
        List<DeviceFunctionDTO> functionCodeCommands = xiaomiControlConverter.convert(setProperties.getProperties());
        deviceServiceHttpManager.commands(context.getAccessTokenInfo().getUser().getUserId(), functionCodeCommands, context.getThirdPartyCloudConfigInfo());
        List<XiaomiProperties> properties = setProperties.getProperties().stream().peek(sp -> sp.setStatus(XiaomiResultCode.SUCCESS.getCode())).collect(Collectors.toList());
        setProperties.setProperties(properties);
        return setProperties;
    }

    @Override
    protected String getKey() {
        return XiaomiIntent.SET_PROPERTIES.name();
    }

}
