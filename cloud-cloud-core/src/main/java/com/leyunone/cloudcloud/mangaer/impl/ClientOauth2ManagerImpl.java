package com.leyunone.cloudcloud.mangaer.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.ClientAccessTokenModel;
import com.leyunone.cloudcloud.bean.RefreshTokenModel;
import com.leyunone.cloudcloud.bean.dto.RequestTokenDTO;
import com.leyunone.cloudcloud.bean.enums.DelayMessageTypeEnum;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.message.RefreshTokenMessage;
import com.leyunone.cloudcloud.dao.DeviceRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.exception.AccessTokenException;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.mangaer.ClientOauthManager;
import com.leyunone.cloudcloud.mangaer.DelayMessageManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import com.leyunone.cloudcloud.util.ClientTokenUtils;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import com.leyunone.cloudcloud.util.TimeTranslationUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * :)
 * 仅支持客户端oauth2标准流程
 *
 * @Author LeYunone
 * @Date 2024/5/27 17:24
 */
@Service
public class ClientOauth2ManagerImpl implements ClientOauthManager {

    private final ThirdPartyConfigService thirdPartyCloudService;
    private final DelayMessageManager delayMessageManager;
    private final DeviceRepository deviceRepository;

    private final String CLIENT_AUTH_CODE = "CLIENT_AUTH_CODE";

    private final CacheManager cacheManager;

    public ClientOauth2ManagerImpl(ThirdPartyConfigService thirdPartyCloudService, DelayMessageManager delayMessageManager, DeviceRepository deviceRepository, CacheManager cacheManager) {
        this.thirdPartyCloudService = thirdPartyCloudService;
        this.delayMessageManager = delayMessageManager;
        this.deviceRepository = deviceRepository;
        this.cacheManager = cacheManager;
    }

    @Override
    public ClientAccessTokenModel requestExchangeAccessToken(RequestTokenDTO requestTokenModel) {
        ThirdPartyCloudConfigInfo config = thirdPartyCloudService.getConfig(requestTokenModel.getClientId());
        ClientAccessTokenModel accessTokenModel = this.requestToken(requestTokenModel.getCode(),
                "authorization_code", config.getRedirectUri(), config.getAccessTokenUrl(), config.getClientId(), config.getClientSecret(), null);
        if (ObjectUtil.isNull(accessTokenModel.getAccessToken())) {
            throw new AccessTokenException("access fail");
        }
        accessTokenModel.setClientId(requestTokenModel.getClientId());
        accessTokenModel.setBusinessId(requestTokenModel.getBusinessId());
        accessTokenModel.setAppId(requestTokenModel.getAppId());
        this.loadClientToken(accessTokenModel, config.getThirdPartyCloud());
        return accessTokenModel;
    }

    /**
     * 定时刷新令牌
     *
     * @param refreshTokenModel
     */
    @Override
    public void timingRefreshUserAccessToken(RefreshTokenModel refreshTokenModel, String messageId) {
        ThirdPartyCloudConfigInfo thirdPartyClient = thirdPartyCloudService.getConfig(refreshTokenModel.getClientId());

        ClientAccessTokenModel clientAccessTokenModel = null;
        try {
            clientAccessTokenModel = this.requestToken(null, "refresh_token",
                    null, thirdPartyClient.getAccessTokenUrl(),
                    thirdPartyClient.getClientId(), thirdPartyClient.getClientSecret(), refreshTokenModel.getRefreshToken());
        } catch (Exception e) {
            /**
             * TODO 当我方https无法连接外网或对方域名迁移时，将重复进行http请求直到重试三次
             */
            RefreshTokenMessage refreshTokenMessage = new RefreshTokenMessage(DelayMessageTypeEnum.OAUTH2_REFRESH_TOKEN);
            refreshTokenMessage.setMessageId(messageId);
            refreshTokenMessage.setBusinessId(refreshTokenModel.getBusinessId());
            refreshTokenMessage.setAppId(refreshTokenModel.getAppId());
            refreshTokenMessage.setClientId(refreshTokenModel.getClientId());
            refreshTokenMessage.setRefreshToken(refreshTokenModel.getRefreshToken());
            //1分钟执行三次
            delayMessageManager.pushMessage(refreshTokenMessage, 1000 * 60);
            cacheManager.count(messageId, 1);
            return;
        }
        clientAccessTokenModel.setBusinessId(refreshTokenModel.getBusinessId());
        clientAccessTokenModel.setAppId(refreshTokenModel.getAppId());
        clientAccessTokenModel.setClientId(refreshTokenModel.getClientId());
        this.loadClientToken(clientAccessTokenModel, thirdPartyClient.getThirdPartyCloud());
    }

