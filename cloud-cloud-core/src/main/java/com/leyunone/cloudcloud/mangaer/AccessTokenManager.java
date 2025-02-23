package com.leyunone.cloudcloud.mangaer;

import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public interface AccessTokenManager {

    /**
     * 根据clientId和用户session信息获取OAuthCode
     * @param clientId
     * @return
     */
    String generateOAuthCode(String clientId,String userId, ThirdPartyCloudConfigInfo cloudServiceConfig);

    /**
     * 授权后生成对应应用的鉴权信息
     * @param code
     * @param clientId
     * @return
     */
    AccessTokenInfo generateAccessTokenByCode(String code,String clientId);

    AccessTokenInfo generateAccessTokenByRefreshToken(String refreshToken,String clientId);

    AccessTokenInfo refreshAccessToken(String accessToken);

    AccessTokenInfo getAccessToken(String token);
}
