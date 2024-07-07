package com.leyunone.cloudcloud.mangaer;

import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.info.DeviceMappingInfo;
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

    /**
     * 不存在就新增存在就更新
     * @param deviceMappingInfos
     */
    void saveDeviceAndMapping(List<DeviceMappingInfo> deviceMappingInfos);

    void deleteDeviceMappingByUserIdAndCloudId(String userId,ThirdPartyCloudEnum cloud);

    List<DeviceCloudInfo> selectByDeviceIds(List<String> deviceIds);

    /**
     *
     * @param deviceId
     * @return
     */
    DeviceCloudInfo selectByDeviceId(String deviceId);

    void updateDeviceMappingByCloudAndUserIdAndDeviceId(List<DeviceCloudInfo> entities);
}
