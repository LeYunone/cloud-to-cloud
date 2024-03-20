package com.leyunone.cloudcloud.handler.action;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.enums.XiaomiIntent;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.third.xiaomi.XiaomiCommonPayload;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.CloudCloudHandlerFactory;
import com.leyunone.cloudcloud.handler.protocol.AbstractStrategyProtocolHandler;
import com.leyunone.cloudcloud.handler.protocol.CloudProtocolHandler;
import com.leyunone.cloudcloud.mangaer.AccessTokenManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/3/6
 */
@Service
public class XiaoMiCloudHandler extends AbstractCloudCloudHandler{

    protected XiaoMiCloudHandler(CloudCloudHandlerFactory factory, AccessTokenManager accessTokenManager, ThirdPartyConfigService thirdPartyConfigService) {
        super(factory, accessTokenManager, thirdPartyConfigService);
    }

    @Override
    protected String getAccessToken(String request) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Objects.requireNonNull(attributes,"getRequestAttributes is null");
        HttpServletRequest httpRequest = attributes.getRequest();
        return httpRequest.getHeader("User-Token");
    }


    @Override
    protected String dispatchHandler(String request, AccessTokenInfo accessTokenEntity) {
        XiaomiCommonPayload xiaomiCommonPayload = JSONObject.parseObject(request, XiaomiCommonPayload.class);
        ThirdPartyCloudConfigInfo config = thirdPartyConfigService.getConfig(accessTokenEntity.getClientId());
        String intent = xiaomiCommonPayload.getIntent();
        // #{@link XiaomiIntent}
        XiaomiIntent xiaomiIntent = XiaomiIntent.getByIntent(intent);
        AbstractStrategyProtocolHandler outwardCloudHandler = factory.getStrategy(xiaomiIntent.name(), AbstractStrategyProtocolHandler.class);
        Object action = outwardCloudHandler.action(request, new ActionContext(accessTokenEntity, config));
        return JSONObject.toJSONString(action);
    }


    @Override
    protected String getKey() {
        return ThirdPartyCloudEnum.XIAOMI.name();
    }
}
