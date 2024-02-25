package com.leyunone.cloudcloud.dao;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceCapabilityDO;
import com.leyunone.cloudcloud.dao.mapper.DeviceCapabilityMapper;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Repository
public class DeviceCapabilityRepositoryImpl extends BaseRepository<DeviceCapabilityMapper, DeviceCapabilityDO> implements DeviceCapabilityRepository {

    private final CacheManager cacheManager;

    public static final String CAPABILITY_KEY = "CAPABILITY_CONFIG";

    public DeviceCapabilityRepositoryImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public List<DeviceCapabilityDO> selectByCloud(ThirdPartyCloudEnum cloud) {
        String data = cacheManager.getData(CAPABILITY_KEY, String.class);
        if (StrUtil.isBlank(data)) {
            LambdaQueryWrapper<DeviceCapabilityDO> lambda = new QueryWrapper<DeviceCapabilityDO>().lambda();
            lambda.eq(DeviceCapabilityDO::getThirdPartyCloud, cloud.name());
            List<DeviceCapabilityDO> deviceCapabilityDOS = this.baseMapper.selectList(lambda);
            data = JSONObject.toJSONString(deviceCapabilityDOS);
            cacheManager.addData(CAPABILITY_KEY, data, 15L, TimeUnit.MINUTES);
        }
        return JSONObject.parseArray(data, DeviceCapabilityDO.class);
    }
}
