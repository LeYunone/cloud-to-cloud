package com.leyunone.cloudcloud.service;

import com.leyunone.cloudcloud.bean.dto.RequestTokenDTO;
import com.leyunone.cloudcloud.bean.vo.AccessTokenVO;

import javax.servlet.http.HttpServletRequest;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/18
 */
public interface OAuthService {

    /**
     * 根据clientId和用户session信息获取OAuthCode
     * @param clientId
     * @param request
     * @return
     */
    String generateOAuthCode(String clientId, HttpServletRequest request);


    /**
     * 根据generateOAuthCode返回的code值返回accessToken。
     * @param code
     * @return
     */
    AccessTokenVO generateAccessTokenByCode(String code, String clientId);

    AccessTokenVO generateAccessTokenByRefreshToken(String refreshToken,String clientId);

    void requestExchangeAccessToken(RequestTokenDTO requestToken);
}
