package com.leyunone.cloudcloud.mangaer.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Service
public class DeviceServiceHttpManagerImpl implements DeviceServiceHttpManager {

    private final Logger logger = LoggerFactory.getLogger(DeviceServiceHttpManager.class);

    @Override
    public List<DeviceInfo> getDeviceListByUserId(String userId, ThirdPartyCloudConfigInfo cloudConfig) {
        logger.warn("{}平台：发现设备，通过用户id：{}，", cloudConfig.getThirdPartyCloud(), userId);
        return new ArrayList<>();
    }

    @Override
    public DeviceInfo getDeviceStatusByDeviceId(String userId, String deviceId, ThirdPartyCloudConfigInfo cloudConfig) {
        logger.warn("{}平台：查询设备状态，通过设备id：{}，", cloudConfig.getThirdPartyCloud(), deviceId);
        return new DeviceInfo();
    }

    @Override
    public List<DeviceInfo> getDevicesStatusByDeviceIds(String userId, List<String> deviceIds, ThirdPartyCloudConfigInfo cloudConfig) {
        logger.warn("{}平台：批量查询设备状态，通过设备ids：{}，", cloudConfig.getThirdPartyCloud(), CollectionUtil.join(deviceIds, ","));
        return new ArrayList<>();
    }

    @Override
    public DeviceInfo command(String userId, DeviceFunctionDTO deviceCommand, ThirdPartyCloudConfigInfo cloudConfig) {
        logger.warn("{}平台：控制设备，通过设备id：{}， 控制命令为：{}", cloudConfig.getThirdPartyCloud(), deviceCommand.getDeviceId(), deviceCommand.getSignCode());
        return new DeviceInfo();
    }

    @Override
    public List<DeviceInfo> commands(String userId, List<DeviceFunctionDTO> deviceCommands, ThirdPartyCloudConfigInfo cloudConfig) {
        logger.warn("{}平台：批量控制设备，信息为：{}，", cloudConfig.getThirdPartyCloud(), CollectionUtil.join(deviceCommands, ","));
        return new ArrayList<>();
    }
}
