package com.leyunone.cloudcloud.mangaer;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.UserClientInfoModel;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaGetTokenRequest;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaToken;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.dao.UserAuthorizeRepository;
import com.leyunone.cloudcloud.dao.entity.UserAuthorizeDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * :)
 * Alexa token管理器
 *
 * @Author LeYunone
 * @Date 2024/2/23 14:10
 */
@Service
public class AlexaTokenManager {

    private final CacheManager cacheManager;
    private final UserAuthorizeRepository userAuthorizeRepository;
    private final ThirdPartyConfigService thirdPartyConfigService;
    private final Logger logger = LoggerFactory.getLogger(AlexaTokenManager.class);

    private final static String ALEXA_TOKEN = "ALEXA";
    private final static String ALEXA_REFRESH_TOKEN = "ALEXA_REFRESH";
    private final static String tokenURL = "https://api.amazon.com/auth/o2/token";

    public AlexaTokenManager(CacheManager cacheManager, UserAuthorizeRepository userAuthorizeRepository, ThirdPartyConfigService thirdPartyConfigService) {
        this.cacheManager = cacheManager;
        this.userAuthorizeRepository = userAuthorizeRepository;
        this.thirdPartyConfigService = thirdPartyConfigService;
    }

    public void acceptToken(String token, String userId) {
        cacheManager.addData(generateAlexaTokenKey(userId), token, 3500L, TimeUnit.SECONDS);
    }

    public void acceptRefreshToken(String refreshToken, String userId) {
        cacheManager.addData(generateAlexaRefreshToken(userId), refreshToken);
    }

    public void loadAlexaToken(String code, AccessTokenInfo accessToken) {
        ThirdPartyCloudConfigInfo config = thirdPartyConfigService.getConfig(accessToken.getClientId());
        AlexaToken alexaToken = this.tokenRequest("authorization_code", code, null,config);
        this.acceptRefreshToken(alexaToken.getRefreshToken(), accessToken.getUser().getUserId());
        this.acceptToken(alexaToken.getAccessToken(), accessToken.getUser().getUserId());
    }

    public String getTokenOrRefresh(String userId) {
        String token = cacheManager.getData(generateAlexaTokenKey(userId), String.class);
        if (StrUtil.isBlank(token)) {
            //token不存在 刷新令牌 
            String refreshToken = cacheManager.getData(generateAlexaRefreshToken(userId), String.class);
            UserClientInfoModel userClientInfoModel = userAuthorizeRepository.selectUserClientInfo(userId, ThirdPartyCloudEnum.ALEXA);
            ThirdPartyCloudConfigInfo config = thirdPartyConfigService.getConfig(userClientInfoModel.getClientId());
            AlexaToken alexaToken = null;
            //刷新令牌也不存在 获取新的令牌
            if (StrUtil.isBlank(refreshToken)) {
                alexaToken = this.tokenRequest("authorization_code", userClientInfoModel.getThirdInfo(), null,config);
            } else {
                //刷新令牌
                alexaToken = this.tokenRequest("refresh_token", null, refreshToken,config);
            }
            if (ObjectUtil.isNotNull(alexaToken)) {
                token = alexaToken.getAccessToken();
                refreshToken = alexaToken.getRefreshToken();
                this.acceptToken(token, userId);
                this.acceptRefreshToken(refreshToken, userId);
            }
        }
        return token;
    }

    public void saveTokenCode(String userId, String code) {
        UserAuthorizeDO userAuthorizeDO = userAuthorizeRepository.selectByUserIdAndThirdPartyCloud(userId, ThirdPartyCloudEnum.ALEXA);
        if (ObjectUtil.isNotNull(userAuthorizeDO)) {
            userAuthorizeDO.setThirdInfo(code);
            userAuthorizeRepository.updateByUserIdAndThirdPartyCloud(userAuthorizeDO, ThirdPartyCloudEnum.ALEXA);
        }
    }

    private AlexaToken tokenRequest(String type, String code, String refreshToken,ThirdPartyCloudConfigInfo config) {
        AlexaGetTokenRequest alexaGetTokenRequest = AlexaGetTokenRequest.builder()
                .client_secret(config.getThirdSecret())
                .client_id(config.getThirdClientId())
                .code(code)
                .grant_type(type)
                .refresh_token(refreshToken)
                .build();
        String body = HttpUtil.createRequest(Method.POST, tokenURL)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .body(JSONObject.toJSONString(alexaGetTokenRequest)).execute().body();
        logger.debug("alexa token:{}", body);
        return JSONObject.parseObject(body, AlexaToken.class);
    }

    private String generateAlexaTokenKey(String userId) {
        return StrUtil.join("_", ALEXA_TOKEN, userId);
    }

    private String generateAlexaRefreshToken(String userId) {
        return StrUtil.join("_", ALEXA_REFRESH_TOKEN, userId);
    }

}
