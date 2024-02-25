package com.leyunone.cloudcloud.handler.protocol.alexa;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.alexa.AlexaControlRequest;
import com.leyunone.cloudcloud.bean.alexa.AlexaControlResponse;
import com.leyunone.cloudcloud.bean.alexa.AlexaDeviceProperty;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.enums.ActionTypeEnum;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.convert.alexa.AlexaActionConverter;
import com.leyunone.cloudcloud.handler.convert.alexa.AlexaStatusConverter;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import com.leyunone.cloudcloud.service.ThirdPartyLoadConfigService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/30 18:32
 */
@DependsOn("thirdPartyLoadConfigServiceImpl")
@Service
public class AlexaDeviceControlHandler extends AbstractStrategyAlexaHandler<AlexaControlResponse, AlexaControlRequest> {

    private final ThirdPartyLoadConfigService thirdPartyLoadConfigService;
    private final AlexaActionConverter alexaActionConverter;
    private final AlexaStatusConverter alexaStatusConverter;
    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected AlexaDeviceControlHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, ThirdPartyLoadConfigService thirdPartyLoadConfigService, AlexaActionConverter alexaActionConverter, AlexaStatusConverter alexaStatusConverter, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.thirdPartyLoadConfigService = thirdPartyLoadConfigService;
        this.alexaActionConverter = alexaActionConverter;
        this.alexaStatusConverter = alexaStatusConverter;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }

    /**
     * Alexa实际只支持单控
     *
     * @param alexaControlRequest
     * @param context
     * @return
     */
    @Override
    protected AlexaControlResponse action1(AlexaControlRequest alexaControlRequest, ActionContext context) {
        //转换对方云操作值
        List<DeviceFunctionDTO> convert = alexaActionConverter.convert(alexaControlRequest);
        List<DeviceInfo> commands = deviceServiceHttpManager
                .commands(context.getAccessTokenInfo().getUser().getUserId(), convert, context.getThirdPartyCloudConfigInfo());
        //单控
        List<AlexaDeviceProperty> properties = alexaStatusConverter.convert(CollectionUtil.getFirst(commands));
        return new AlexaControlResponse(AlexaControlResponse.Event.builder()
                .endpoint(alexaControlRequest.getDirective().getEndpoint())
                .header(super.buildHeader(alexaControlRequest.getDirective().getHeader()))
                .payload(AlexaControlResponse.Payload.builder().build())
                .build(),
                AlexaControlResponse.Context.builder()
                        .properties(properties).build());
    }

    @Override
    public List<String> getKeys() {
        return thirdPartyLoadConfigService.getKeys(ThirdPartyCloudEnum.ALEXA, ActionTypeEnum.CONTROL);
    }

}
