package com.leyunone.cloudcloud.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceCapabilityDO;
import com.leyunone.cloudcloud.dao.mapper.DeviceCapabilityMapper;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
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
public class DeviceCapabilityRepositoryImpl extends BaseRepository<DeviceCapabilityMapper, DeviceCapabilityDO> implements DeviceCapabilityRepository {

    @Override
    public List<DeviceCapabilityDO> selectByPids(List<String> pids, ThirdPartyCloudEnum cloud) {
        LambdaQueryWrapper<DeviceCapabilityDO> lambda = new QueryWrapper<DeviceCapabilityDO>().lambda();
        lambda.in(DeviceCapabilityDO::getProductId, pids);
        lambda.eq(DeviceCapabilityDO::getThirdPartyCloud, cloud.name());
        return this.baseMapper.selectList(lambda);
    }
}
