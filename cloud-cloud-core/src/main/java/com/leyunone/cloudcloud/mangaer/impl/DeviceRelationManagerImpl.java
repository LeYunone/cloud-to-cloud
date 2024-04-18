package com.leyunone.cloudcloud.mangaer.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.info.DeviceMappingInfo;
import com.leyunone.cloudcloud.dao.DeviceMappingRepository;
import com.leyunone.cloudcloud.dao.DeviceRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceDO;
import com.leyunone.cloudcloud.dao.entity.DeviceMappingDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Service
@RequiredArgsConstructor
public class DeviceRelationManagerImpl implements DeviceRelationManager {


    private final DeviceMappingRepository deviceMappingRepository;
    private final DeviceRepository deviceRepository;
    private final CacheManager cacheManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveDeviceAndMapping(List<DeviceMappingInfo> deviceMappings) {
        DeviceMappingInfo deviceMapping = CollectionUtil.getFirst(deviceMappings);
        List<DeviceMappingDO> userDevices = deviceMappingRepository.selectByUserIdAndCloudId(deviceMapping.getUserId(), deviceMapping.getThirdPartyCloud());
        List<DeviceMappingDO> insertMapping = new ArrayList<>();
        List<DeviceMappingDO> updateMapping = new ArrayList<>();
        List<Long> deleteMapping = new ArrayList<>();
        Map<String, DeviceMappingDO> userMap = CollectionFunctionUtils.mapTo(userDevices, d -> this.generateDeviceMappingKey(d.getThirdPartyCloud(), d.getUserId(), d.getDeviceId()));
        //用户-设备关系存储
        for (DeviceMappingInfo d : deviceMappings) {
            String key = this.generateDeviceMappingKey(d.getThirdPartyCloud(), d.getUserId(), d.getDeviceId());
            if (userMap.containsKey(key)) {
                //更新
                DeviceMappingDO dbData = userMap.get(key);
                if (StringUtils.isNotBlank(d.getThirdId()) && !d.getThirdId().equals(dbData.getThirdId())) {
                    dbData.setThirdId(d.getThirdId());
                    updateMapping.add(dbData);
                }
                userMap.remove(key);
            } else {
                //新增
                DeviceMappingDO newData = new DeviceMappingDO();
                BeanUtil.copyProperties(d, newData);
                insertMapping.add(newData);
            }
        }
        if (CollectionUtil.isNotEmpty(userMap)) {
            deleteMapping.addAll(userMap.values().stream().map(DeviceMappingDO::getId).collect(Collectors.toList()));
        }

        //设备模型保存
        List<DeviceDO> devices = deviceMappings.stream().map(dm -> new DeviceDO()
                .setDeviceId(dm.getDeviceId())
                .setProductId(dm.getProductId())).collect(Collectors.toList());
        deviceRepository.saveOrUpdateBatch(devices);

        if (CollectionUtil.isNotEmpty(insertMapping)) {
            deviceMappingRepository.insertBatch(insertMapping);
        }
        if (CollectionUtil.isNotEmpty(updateMapping)) {
            deviceMappingRepository.updateBatchById(updateMapping);
        }
        if (CollectionUtil.isNotEmpty(deleteMapping)) {
            deviceMappingRepository.deleteByIds(deleteMapping);
        }
        List<Object> keys = updateMapping.stream().map(d -> generateCacheKeyByDeviceId(d.getDeviceId())).collect(Collectors.toList());
        cacheManager.deleteData(keys);
    }

    @Override
    public void deleteDeviceMappingByUserIdAndCloudId(String userId, ThirdPartyCloudEnum cloud) {
        List<DeviceMappingDO> deviceMappingDOS = deviceMappingRepository.selectByUserIdAndCloudId(userId, cloud);
        if (CollectionUtil.isNotEmpty(deviceMappingDOS)) {
            List<String> deviceIds = deviceMappingDOS.stream().map(DeviceMappingDO::getDeviceId).collect(Collectors.toList());
            //删除设备关系缓存
            List<Object> keys = deviceIds.stream().distinct().map(this::generateCacheKeyByDeviceId).collect(Collectors.toList());
            cacheManager.deleteData(keys);
        }
        deviceMappingRepository.deleteByCloudAndUserId(userId, cloud);
    }

