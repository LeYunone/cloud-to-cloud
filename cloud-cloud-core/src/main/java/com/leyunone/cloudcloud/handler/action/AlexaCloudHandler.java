package com.leyunone.cloudcloud.handler.action;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.annotate.Strategist;
import com.leyunone.cloudcloud.bean.alexa.*;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.action.AbstractCloudCloudHandler;
import com.leyunone.cloudcloud.handler.factory.CloudCloudHandlerFactory;
import com.leyunone.cloudcloud.handler.protocol.CloudProtocolHandler;
import com.leyunone.cloudcloud.mangaer.AccessTokenManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/25
 */
@Service
public class AlexaCloudHandler extends AbstractCloudCloudHandler {

    private final ThirdPartyConfigService thirdPartyConfigService;

    protected AlexaCloudHandler(CloudCloudHandlerFactory factory, AccessTokenManager accessTokenManager, ThirdPartyConfigService thirdPartyConfigService) {
        super(factory, accessTokenManager);
        this.thirdPartyConfigService = thirdPartyConfigService;
    }

    /**
     * @return 策略key
     */
    protected String getKey() {
        return ThirdPartyCloudEnum.ALEXA.name();
    }

    @Override
    protected String getAccessToken(String request) {
        AlexaStandardRequest alexaStandardRequest = JSONObject.parseObject(request, AlexaStandardRequest.class);
        String token;
        try {
            token = alexaStandardRequest.getDirective().getPayload().getScope().getToken();
        } catch (Exception e) {
            token = alexaStandardRequest.getDirective().getEndpoint().getScope().getToken();
        }
        return token;
    }

    @Override
    protected String dispatchHandler(String request, AccessTokenInfo accessToken) {
        AlexaStandardRequest alexaStandardRequest = JSONObject.parseObject(request, AlexaStandardRequest.class);
        ThirdPartyCloudConfigInfo config = thirdPartyConfigService.getConfig(accessToken.getClientId());

        AlexaHeader header = alexaStandardRequest.getDirective().getHeader();
        String namespace = header.getNamespace();
        CloudProtocolHandler outwardCloudHandler = factory.getStrategy(namespace, CloudProtocolHandler.class);
        Object action = outwardCloudHandler.action(request, new ActionContext(accessToken, config));
        /**
         * 发现设备响应需要处理 interface特殊字段
         */
        if (action.getClass().isAssignableFrom(AlexaDiscoveryResponse.class)) {
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(action));
            JSONArray jsonArray = json.getJSONObject("event").getJSONObject("payload").getJSONArray("endpoints");
            for (Object o : jsonArray) {
                JSONObject jb = (JSONObject) o;
                JSONArray capabilities = jb.getJSONArray("capabilities");
                for (Object capability : capabilities) {
                    JSONObject cb = (JSONObject) capability;
                    cb.put("interface", cb.getString("interfaceStr"));
                    cb.remove("interfaceStr");
                }
            }
            return JSONObject.toJSONString(json);
        }
        return JSONObject.toJSONString(action);
    }
}
