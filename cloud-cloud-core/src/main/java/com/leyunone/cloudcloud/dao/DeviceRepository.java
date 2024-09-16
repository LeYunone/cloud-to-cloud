package com.leyunone.cloudcloud.dao;


import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceDO;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface DeviceRepository extends IBaseRepository<DeviceDO> {

    List<DeviceDO> selectByThirdDeviceIdsClient(String clientId, List<String> thirdDeviceIds);

    List<DeviceDO> selectByClientId(String clientId);

    List<DeviceDO> selectByTokenKey(String tokenKey);

    List<DeviceDO> selectByRefreshKeys(List<String> refreshTokenKeys);
}
