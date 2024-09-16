package com.leyunone.cloudcloud.mangaer;

import com.leyunone.cloudcloud.bean.ClientAccessTokenModel;
import com.leyunone.cloudcloud.bean.RefreshTokenModel;
import com.leyunone.cloudcloud.bean.dto.RequestTokenDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/27 17:18
 */
@Service
public interface ClientOauthManager {

    /**
     * 对方从我方获取token
     * @param clientId 客户端id
     * @param userId 用户id
     * @param appId 应用标识
     * @return
     */
    String getAccessToken(String clientId,String userId,Integer appId);

    ClientAccessTokenModel requestExchangeAccessToken(RequestTokenDTO requestTokenModel);

    void timingRefreshUserAccessToken(RefreshTokenModel refreshTokenModel, String messageId);

    void requestRefreshAccessTokenByKeys(List<String> refreshTokenKey);

    void requestRefreshAccessTokenByDeviceIds(List<Long> deviceIds);

    String getRefreshToken(String clientId, String businessId, Integer appId);

    void removeToken(String clientId, String businessId, Integer appId);

    String getAccessToken(String tokenKey,boolean exception);
}
