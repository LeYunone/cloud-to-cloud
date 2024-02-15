package com.leyunone.cloudcloud.dao;

import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.ThirdPartyClientDO;
import com.leyunone.cloudcloud.dao.mapper.ThirdPartyClientMapper;
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
                cacheManager.addDate(key, outwardClientDO, 300L);
            }
        }
        return outwardClientDO;
    }

    private String generateCacheKey(String clientId) {
        return String.join("_", this.getClass().getSimpleName(), clientId);
    }
}
