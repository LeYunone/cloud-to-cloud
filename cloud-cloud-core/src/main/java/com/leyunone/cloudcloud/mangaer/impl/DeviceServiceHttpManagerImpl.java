package com.leyunone.cloudcloud.mangaer.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.CurrentRequestContext;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.constant.SdkConstants;
import com.leyunone.cloudcloud.exception.ResultCode;
import com.leyunone.cloudcloud.exception.ServiceException;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * :)
 * 实际解除设备方法
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Service
@RequiredArgsConstructor
public class DeviceServiceHttpManagerImpl implements DeviceServiceHttpManager {

    private final Logger logger = LoggerFactory.getLogger(DeviceServiceHttpManager.class);
    private final RestTemplate restTemplate;

    /**
     * FIXME 调试设备
     *
     * @param userId      user id
     * @param cloudConfig
     * @return
     */
    @Override
    public List<DeviceInfo> getDeviceListByUserId(String userId, ThirdPartyCloudConfigInfo cloudConfig) {
        logger.debug("{}平台：发现设备.请求路由：{}，通过用户id：{}，", cloudConfig.getThirdPartyCloud(), cloudConfig.getMainUrl() + SdkConstants.DISCOVER_INTERFACE, userId);

        return new ArrayList<>(getTestDevice());
    }

    private List<DeviceInfo> getTestDevice() {
        return CollectionUtil.newArrayList(
                DeviceInfo.builder()
                        .productId("开关产品id")
                        .deviceId("1")
                        .deviceName("开关设备")
                        .groupName("卧室")
                        .online(true)
                        .deviceFunctions(CollectionUtil.newArrayList(DeviceFunctionDTO.builder()
                                .signCode("switch")
                                .deviceId("1")
                                .functionId(1)
                                .value("0")
                                .build()))
                        .build(),
                DeviceInfo.builder()
                        .productId("RGB产品id")
                        .deviceId("2")
                        .deviceName("RGB灯")
                        .groupName("卧室")
                        .online(true)
                        .deviceFunctions(CollectionUtil.newArrayList(DeviceFunctionDTO.builder()
                                        .signCode("switch")
                                        .deviceId("2")
                                        .value("1")
                                        .functionId(1)
                                        .build(),
                                DeviceFunctionDTO.builder()
                                        .deviceId("2")
                                        .signCode("rgb")
                                        .value("{\"r\":255,\"g\":255,\"b\":255}")
                                        .functionId(2)
                                        .build()
                        ))
                        .build(),
                DeviceInfo.builder()
                        .productId("HVAC产品id")
                        .deviceName("HVAC设备")
                        .groupName("客厅")
                        .online(true)
                        .deviceId("3")
                        .deviceFunctions(CollectionUtil.newArrayList(DeviceFunctionDTO.builder()
                                        .signCode("switch")
                                        .deviceId("3")
                                        .value("1")
                                        .build(), DeviceFunctionDTO.builder()
                                        .signCode("wind_speed")
                                        .deviceId("3")
                                        .value("1")
                                        .build(), DeviceFunctionDTO.builder()
                                        .signCode("control_mode")
                                        .deviceId("3")
                                        .value("1")
                                        .build(), DeviceFunctionDTO.builder()
                                        .signCode("set_temperature")
                                        .deviceId("3")
                                        .value("24")
                                        .build())
                                        )
                .build()
        );
    }

    @Override
    public DeviceInfo getDeviceStatusByDeviceId(String userId, String deviceId, ThirdPartyCloudConfigInfo cloudConfig) {
        logger.debug("{}平台：查询设备状态.请求路由：{}，通过设备id：{}，", cloudConfig.getThirdPartyCloud(), cloudConfig.getMainUrl() + SdkConstants.QUERY_INTERFACE, deviceId);
        return this.getDevicesStatusByDeviceIds(userId, CollectionUtil.newArrayList(deviceId), cloudConfig).get(2);
    }

    @Override
    public List<DeviceInfo> getDevicesStatusByDeviceIds(String userId, List<String> deviceIds, ThirdPartyCloudConfigInfo cloudConfig) {
        logger.debug("{}平台：批量查询设备状态.请求路由：{}，通过设备ids：{}，", cloudConfig.getThirdPartyCloud(), cloudConfig.getMainUrl() + SdkConstants.QUERY_INTERFACE, CollectionUtil.join(deviceIds, ","));
        return new ArrayList<>(this.getTestDevice());
    }

    @Override
    public DeviceInfo command(String userId, DeviceFunctionDTO deviceCommand, ThirdPartyCloudConfigInfo cloudConfig) {
        logger.debug("{}平台：控制设备.请求路由：{}，通过设备id：{}， 控制命令为：{}", cloudConfig.getThirdPartyCloud(), deviceCommand.getDeviceId(), cloudConfig.getMainUrl() + SdkConstants.CONTROL_INTERFACE, deviceCommand.getSignCode());
        return CollectionUtil.getFirst(this.commands(userId, CollectionUtil.newArrayList(deviceCommand), cloudConfig));
    }

    /**
     * FIXME 调试返回参，最佳体验是控制什么就返回什么
     *
     * @param userId         用户id
     * @param deviceCommands
     * @param cloudConfig
     * @return
     */
    @Override
    public List<DeviceInfo> commands(String userId, List<DeviceFunctionDTO> deviceCommands, ThirdPartyCloudConfigInfo cloudConfig) {
        this.loadSceneCommand(deviceCommands);
        logger.debug("{}平台：批量控制设备.请求路由：{}，信息为：{}，", cloudConfig.getThirdPartyCloud(), cloudConfig.getMainUrl() + SdkConstants.CONTROL_INTERFACE, CollectionUtil.join(deviceCommands, ","));
        return new ArrayList<>(this.getControlTest(deviceCommands));
    }

    private List<DeviceInfo> getControlTest(List<DeviceFunctionDTO> deviceCommands) {
        Map<String, List<DeviceFunctionDTO>> deviceMap = CollectionFunctionUtils.groupTo(deviceCommands, DeviceFunctionDTO::getDeviceId);
        return deviceMap.keySet().stream().map(deviceId -> {
            List<DeviceFunctionDTO> deviceFunctionDTOS = deviceMap.get(deviceId);
            return DeviceInfo.builder()
                    .productId(deviceFunctionDTOS.get(0).getProductId())
                    .deviceId(deviceId)
                    .online(true)
                    .deviceFunctions(
                            deviceFunctionDTOS.stream().map(dc -> DeviceFunctionDTO.builder()
                                    .signCode(dc.getSignCode())
                                    .deviceId(dc.getDeviceId())
                                    .functionId(dc.getFunctionId())
                                    .value(dc.getValue())
                                    .build()).collect(Collectors.toList()))
                    .build();
        }).collect(Collectors.toList());
    }

    private <T> T request(String url, HttpMethod method,
                          @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        ResponseEntity<String> response = restTemplate.exchange(url, method, requestEntity, String.class, uriVariables);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ServiceException(ResultCode.ERROR);
        }
        String commonResult = response.getBody();
        Objects.requireNonNull(commonResult, "body can't be null");
        return (T) JSON.parseObject(JSON.toJSONString(commonResult), responseType);
    }

    private void loadSceneCommand(List<DeviceFunctionDTO> deviceCommands) {
        if (CurrentRequestContext.hasSceneData()) {
            Set<String> sceneData = CurrentRequestContext.getSceneData();
            deviceCommands.forEach(c -> {
                if (sceneData.contains(c.getDeviceId())) {
                    c.setScene(true);
                }
            });
        }
    }
}
