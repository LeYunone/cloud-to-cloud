package com.leyunone.cloudcloud.mangaer.impl;

import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public class DeviceServiceHttpManagerImpl implements DeviceServiceHttpManager {

    @Override
    public List<DeviceInfo> getDeviceListByUserId(String userId, ThirdPartyCloudConfigInfo cloudConfig) {
        return null;
    }

    @Override
    public DeviceInfo getDeviceStatusByDeviceId(String userId, String deviceId, ThirdPartyCloudConfigInfo cloudConfig) {
        return null;
    }

    @Override
    public List<DeviceInfo> getDevicesStatusByDeviceIds(String userId, List<String> deviceIds, ThirdPartyCloudConfigInfo cloudConfig) {
        return null;
    }

    @Override
    public DeviceInfo command(String userId, DeviceFunctionDTO deviceCommand, ThirdPartyCloudConfigInfo cloudConfig) {
        return null;
    }

    @Override
    public List<DeviceInfo> commands(String userId, List<DeviceFunctionDTO> deviceCommands, ThirdPartyCloudConfigInfo cloudConfig) {
        return null;
    }
}
