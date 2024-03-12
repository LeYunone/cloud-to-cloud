package com.leyunone.cloudcloud.mangaer;


import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-17
 */
public interface DeviceManager {


    /**
     * 不存在就新增存在就更新
     * @param entities
     */
    void saveDeviceAndMapping(List<DeviceCloudInfo> entities);

    void deleteDeviceMappingByUserIdAndCloudId(Long userId,String cloud);

    List<DeviceCloudInfo> selectByDeviceIds(List<Long> deviceIds);

    /**
     *
     * @param deviceId
     * @return
     */
    DeviceCloudInfo selectByDeviceId(Long deviceId);

    void updateDeviceMappingByCloudAndUserIdAndDeviceId(List<DeviceCloudInfo> entities);
}
