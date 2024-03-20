package com.leyunone.cloudcloud.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.leyunone.cloudcloud.bean.UserClientInfoModel;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.UserAuthorizeDO;
import com.leyunone.cloudcloud.dao.mapper.UserAuthorizeMapper;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.mangaer.CacheManager;
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
public class UserAuthorizeRepositoryImpl extends BaseRepository<UserAuthorizeMapper, UserAuthorizeDO> implements UserAuthorizeRepository {


    private static final String USER_AUTHORIZE_CACHE_PREFIX = "USER_AUTHORIZE";
    private static final Long USER_AUTHORIZE_CACHE_TIME = 120L;

    private final CacheManager cacheManager;

    public UserAuthorizeRepositoryImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public UserAuthorizeDO selectByUserIdAndThirdPartyCloud(String userId, ThirdPartyCloudEnum cloud) {
        UserAuthorizeDO userAuthorizeDO = cacheManager.getData(generateUserCacheKey(userId, cloud), UserAuthorizeDO.class);
        if (null == userAuthorizeDO) {
            userAuthorizeDO = this.baseMapper.selectOne(new LambdaQueryWrapper<UserAuthorizeDO>()
                    .eq(UserAuthorizeDO::getUserId, userId)
                    .eq(UserAuthorizeDO::getThirdPartyCloud, cloud.name()));
            cacheManager.addData(generateUserCacheKey(userId, cloud), userAuthorizeDO, USER_AUTHORIZE_CACHE_TIME);
        }
        return userAuthorizeDO;
    }

    @Override
    public void updateByUserIdAndThirdPartyCloud(UserAuthorizeDO userAuthorizeDO, ThirdPartyCloudEnum cloud) {
        cacheManager.deleteData(generateUserCacheKey(userAuthorizeDO.getUserId(), cloud));
        this.baseMapper.update(userAuthorizeDO, new LambdaUpdateWrapper<UserAuthorizeDO>()
                .eq(UserAuthorizeDO::getUserId, userAuthorizeDO.getUserId())
                .eq(UserAuthorizeDO::getThirdPartyCloud, cloud.name())
        );

    }


    @Override
    public UserClientInfoModel selectUserClientInfo(String userId, ThirdPartyCloudEnum cloud) {
        return this.baseMapper.selectUserClientInfo(userId, cloud);
    }

    @Override
    public List<UserAuthorizeDO> selectByUserIds(List<String> userIds) {
        LambdaQueryWrapper<UserAuthorizeDO> lambda = new QueryWrapper<UserAuthorizeDO>().lambda();
        lambda.in(UserAuthorizeDO::getUserId, userIds);
        return this.baseMapper.selectList(lambda);
    }

    private String generateUserCacheKey(String userId, ThirdPartyCloudEnum cloud) {
        return String.join("_", USER_AUTHORIZE_CACHE_PREFIX, cloud.name(), userId.toString());
    }


}
