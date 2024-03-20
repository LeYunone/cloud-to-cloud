package com.leyunone.cloudcloud.handler.action;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.third.google.GoogleStandardRequest;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.CloudCloudHandlerFactory;
import com.leyunone.cloudcloud.handler.protocol.AbstractStrategyProtocolHandler;
import com.leyunone.cloudcloud.handler.protocol.CloudProtocolHandler;
import com.leyunone.cloudcloud.mangaer.AccessTokenManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/3/4
 */
@Service
public class GoogleCloudHandler extends AbstractCloudCloudHandler {

    private final HttpServletRequest httpServletRequest;

    protected GoogleCloudHandler(CloudCloudHandlerFactory factory, AccessTokenManager accessTokenManager, ThirdPartyConfigService thirdPartyConfigService, HttpServletRequest httpServletRequest) {
        super(factory, accessTokenManager, thirdPartyConfigService);
        this.httpServletRequest = httpServletRequest;
    }


    @Override
    protected String getAccessToken(String request) {
        /**
         * google 访问令牌在请求头中 Authorization: Bearer ACCESS_TOKEN
         */
        String authorization = httpServletRequest.getHeader("Authorization");
        if (StrUtil.isBlank(authorization)) {
            //TODO 令牌传输异常
        }
        return authorization.replace("Bearer ","").trim();
    }

    @Override
    protected String dispatchHandler(String request, AccessTokenInfo accessToken) {
        GoogleStandardRequest googleStandardRequest = JSONObject.parseObject(request, GoogleStandardRequest.class);
        ThirdPartyCloudConfigInfo config = thirdPartyConfigService.getConfig(accessToken.getClientId());

        String intent = CollectionUtil.getFirst(googleStandardRequest.getInputs()).getIntent();
        AbstractStrategyProtocolHandler outwardCloudHandler = factory.getStrategy(intent, AbstractStrategyProtocolHandler.class);
        Object action = outwardCloudHandler.action(request, new ActionContext(accessToken, config));
        return JSONObject.toJSONString(action);
    }

    @Override
    protected String getKey() {
        return ThirdPartyCloudEnum.GOOGLE.name();
    }
}