    @Override
    public List<DeviceCloudInfo> selectByDeviceIds(List<String> deviceIds) {
        if (CollectionUtil.isEmpty(deviceIds)) {
            return new ArrayList<>();
        }
        List<Object> keys = deviceIds.stream().distinct().map(this::generateCacheKeyByDeviceId).collect(Collectors.toList());
        Optional<List<DeviceCloudInfo>> deviceMappingEntities = cacheManager
                .getData(keys,
                        1L, TimeUnit.MINUTES,
                        hits -> {
                            List<String> miss;
                            if (CollectionUtil.isEmpty(hits)) {
                                miss = deviceIds;
                            } else {
                                Map<String, DeviceCloudInfo> hitsMap = hits.stream().collect(Collectors.toMap(DeviceCloudInfo::getDeviceId, v -> v, (v1, v2) -> v2));
                                miss = deviceIds.stream().filter(did -> !hitsMap.containsKey(did)).collect(Collectors.toList());
                            }
                            if (!CollectionUtil.isEmpty(miss)) {
                                List<DeviceDO> deviceDos = deviceRepository.selectByIds(miss);
                                List<DeviceMappingDO> mappingDos = deviceMappingRepository.selectByDeviceIds(miss);
                                Map<String, List<DeviceMappingDO>> mappingMap = mappingDos.stream().collect(Collectors.groupingBy(DeviceMappingDO::getDeviceId));
                                return deviceDos
                                        .stream()
                                        .map(d -> {
                                            List<DeviceMappingDO> mappings = mappingMap.get(d.getDeviceId());
                                            List<DeviceCloudInfo.ThirdMapping> thirdMappings = new ArrayList<>();
                                            if (!CollectionUtil.isEmpty(mappings)) {
                                                thirdMappings = mappings
                                                        .stream()
                                                        .map(m -> DeviceCloudInfo
                                                                .ThirdMapping
                                                                .builder()
                                                                .thirdId(m.getThirdId())
                                                                .thirdPartyCloud(m.getThirdPartyCloud())
                                                                .userId(m.getUserId())
                                                                .clientId(m.getClientId())
                                                                .build()
                                                        )
                                                        .collect(Collectors.toList());
                                            }
                                            return DeviceCloudInfo.builder().deviceId(d.getDeviceId()).productId(d.getProductId()).mapping(thirdMappings).build();
                                        })
                                        .collect(Collectors.toList());
                            } else {
                                return new ArrayList<>();
                            }
                        },
                        did -> generateCacheKeyByDeviceId(did.getDeviceId()));
        return deviceMappingEntities.orElse(new ArrayList<>());
    }

    @Override
    public DeviceCloudInfo selectByDeviceId(String deviceId) {
        String key = generateCacheKeyByDeviceId(deviceId);
        Optional<DeviceCloudInfo> deviceEntity = cacheManager.getData(key,
                1L, TimeUnit.MINUTES,
                () -> {
                    DeviceDO deviceDO = deviceRepository.selectById(deviceId);
                    if (null == deviceDO) {
                        return null;
                    }
                    List<DeviceMappingDO> mappingDos = deviceMappingRepository.selectByDeviceId(deviceId);
                    List<DeviceCloudInfo.ThirdMapping> thirdMappings = mappingDos
                            .stream()
                            .map(m -> DeviceCloudInfo
                                    .ThirdMapping
                                    .builder()
                                    .thirdId(m.getThirdId())
                                    .thirdPartyCloud(m.getThirdPartyCloud())
                                    .userId(m.getUserId())
                                    .clientId(m.getClientId())
                                    .build()
                            )
                            .collect(Collectors.toList());

                    return DeviceCloudInfo
                            .builder()
                            .deviceId(deviceDO.getDeviceId())
                            .productId(deviceDO.getProductId())
                            .mapping(thirdMappings)
                            .build();
                });
        return deviceEntity.orElse(null);
    }

    @Override
    public void updateDeviceMappingByCloudAndUserIdAndDeviceId(List<DeviceCloudInfo> entities) {
        if (CollectionUtil.isEmpty(entities)) {
            return;
        }
        List<Object> keys = entities.stream().map(d -> generateCacheKeyByDeviceId(d.getDeviceId())).collect(Collectors.toList());
        List<DeviceMappingDO> dos = entities.stream()
                .flatMap(d -> d.getMapping()
                        .stream()
                        .map(m -> new DeviceMappingDO()
                                .setThirdPartyCloud(m.getThirdPartyCloud())
                                .setUserId(m.getUserId())
                                .setDeviceId(d.getDeviceId())
                                .setClientId(m.getClientId())
                                .setUpdateTime(LocalDateTime.now())
                                .setThirdId(m.getThirdId())
                        )
                )
                .collect(Collectors.toList());
        deviceMappingRepository.updateBatchByDeviceIdAndCloudAndUserId(dos);
        cacheManager.deleteData(keys);
    }

    private String generateDeviceMappingKey(ThirdPartyCloudEnum cloud, String userId, String deviceId) {
        return String.join("_", cloud.name(), userId, deviceId);
    }

    private String generateCacheKeyByDeviceId(String deviceId) {
        return String.join("_", this.getClass().getName(), deviceId);
    }
}
