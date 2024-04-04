package com.leyunone.cloudcloud.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.cloudcloud.dao.ThirdPartyClientRepository;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.ThirdPartyClientDO;
import com.leyunone.cloudcloud.dao.mapper.ThirdPartyClientMapper;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Repository
public class ThirdPartyClientRepositoryImpl extends BaseRepository<ThirdPartyClientMapper, ThirdPartyClientDO> implements ThirdPartyClientRepository {

    @Resource
    ThirdPartyClientMapper thirdPartyClientMapper;

    private final CacheManager cacheManager;

    public ThirdPartyClientRepositoryImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    @Override
    public ThirdPartyClientDO selectByClientId(String clientId) {
        String key = generateCacheKey(clientId);
        ThirdPartyClientDO outwardClientDO = cacheManager.getData(key, ThirdPartyClientDO.class);
        if (null == outwardClientDO) {
            outwardClientDO = thirdPartyClientMapper.selectById(clientId);
            if (null != outwardClientDO) {
                cacheManager.addData(key, outwardClientDO, 300L);
            }
        }
        return outwardClientDO;
    }

    @Override
    public ThirdPartyClientDO selectByCloud(ThirdPartyCloudEnum cloudEnum) {
        LambdaQueryWrapper<ThirdPartyClientDO> lambda = new QueryWrapper<ThirdPartyClientDO>().lambda();
        lambda.eq(ThirdPartyClientDO::getThirdPartyCloud,cloudEnum);
        return this.baseMapper.selectOne(lambda);
    }

    @Override
    public int deleteByCloud(ThirdPartyCloudEnum cloudEnum) {
        LambdaQueryWrapper<ThirdPartyClientDO> lambda = new QueryWrapper<ThirdPartyClientDO>().lambda();
        lambda.eq(ThirdPartyClientDO::getThirdPartyCloud,cloudEnum);
        return this.baseMapper.delete(lambda);
    }

    private String generateCacheKey(String clientId) {
        return String.join("_", this.getClass().getSimpleName(), clientId);
    }
}
