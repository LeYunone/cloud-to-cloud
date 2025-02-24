package com.leyunone.cloudcloud.mangaer.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.dao.UserAuthorizeRepository;
import com.leyunone.cloudcloud.dao.entity.UserAuthorizeDO;
import com.leyunone.cloudcloud.exception.ResultCode;
import com.leyunone.cloudcloud.exception.ServiceException;
import com.leyunone.cloudcloud.mangaer.AccessTokenManager;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/18
 */
@Service
public class AccessTokenManagerImpl implements AccessTokenManager {

    /**
     * token有效时间
     */
    public final static Long TOKEN_EFFICIENT_TIME = 1000L * 60 * 60 * 24 * 15;

    /**
     * token刷新时间
     */
    public final static Long REFRESH_TOKEN_EFFICIENT_TIME = 1000L * 60 * 60 * 24 * 180;

    /**
     * 授权码有效期
     */
    public final static Long CODE_EFFICIENT_TIME = 600L;


    private static final String OAUTH2_CODE = "OAUTH2_CODE";

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    private static final String USER_TOKEN_PREFIX = "USER_TOKEN";

    private final CacheManager cacheManager;
    private final UserAuthorizeRepository userAuthorizeRepository;

    public AccessTokenManagerImpl(CacheManager cacheManager, UserAuthorizeRepository userAuthorizeRepository) {
        this.cacheManager = cacheManager;
        this.userAuthorizeRepository = userAuthorizeRepository;
    }

    @Override
    public String generateOAuthCode(String clientId, String userId, ThirdPartyCloudConfigInfo cloudServiceConfig) {
        UserAuthorizeDO userAuthorizeDO = userAuthorizeRepository.selectByUserIdAndThirdPartyCloud(userId, cloudServiceConfig.getThirdPartyCloud());
        if (null == userAuthorizeDO) {
            userAuthorizeDO = new UserAuthorizeDO()
                    .setUserId(userId)
                    .setThirdPartyCloud(cloudServiceConfig.getThirdPartyCloud())
                    .setClientId(cloudServiceConfig.getClientId())
                    .setCreateTime(LocalDateTime.now());
            userAuthorizeRepository.save(userAuthorizeDO);
        }
        String code = UUID.randomUUID().toString();
        AccessTokenInfo.User user = new AccessTokenInfo.User();
        BeanUtil.copyProperties(userAuthorizeDO, user);
        cacheManager.addData(generateCodeKey(code), user, CODE_EFFICIENT_TIME);
        return code;
    }

    @Override
    public AccessTokenInfo generateAccessTokenByCode(String code, String clientId) {
        //注意写入token时需要判断是否已经存在
        AccessTokenInfo.User userAuthorize = cacheManager.getData(generateCodeKey(code), AccessTokenInfo.User.class);
        if (null == userAuthorize) {
            /**
             * TODO 用户不存在 说明用户登录鉴权失败
             */
            throw new ServiceException(ResultCode.FORBIDDEN);
        }
        cacheManager.deleteData(generateCodeKey(code));
        //查询用户是否已有token
        String userId = userAuthorize.getUserId();
        AccessTokenInfo oldAccessToken = cacheManager.getData(generateUserTokenKey(userId, clientId), AccessTokenInfo.class);
        if (ObjectUtil.isNull(oldAccessToken)) {
            cacheManager.deleteData(generateAccessTokenKey(oldAccessToken.getAccessToken()));
            cacheManager.deleteData(generateRefreshTokenKey(oldAccessToken.getRefreshToken()));
        }
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
        accessTokenInfo.setAccessToken(accessToken);
        accessTokenInfo.setRefreshToken(refreshToken);
        accessTokenInfo.setExpiresIn(TOKEN_EFFICIENT_TIME);
        accessTokenInfo.setClientId(clientId);
        accessTokenInfo.setUser(userAuthorize);
        accessTokenInfo.setTokenType("bearer");
        cacheManager.addData(generateUserTokenKey(userId, clientId), accessTokenInfo, TOKEN_EFFICIENT_TIME);
        cacheManager.addData(generateAccessTokenKey(accessToken), accessTokenInfo, TOKEN_EFFICIENT_TIME);
        cacheManager.addData(generateRefreshTokenKey(refreshToken), accessToken, REFRESH_TOKEN_EFFICIENT_TIME);
        return accessTokenInfo;
    }

    @Override
    public AccessTokenInfo generateAccessTokenByRefreshToken(String refreshToken, String clientId) {
        String accessToken = cacheManager.getData(generateRefreshTokenKey(refreshToken), String.class);
        if(StrUtil.isBlank(accessToken)){
            throw new ServiceException(ResultCode.FORBIDDEN);
        }
        AccessTokenInfo accessTokenEntity = cacheManager.getData(generateAccessTokenKey(accessToken), AccessTokenInfo.class);
        if(ObjectUtil.isNull(accessTokenEntity)) {
            throw new ServiceException(ResultCode.FORBIDDEN);
        }

        AccessTokenInfo newAccessTokenEntity = AccessTokenInfo
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(TOKEN_EFFICIENT_TIME)
                .clientId(clientId)
                .user(accessTokenEntity.getUser())
                .tokenType("bearer")
                .build();
        cacheManager.addData(generateUserTokenKey(accessTokenEntity.getUser().getUserId(),clientId),newAccessTokenEntity,TOKEN_EFFICIENT_TIME);
        cacheManager.addData(generateAccessTokenKey(accessToken),newAccessTokenEntity,TOKEN_EFFICIENT_TIME);
        cacheManager.addData(generateRefreshTokenKey(refreshToken),accessToken,REFRESH_TOKEN_EFFICIENT_TIME);
        return newAccessTokenEntity;
    }

    @Override
    public AccessTokenInfo refreshAccessToken(String accessToken) {
        //刷新token,如果token不存在或者已失效则报错 抛出AccessTokenException
        AccessTokenInfo accessTokenEntity = cacheManager.getData(generateAccessTokenKey(accessToken), AccessTokenInfo.class);
        if (null == accessTokenEntity) {
            /**
             * TODO 密钥不存在 说明密钥过期
             */
            throw new ServiceException(ResultCode.FORBIDDEN);
        }
        UserAuthorizeDO userAuthorizeDO = userAuthorizeRepository.selectByUserIdAndThirdPartyCloud(accessTokenEntity.getUser().getUserId(), accessTokenEntity.getUser().getThirdPartyCloud());
        AccessTokenInfo.User user = new AccessTokenInfo.User();
        BeanUtil.copyProperties(userAuthorizeDO, user);
        accessTokenEntity.setUser(user);
        //刷新token
        cacheManager.addData(generateAccessTokenKey(accessToken), accessTokenEntity, TOKEN_EFFICIENT_TIME);
        return accessTokenEntity;
    }

    @Override
    public AccessTokenInfo getAccessToken(String token) {
        AccessTokenInfo accessTokenEntity = cacheManager.getData(generateAccessTokenKey(token), AccessTokenInfo.class);
        if (null == accessTokenEntity) {
            throw new ServiceException(ResultCode.FORBIDDEN);
        }
        return accessTokenEntity;
    }


    private String generateCodeKey(String code) {
        return String.join("_", OAUTH2_CODE, code);
    }

    private String generateAccessTokenKey(String accessToken) {
        return String.join("_", ACCESS_TOKEN, accessToken);
    }

    private String generateUserTokenKey(String userId, String clientId) {
        return String.join("_", USER_TOKEN_PREFIX, userId, clientId);
    }

    private String generateRefreshTokenKey(String refreshToken) {
        return String.join("_", REFRESH_TOKEN, refreshToken);
    }
}
