package com.leyunone.cloudcloud.mangaer.impl;

import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Service
public class DeviceRelationManagerImpl implements DeviceRelationManager {

    @Override
    public void deleteDeviceMappingByUserIdAndCloudId(String userId, ThirdPartyCloudEnum cloud) {

    }

    @Override
    public void saveDeviceAndMapping(List<DeviceCloudInfo> deviceCloudInfos) {

    }
}