    @Override
    public void requestRefreshAccessTokenByKeys(List<String> refreshTokenKey) {
        if (CollectionUtil.isEmpty(refreshTokenKey)) {
            return;
        }
        List<DeviceDO> deviceDOS = deviceRepository.selectByRefreshKeys(refreshTokenKey);
        if (CollectionUtil.isEmpty(deviceDOS)) {
            return;
        }
        List<ThirdPartyCloudConfigInfo> clients = thirdPartyCloudService.getConfig(CollectionUtil.newArrayList(deviceDOS.stream().map(DeviceDO::getClientId).collect(Collectors.toSet())));
        Map<String, ThirdPartyCloudConfigInfo> clientMap = CollectionFunctionUtils.mapTo(clients, ThirdPartyCloudConfigInfo::getClientId);
        Map<String, List<DeviceDO>> deviceMap = CollectionFunctionUtils.groupTo(deviceDOS, DeviceDO::getRefreshTokenKey);
        deviceMap.forEach((rk, values) -> {
            DeviceDO deviceDO = CollectionUtil.getFirst(values);
            ThirdPartyCloudConfigInfo thirdPartyClient = clientMap.get(deviceDO.getClientId());
            if (ObjectUtil.isNull(thirdPartyClient)) {
                return;
            }
            ClientAccessTokenModel clientAccessTokenModel = this.requestToken(null, "refresh_token",
                    null, thirdPartyClient.getAccessTokenUrl(),
                    thirdPartyClient.getClientId(), thirdPartyClient.getClientSecret(), rk);
            this.loadClientToken(clientAccessTokenModel, thirdPartyClient.getThirdPartyCloud());
        });
    }

    @Override
    public void requestRefreshAccessTokenByDeviceIds(List<Long> deviceIds) {
        List<DeviceDO> deviceDOS = deviceRepository.selectByIds(deviceIds);
        if (CollectionUtil.isEmpty(deviceDOS)) {
            return;
        }
        Set<String> refreshTokenKeys = deviceDOS.stream().filter(t -> StrUtil.isNotBlank(t.getRefreshTokenKey())).map(DeviceDO::getRefreshTokenKey).collect(Collectors.toSet());
        this.requestRefreshAccessTokenByKeys(CollectionUtil.newArrayList(refreshTokenKeys));
    }

    private ClientAccessTokenModel requestToken(String code, String grantType, String redirectUri, String tokenUrl, String clientId, String clientSecret, String refreshToken) {
        /**
         * 获取accessToken
         */
        Map<String, Object> param = new HashMap<>();
        if (StrUtil.isNotBlank(code)) {
            param.put("code", code);
        }
        if (StrUtil.isNotBlank(redirectUri)) {
            //这个重定向路由只能是原机器路由
            param.put("redirect_uri", redirectUri);
        }
        if (StrUtil.isNotBlank(refreshToken)) {
            param.put("refresh_token", refreshToken);
        }

        param.put("grant_type", grantType);

        String body = HttpUtil.createRequest(Method.POST, tokenUrl)
                .basicAuth(clientId, clientSecret)
                .form(param)
                .execute().body();
        return JSONObject.parseObject(body, ClientAccessTokenModel.class);
    }

