package com.leyunone.cloudcloud.mangaer;

import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public interface DeviceServiceHttpManager {


    /**
     * 根据userId查询设备列表
     *
     * @param userId      user id
     * @param cloudConfig
     * @return 设备列表
     */
    List<DeviceInfo> getDeviceListByUserId(Long userId, ThirdPartyCloudConfigInfo cloudConfig);


    DeviceInfo getDeviceStatusByDeviceId(Long userId, Long deviceId, ThirdPartyCloudConfigInfo cloudConfig);

    /**
     * 批量获取状态
     *
     * @param deviceIds
     * @param cloudConfig
     * @return
     */
    List<DeviceInfo> getDevicesStatusByDeviceIds(Long userId, List<Long> deviceIds, ThirdPartyCloudConfigInfo cloudConfig);


    DeviceInfo command(Long userId, DeviceFunctionDTO deviceCommand, ThirdPartyCloudConfigInfo cloudConfig);

    /**
     * 批量控制
     *
     * @param userId
     * @param deviceCommands
     * @param cloudConfig
     * @return
     */
    List<DeviceInfo> commands(Long userId, List<DeviceFunctionDTO> deviceCommands, ThirdPartyCloudConfigInfo cloudConfig);
}
