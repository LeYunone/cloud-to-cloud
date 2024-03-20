package com.leyunone.cloudcloud.service;

import cn.hutool.core.util.StrUtil;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.vo.AccessTokenVO;
import com.leyunone.cloudcloud.mangaer.AccessTokenManager;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/18
 */
@Service
public class OAuthServiceImpl implements OAuthService {

    public static final String USER_ID = "userId";

    private final AccessTokenManager accessTokenManager;
    private final ThirdPartyConfigService thirdPartyConfigService;

    public OAuthServiceImpl(AccessTokenManager accessTokenManager, ThirdPartyConfigService thirdPartyConfigService) {
        this.accessTokenManager = accessTokenManager;
        this.thirdPartyConfigService = thirdPartyConfigService;
    }

    @Override
    public String generateOAuthCode(String clientId, HttpServletRequest request) {
        String userId = request.getParameter(USER_ID);
        if (StrUtil.isBlank(userId)) {
            userId = request.getHeader(USER_ID);
        }
        ThirdPartyCloudConfigInfo config = thirdPartyConfigService.getConfig(clientId);

        return accessTokenManager.generateOAuthCode(clientId, userId, config);
    }

    @Override
    public AccessTokenVO generateAccessTokenByCode(String code, String clientId) {
        AccessTokenInfo accessTokenEntity = accessTokenManager.generateAccessTokenByCode(code, clientId);
        return AccessTokenVO
                .builder()
                .access_token(accessTokenEntity.getAccessToken())
                .refresh_token(accessTokenEntity.getRefreshToken())
                .expires_in(accessTokenEntity.getExpiresIn())
                .token_type(accessTokenEntity.getTokenType())
                .build();
    }

    @Override
    public AccessTokenVO generateAccessTokenByRefreshToken(String refreshToken, String clientId) {
        AccessTokenInfo accessTokenEntity = accessTokenManager.generateAccessTokenByRefreshToken(refreshToken, clientId);
        return AccessTokenVO
                .builder()
                .access_token(accessTokenEntity.getAccessToken())
                .refresh_token(accessTokenEntity.getRefreshToken())
                .expires_in(accessTokenEntity.getExpiresIn())
                .token_type(accessTokenEntity.getTokenType())
                .build();
    }
}
