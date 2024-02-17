package com.leyunone.cloudcloud.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceMappingDO;
import com.leyunone.cloudcloud.dao.mapper.DeviceMappingMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Repository
public class DeviceMappingRepositoryImpl extends BaseRepository<DeviceMappingMapper, DeviceMappingDO> implements DeviceMappingRepository {

    @Resource
    DeviceMappingMapper deviceMappingMapper;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<DeviceMappingDO> selectByDeviceIds(List<Long> deviceIds) {
        return deviceMappingMapper.selectList(new LambdaQueryWrapper<DeviceMappingDO>()
                .in(DeviceMappingDO::getDeviceId, deviceIds)
        );
    }

    @Override
    public List<DeviceMappingDO> selectByUserIdAndCloudId(String userId, String cloud) {
        return deviceMappingMapper.selectList(new LambdaQueryWrapper<DeviceMappingDO>().eq(DeviceMappingDO::getUserId, userId).eq(DeviceMappingDO::getThirdPartyCloud, cloud));
    }

    @Override
    public void deleteByCloudAndUserId(String userId, String cloud) {
        deviceMappingMapper.delete(new LambdaQueryWrapper<DeviceMappingDO>().eq(DeviceMappingDO::getUserId, userId).eq(DeviceMappingDO::getThirdPartyCloud, cloud));
    }

    @Override
    public List<DeviceMappingDO> selectByDeviceId(Long deviceId) {
        return deviceMappingMapper.selectList(new LambdaQueryWrapper<DeviceMappingDO>()
                .eq(DeviceMappingDO::getDeviceId, deviceId)
        );
    }


    @Override
    public void updateByDeviceIdAndCloudAndUserId(DeviceMappingDO deviceMappingDO) {
        LambdaUpdateWrapper<DeviceMappingDO> lambda = new UpdateWrapper<DeviceMappingDO>().lambda();
        lambda.eq(DeviceMappingDO::getThirdPartyCloud, deviceMappingDO.getThirdPartyCloud());
        lambda.eq(DeviceMappingDO::getUserId, deviceMappingDO.getUserId());
        lambda.eq(DeviceMappingDO::getDeviceId, deviceMappingDO.getDeviceId());
    }

    @Override
    public void updateBatchByDeviceIdAndCloudAndUserId(List<DeviceMappingDO> dos) {
//        if (CollectionUtils.isEmpty(dos)) {
//            return;
//        }
//        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//        try {
//            dos.forEach(d -> {
//                d.update(new LambdaQueryWrapper<DeviceMappingDO>()
//                        .eq(DeviceMappingDO::getThirdPartyCloud, d.getThirdPartyCloud())
//                        .eq(DeviceMappingDO::getUserId, d.getUserId())
//                        .eq(DeviceMappingDO::getDeviceId, d.getDeviceId())
//                );
//            });
//        } finally {
//            sqlSession.commit();
//            sqlSession.clearCache();
//            sqlSession.close();
//        }
    }


    private String getCacheKeyForDeviceIdAndThirdCloud(Long deviceId, String thirdCloud) {
        return String.join("_", this.getClass().getName(), deviceId.toString(), thirdCloud);
    }

}
