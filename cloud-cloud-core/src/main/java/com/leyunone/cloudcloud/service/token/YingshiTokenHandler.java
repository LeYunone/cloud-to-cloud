package com.leyunone.cloudcloud.service.token;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.ClientAccessTokenModel;
import com.leyunone.cloudcloud.bean.enums.DelayMessageTypeEnum;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.message.TimingRefreshTokenMessage;
import com.leyunone.cloudcloud.bean.third.yingshi.YingshiTokenInfoResponse;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.exception.AccessTokenException;
import com.leyunone.cloudcloud.handler.delay.TimingLoopOverallTokenMessageHandler;
import com.leyunone.cloudcloud.handler.factory.NoOauth2GetTokenHandlerFactory;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.mangaer.DelayMessageManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import com.leyunone.cloudcloud.util.TimeTranslationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/9/2 14:20
 */
@Service
public class YingshiTokenHandler extends AbstractNonStandardTokenHandler {

    private final ThirdPartyConfigService thirdPartyCloudService;
    private final Logger logger = LoggerFactory.getLogger(YingshiTokenHandler.class);
    private final DelayMessageManager delayMessageManager;
    private final CacheManager cacheManager;

    public static ClientAccessTokenModel TOKEN_TEMP = null;

    protected YingshiTokenHandler(NoOauth2GetTokenHandlerFactory factory, ThirdPartyConfigService thirdPartyCloudService, DelayMessageManager delayMessageManager, CacheManager cacheManager) {
        super(factory);
        this.thirdPartyCloudService = thirdPartyCloudService;
        this.delayMessageManager = delayMessageManager;
        this.cacheManager = cacheManager;
    }

    @Override
    String getKey() {
        return ThirdPartyCloudEnum.YINGSHI.name();
    }

    @Override
    public ClientAccessTokenModel get(String clientId) {
        ThirdPartyCloudConfigInfo config = thirdPartyCloudService.getConfig(clientId);
        RequestTokenParam requestTokenParam = JSONObject.parseObject(config.getAdditionalInformation(), RequestTokenParam.class);
        //请求 https://open.ys7.com/api/lapp/token/get
        String body = HttpUtil.createPost(config.getAccessTokenUrl())
                .form(BeanUtil.beanToMap(requestTokenParam))
                .execute().body();
        if (StrUtil.isBlank(body)) {
            throw new AccessTokenException();
        }
        YingshiTokenInfoResponse yingShiTokenInfoResponse = JSONObject.parseObject(body, YingshiTokenInfoResponse.class);
        if (!"200".equals(yingShiTokenInfoResponse.getCode())) {
            switch (yingShiTokenInfoResponse.getCode()) {
                case "10001":
                case "10005":
                case "10017":
                case "10030":
                    logger.error("萤石云令牌请求失败,失败信息:{}", yingShiTokenInfoResponse.getMsg());
                default:
            }
            return null;
        }
        ClientAccessTokenModel clientAccessTokenModel = new ClientAccessTokenModel();
        clientAccessTokenModel.setAccessToken(yingShiTokenInfoResponse.getData().getAccessToken());
        clientAccessTokenModel.setExpiresIn(yingShiTokenInfoResponse.getData().getExpireTime());
        TOKEN_TEMP = clientAccessTokenModel;
        return clientAccessTokenModel;
    }

    /**
     * 萤石的托管令牌投递到全局令牌循环中
     *
     * @param clientId
     * @param time
     */
    @Override
    public void deliveryLoop(String clientId, Long time) {
        String key = clientId + TimingLoopOverallTokenMessageHandler.class.getSimpleName();
        boolean exists = cacheManager.exists(key);
        if (exists) {
            return;
        }
        ThirdPartyCloudConfigInfo config = thirdPartyCloudService.getConfig(clientId);
        if (ObjectUtil.isNull(config)) {
            return;
        }
        TimingRefreshTokenMessage timingRefreshTokenMessage = new TimingRefreshTokenMessage();
        timingRefreshTokenMessage.setClientId(clientId);
        timingRefreshTokenMessage.setMessageId(UUID.randomUUID().toString());
        timingRefreshTokenMessage.setDelayMessageType(DelayMessageTypeEnum.LOOP_OVERALL_TOKEN);
        delayMessageManager.pushMessage(timingRefreshTokenMessage, TimeTranslationUtils.expiresInTimeToInt(time, config.getThirdPartyCloud()));
        cacheManager.addData(key, clientId, time - 360000L, TimeUnit.MILLISECONDS);
    }

    public static class RequestTokenParam {

        private String appKey;

        private String appSecret;

        public RequestTokenParam(String appKey, String appSecret) {
            this.appKey = appKey;
            this.appSecret = appSecret;
        }

        public String getAppKey() {
            return appKey;
        }

        public RequestTokenParam setAppKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public RequestTokenParam setAppSecret(String appSecret) {
            this.appSecret = appSecret;
            return this;
        }
    }
}
