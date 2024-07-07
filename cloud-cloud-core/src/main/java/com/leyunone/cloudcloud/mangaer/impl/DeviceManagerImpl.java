package com.leyunone.cloudcloud.mangaer.impl;

import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.mangaer.DeviceManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/4/7
 */
@Service
public class DeviceManagerImpl implements DeviceManager {
    @Override
    public void saveDeviceAndMapping(List<DeviceCloudInfo> entities) {

    }

    @Override
    public void deleteDeviceMappingByUserIdAndCloudId(Long userId, String cloud) {

    }

    @Override
    public List<DeviceCloudInfo> selectByDeviceIds(List<Long> deviceIds) {
        return null;
    }

    @Override
    public DeviceCloudInfo selectByDeviceId(Long deviceId) {
        return null;
    }

    @Override
    public void updateDeviceMappingByCloudAndUserIdAndDeviceId(List<DeviceCloudInfo> entities) {

    }
}