    @Override
    public String getAccessToken(String clientId, String businessId, Integer appId) {
        String token = cacheManager.getData(ClientTokenUtils.generateClientAccessToken(clientId, businessId, appId), String.class);
        if (StrUtil.isBlank(token)) {
            throw new AccessTokenException("access token not exist");
        }
        return token;
    }

    @Override
    public String getRefreshToken(String clientId, String businessId, Integer appId) {
        String token = cacheManager.getData(ClientTokenUtils.generateClientRefreshToken(clientId, businessId, appId), String.class);
        if (StrUtil.isBlank(token)) {
            throw new AccessTokenException("access token not exist");
        }
        return token;
    }

    @Override
    public void removeToken(String clientId, String businessId, Integer appId) {
        cacheManager.deleteData(ClientTokenUtils.generateClientAccessToken(clientId, businessId, appId));
        cacheManager.deleteData(ClientTokenUtils.generateClientRefreshToken(clientId, businessId, appId));
    }

    @Override
    public String getAccessToken(String tokenKey, boolean exception) {
        String token = cacheManager.getData(tokenKey, String.class);
        if (StrUtil.isBlank(token) && exception) {
            throw new AccessTokenException("access token not exist");
        }
        return token;
    }

    /**
     * 如果过期时间超过int4字节 一定说明令牌为按月制
     *
     * @param accessTokenModel
     * @param thirdPartyCloud
     */
    private void loadClientToken(ClientAccessTokenModel accessTokenModel, ThirdPartyCloudEnum thirdPartyCloud) {

        String tokenKey = accessTokenModel.getAccessTokenKey();
        if (StrUtil.isBlank(tokenKey)) {
            tokenKey = ClientTokenUtils.generateClientAccessToken(accessTokenModel.getClientId(),
                    accessTokenModel.getBusinessId(),
                    accessTokenModel.getAppId());
        }
        String refreshTokenKey = accessTokenModel.getRefreshTokenKey();
        if (StrUtil.isBlank(refreshTokenKey)) {
            refreshTokenKey = ClientTokenUtils.generateClientRefreshToken(accessTokenModel.getClientId(),
                    accessTokenModel.getBusinessId(),
                    accessTokenModel.getAppId());
        }
        //当为月制时间时 分两端延时队列处理
        int time = TimeTranslationUtils.expiresInTimeToInt(accessTokenModel.getExpiresIn(), thirdPartyCloud);
        //时间毫秒 存储对方的token 时间为-1时代表半永久
        cacheManager.addData(tokenKey,
                accessTokenModel.getAccessToken()
                , (long) time, TimeUnit.MINUTES);

        /**
         * 使用延时队列23小时投递一次对应客户端的刷新令牌
         */
        cacheManager.addData(refreshTokenKey,
                accessTokenModel.getRefreshToken());
        String messageId = UUID.randomUUID().toString();
        //一半的时间
        RefreshTokenMessage refreshTokenMessage = new RefreshTokenMessage(DelayMessageTypeEnum.OAUTH2_REFRESH_TOKEN);
        refreshTokenMessage.setMessageId(messageId);
        refreshTokenMessage.setBusinessId(accessTokenModel.getBusinessId());
        refreshTokenMessage.setAppId(accessTokenModel.getAppId());
        refreshTokenMessage.setClientId(accessTokenModel.getClientId());
        refreshTokenMessage.setRefreshToken(accessTokenModel.getRefreshToken());
        int timingTime = 0;
        if (time == -1) {
            //10天
            timingTime = 1000 * 60 * 60 * 24 * 10;
            //超出mq延时队列时间
            refreshTokenMessage.setMonth(true);
            refreshTokenMessage.setOverTime(accessTokenModel.getExpiresIn());
        } else {
            timingTime = (time / 2) - 1000 * 60;
        }
        refreshTokenMessage.setTimingTime(timingTime);

        delayMessageManager.pushMessage(refreshTokenMessage, timingTime);
        cacheManager.addData(messageId, "1", 11L, TimeUnit.DAYS);
    }
}
