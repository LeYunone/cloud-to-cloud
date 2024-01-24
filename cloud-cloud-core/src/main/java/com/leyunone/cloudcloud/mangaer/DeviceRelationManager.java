package com.leyunone.cloudcloud.mangaer;

import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public interface DeviceRelationManager {

    void deleteDeviceMappingByUserIdAndCloudId(String userId, ThirdPartyCloudEnum cloud);

    void saveDeviceAndMapping(List<DeviceCloudInfo> deviceCloudInfos);
}
