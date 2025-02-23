package com.leyunone.cloudcloud.util;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *  调试设备
 * @Author LeYunone
 * @Date 2025/2/17 10:48
 */
public class TestUtil {

    public static List<DeviceInfo> getTestDevice() {
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
                                        .build(),
                                DeviceFunctionDTO.builder()
                                        .deviceId("2")
                                        .signCode("rgb")
                                        .value("{\"r\":255,\"g\":255,\"b\":255}")
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

    public static List<DeviceInfo> getControlTest(List<DeviceFunctionDTO> deviceCommands) {
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
                                    .value(dc.getValue())
                                    .build()).collect(Collectors.toList()))
                    .build();
        }).collect(Collectors.toList());
    }
}
