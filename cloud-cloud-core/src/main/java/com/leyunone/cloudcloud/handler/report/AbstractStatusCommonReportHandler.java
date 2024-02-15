package com.leyunone.cloudcloud.handler.report;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.dto.DeviceMessageDTO;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.dao.ThirdPartyClientRepository;
import com.leyunone.cloudcloud.dao.entity.ThirdPartyClientDO;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * :)
 * 上报公共处理器
 *
 * @Author leyunone
 * @Date 2024/2/3 11:29
 */
public abstract class AbstractStatusCommonReportHandler extends AbstractDeviceMessageReportHandler {

    private ThirdPartyClientRepository thirdPartyClientRepository;

    public AbstractStatusCommonReportHandler(DeviceReportHandlerFactory factory) {
        super(factory);
    }

    @Override
    public void handler1(String message, DeviceCloudInfo.ThirdMapping thirdMapping) {
        DeviceMessageDTO deviceMessage = JSONObject.parseObject(message, DeviceMessageDTO.class);
        List<DeviceFunctionDTO> deviceFunctions = deviceMessage.getDeviceFunctions();

        if (CollectionUtil.isEmpty(deviceFunctions)) return;
        String clientId = thirdMapping.getClientId();
        ThirdPartyClientDO thirdPartyClientDO = thirdPartyClientRepository.selectByClientId(clientId);
        DeviceInfo deviceInfo = DeviceInfo.builder()
                .productId(deviceMessage.getProductId())
                .deviceId(deviceMessage.getDeviceId())
                .deviceFunctions(deviceFunctions.stream().map(st -> DeviceFunctionDTO.builder()
                        .signCode(st.getSignCode())
                        .value(st.getValue())
                        .timestamp(deviceMessage.getTimestamp())
                        .build()).collect(Collectors.toList()))
                .build();
        this.handler2(deviceInfo, thirdPartyClientDO, thirdMapping.getThirdId());
    }

    public abstract void handler2(DeviceInfo deviceInfo, ThirdPartyClientDO config, String thirdId);
}
