package com.leyunone.cloudcloud.mangaer.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.cloudcloud.bean.ClientAccessTokenModel;
import com.leyunone.cloudcloud.bean.dto.RequestTokenDTO;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.mangaer.ClientOauthManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import org.springframework.stereotype.Service;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/27 17:24
 */
@Service
public class ClientOauthManagerImpl implements ClientOauthManager {

    private final String CLIENT_ACCESS_TOKEN = "CLIENT_ACCESS_TOKEN";
    private final String CLIENT_REFRESH_TOKEN = "CLIENT_REFRESH_TOKEN";
    private final String CLIENT_AUTH_CODE = "CLIENT_AUTH_CODE";

    private final ThirdPartyConfigService thirdPartyCloudService;
    private final CacheManager cacheManager;

    public ClientOauthManagerImpl(ThirdPartyConfigService thirdPartyCloudService, CacheManager cacheManager) {
        this.thirdPartyCloudService = thirdPartyCloudService;
        this.cacheManager = cacheManager;
    }


    @Override
    public void requestExchangeAccessToken(RequestTokenDTO requestTokenModel) {
        ThirdPartyCloudConfigInfo config = thirdPartyCloudService.getConfig(requestTokenModel.getClientId());
        if (ObjectUtil.isNull(config)) {
            //未配置
        }
        String accessTokenUrl = config.getAccessTokenUrl();
        /**
         * 获取accessToken
         */
        JSONObject param = new JSONObject();
        param.put("code", requestTokenModel.getCode());

        String body = HttpUtil.createRequest(Method.POST, accessTokenUrl).body(param.toJSONString()).execute().body();
        ClientAccessTokenModel accessTokenModel = JSONObject.parseObject(body, ClientAccessTokenModel.class);

        accessTokenModel.setClientId(requestTokenModel.getClientId());
        accessTokenModel.setUserId(requestTokenModel.getUserId());
        accessTokenModel.setAppId(requestTokenModel.getAppId());
        this.loadClientToken(accessTokenModel);
    }

    @Override
    public String getAccessToken(String clientId, String userId, Integer appId) {
        String token = cacheManager.getData(this.generateClientAccessToken(clientId, userId, appId), String.class);
        if (StringUtils.isBlank(token)) {
        }
        return token;
    }


    /**
     * 刷新令牌
     *
     * @param clientId
     */
    public void requestRefreshToken(String clientId, String userId, Integer appId) {
        ThirdPartyCloudConfigInfo config = thirdPartyCloudService.getConfig(clientId);
        if (ObjectUtil.isNull(config)) {
            //未配置
        }
        String refreshToken = cacheManager.getData(generateClientRefreshToken(clientId, userId, appId), String.class);
        JSONObject param = new JSONObject();
        param.put("refreshToken", refreshToken);
        String body = HttpUtil.createRequest(Method.POST, config.getAccessTokenUrl()).body(param.toJSONString()).execute().body();
        ClientAccessTokenModel accessTokenModel = JSONObject.parseObject(body, ClientAccessTokenModel.class);
        accessTokenModel.setClientId(clientId);
        this.loadClientToken(accessTokenModel);
    }

    private void loadClientToken(ClientAccessTokenModel accessTokenModel) {
        //时间毫秒 存储对方的token
        cacheManager.addData(generateClientAccessToken(accessTokenModel.getClientId(), accessTokenModel.getUserId(), accessTokenModel.getAppId()), accessTokenModel.getAccessToken(), accessTokenModel.getExpiresIn());

        /**
         * TODO 刷新令牌
         * 使用延时队列每X分钟投递一次对应客户端的刷新令牌
         */
        cacheManager.addData(generateClientRefreshToken(accessTokenModel.getClientId(), accessTokenModel.getUserId(), accessTokenModel.getAppId()), accessTokenModel.getRefreshToken());
    }

    private String generateClientAuthCode(String code) {
        return String.join("_", CLIENT_AUTH_CODE, code);
    }

    private String generateClientAccessToken(String clientId, String userId, Integer appId) {
        return String.join("_", CLIENT_ACCESS_TOKEN, clientId, userId, appId.toString());
    }

    private String generateClientRefreshToken(String clientId, String userId, Integer appId) {
        return String.join("_", CLIENT_REFRESH_TOKEN, clientId, userId, appId.toString());
    }
}
