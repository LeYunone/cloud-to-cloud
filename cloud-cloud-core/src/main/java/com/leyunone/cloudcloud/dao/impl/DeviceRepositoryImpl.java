package com.leyunone.cloudcloud.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.cloudcloud.dao.DeviceRepository;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceDO;
import com.leyunone.cloudcloud.dao.mapper.DeviceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Repository
public class DeviceRepositoryImpl extends BaseRepository<DeviceMapper, DeviceDO,Object> implements DeviceRepository {

    @Override
    public List<DeviceDO> selectByThirdDeviceIdsClient(String clientId, List<String> thirdDeviceIds) {
        LambdaQueryWrapper<DeviceDO> lambda = new QueryWrapper<DeviceDO>().lambda();
        lambda.in(DeviceDO::getThirdDeviceId, thirdDeviceIds);
        lambda.eq(DeviceDO::getClientId, clientId);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public List<DeviceDO> selectByClientId(String clientId) {
        LambdaQueryWrapper<DeviceDO> lambda = new QueryWrapper<DeviceDO>().lambda();
        lambda.eq(DeviceDO::getClientId, clientId);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public List<DeviceDO> selectByTokenKey(String tokenKey) {
        LambdaQueryWrapper<DeviceDO> lambda = new QueryWrapper<DeviceDO>().lambda();
        lambda.eq(DeviceDO::getTokenKey, tokenKey);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public List<DeviceDO> selectByRefreshKeys(List<String> refreshTokenKeys) {
        LambdaQueryWrapper<DeviceDO> lambda = new QueryWrapper<DeviceDO>().lambda();
        lambda.in(DeviceDO::getRefreshTokenKey, refreshTokenKeys);
        return this.baseMapper.selectList(lambda);
    }
}
