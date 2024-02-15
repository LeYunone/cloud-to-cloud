package com.leyunone.cloudcloud.dao;

import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceCapabilityDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface DeviceCapabilityRepository extends IBaseRepository<DeviceCapabilityDO> {

    List<DeviceCapabilityDO> selectByPids(List<String> pids, ThirdPartyCloudEnum cloud);
}
