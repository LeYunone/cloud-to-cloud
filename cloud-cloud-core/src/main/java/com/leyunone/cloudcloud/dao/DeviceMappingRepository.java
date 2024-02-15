package com.leyunone.cloudcloud.dao;

import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceMappingDO;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface DeviceMappingRepository extends IBaseRepository<DeviceMappingDO> {

    List<DeviceMappingDO> selectByDeviceIds(List<Long> deviceIds);

    List<DeviceMappingDO> selectByUserIdAndCloudId(Long userId,String cloud);

    List<DeviceMappingDO> selectByDeviceId(Long deviceId);

    void updateByDeviceIdAndCloudAndUserId(DeviceMappingDO deviceMappingDO);

    void updateBatchByDeviceIdAndCloudAndUserId(List<DeviceMappingDO> dos);

    void deleteByCloudAndUserId(Long userId,String cloud);

}
