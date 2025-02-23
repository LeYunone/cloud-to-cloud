package com.leyunone.cloudcloud.mangaer;

import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;

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
     * @param context
     * @return 设备列表
     */
    List<DeviceInfo> getDeviceListByUserId(ActionContext context);


    DeviceInfo getDeviceStatusByDeviceId(ActionContext context, String deviceId);

    /**
     * 批量获取状态
     *
     * @param deviceIds
     * @param context
     * @return
     */
    List<DeviceInfo> getDevicesStatusByDeviceIds(ActionContext context, List<String> deviceIds);


    DeviceInfo command(ActionContext context, DeviceFunctionDTO deviceCommand);

    /**
     * 批量控制
     *
     * @param context
     * @param deviceCommands
     * @return
     */
    List<DeviceInfo> commands(ActionContext context, List<DeviceFunctionDTO> deviceCommands);
}
