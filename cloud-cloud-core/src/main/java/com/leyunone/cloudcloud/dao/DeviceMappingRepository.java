package com.leyunone.cloudcloud.dao;

import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceMappingDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface DeviceMappingRepository extends IBaseRepository<DeviceMappingDO> {

    List<DeviceMappingDO> selectByDeviceIds(List<String> deviceIds);

    List<DeviceMappingDO> selectByUserIdAndCloudId(String userId, ThirdPartyCloudEnum cloud);

    List<DeviceMappingDO> selectByDeviceId(String deviceId);

    void updateByDeviceIdAndCloudAndUserId(DeviceMappingDO deviceMappingDO);

    void updateBatchByDeviceIdAndCloudAndUserId(List<DeviceMappingDO> dos);

    void deleteByCloudAndUserId(String userId,ThirdPartyCloudEnum cloud);

}
